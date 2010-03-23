package org.hhu.cs.p2p.net;

import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.log4j.Logger;
import org.hhu.cs.p2p.util.IOUtils;

/**
 * Downloads a file to the given root directory, creating missing directories in
 * respect to the root directory as a base.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class FileResponseHandler implements ResponseHandler<Path> {

	private static Logger logger = Logger.getLogger(FileResponseHandler.class);

	private Path rootDirectory;

	private URI uri;

	/**
	 * Creates a new {@link FileResponseHandler} that downloads the file under
	 * the given url to the same path under the base directory.
	 * 
	 * If the file already exists, it will be overwritten. If it is missing it
	 * will be created. Missing directories will be created.
	 * 
	 * @param rootDirectory
	 *            the base directory files are written to
	 * @param uri
	 *            the file that should be downloadead
	 */
	protected FileResponseHandler(Path rootDirectory, URI uri) {
		this.rootDirectory = rootDirectory;
		this.uri = uri;
	}

	@Override
	public Path handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {

		Path p = Paths.get(rootDirectory.toString() + uri.getPath());
		Files.createDirectories(IOUtils.pathWithoutFilename(p));

		logger.info(String.format("Writing to %1s.", p));

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			OutputStream out = null;
			try {
				out = new BufferedOutputStream(p.newOutputStream(CREATE));
				response.getEntity().writeTo(out);
			} catch (IOException e) {
				logger
						.error(String.format("Couldn't download \"%1s\"", uri),
								e);
			} finally {
				if (out != null) {
					out.flush();
					out.close();
				}
			}
		}
		return p;
	}
}