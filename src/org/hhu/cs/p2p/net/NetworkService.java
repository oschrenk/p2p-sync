package org.hhu.cs.p2p.net;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.log4j.Logger;

import com.sun.grizzly.http.embed.GrizzlyWebServer;

/**
 * Embedded WebServer for serving static files.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class NetworkService {

	/**
	 * Default port for the embedded Grizzly server
	 */
	public static final int DEFAULT_PORT = 49154;

	private static Logger logger = Logger.getLogger(NetworkService.class);

	/**
	 * Creates a new Grizzly running on the default port.
	 * 
	 * @param rootDirectory
	 *            the directory to serve
	 */
	public NetworkService(Path rootDirectory) {
		this(DEFAULT_PORT, rootDirectory);
	}

	/**
	 * Creates a new Grizzly running on the given port.
	 * 
	 * @param port
	 *            the port to run Grizzly on
	 * @param rootDirectory
	 *            the directory to serve
	 */
	public NetworkService(int port, Path rootDirectory) {
		GrizzlyWebServer ws = new GrizzlyWebServer(port, rootDirectory
				.toString());
		try {
			ws.start();
			logger.info(String.format(
					"Grizzly started on port %1s serving %2s", port,
					rootDirectory));
		} catch (IOException e) {
			logger.fatal("Grizzly couldn't start.", e);
		}
	}

}
