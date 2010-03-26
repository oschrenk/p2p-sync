package org.hhu.cs.p2p.tasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.core.Registry;
import org.hhu.cs.p2p.index.Attributes;
import org.hhu.cs.p2p.local.LocalIndex;
import org.hhu.cs.p2p.net.NetworkClient;
import org.hhu.cs.p2p.remote.RemoteIndex;

/**
 * A task that pushes a change that occured remotely and pushing it into local
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class CreatePullTask extends GenericTask {

	private static Logger logger = Logger.getLogger(CreatePullTask.class);

	/**
	 * @param localIndex
	 * @param remoteIndex
	 * @param path
	 * @see GenericTask
	 */
	public CreatePullTask(LocalIndex localIndex, RemoteIndex remoteIndex,
			Path path) {
		super(localIndex, remoteIndex, path);
	}

	@Override
	public void execute() throws IOException {
		Attributes remoteAttributes = remoteIndex.get(path);
		Path rootDirectory = Registry.getInstance().getRootDirectory();

		try {
			// TODO get rid of getRootDirectory, stupid (non)dependency
			new NetworkClient(rootDirectory).request(remoteAttributes
					.getAddress(), path);
		} catch (URISyntaxException e) {
			logger.error(e);
		}

		if (logger.isTraceEnabled()) {

		}
		logger.trace(String.format("Setting time on %1s to %2s", path,
				remoteAttributes.lastModifiedTime()));

		rootDirectory.resolve(path).setAttribute("basic:lastModifiedTime",
				FileTime.fromMillis(remoteAttributes.lastModifiedTime()),
				LinkOption.NOFOLLOW_LINKS);

		localIndex.addFromRemote(path, remoteAttributes);
	}
}