package org.hhu.cs.p2p.net;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.sun.grizzly.http.embed.GrizzlyWebServer;

/**
 * Embedded WebServer for serving static files.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class NetworkService {

	private static Logger logger = Logger.getLogger(NetworkService.class);

	private static int CORE_THREADS = 10;

	private static int MAX_THREADS = 50;

	/**
	 * Default port for the embedded Grizzly server
	 */
	public static final int DEFAULT_PORT = 49154;

	private GrizzlyWebServer ws;

	private int port;

	private Path rootDirectory;

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
		this.port = port;
		this.rootDirectory = rootDirectory;
		this.ws = new GrizzlyWebServer(port, rootDirectory.toString());
		this.ws.setCoreThreads(CORE_THREADS);
		this.ws.setMaxThreads(MAX_THREADS);
	}

	/**
	 * Starts the embedded web server
	 */
	public void start() {
		try {
			ws.start();
			logger.info(String.format(
					"Grizzly started on port %1s serving %2s", port,
					rootDirectory));
		} catch (IOException e) {
			logger.fatal("Grizzly couldn't start.", e);
		}
	}

	public void shutdown() {
		ws.stop();
	}

	@Override
	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}

	public static void main(String[] args) {
		new NetworkService(8080, Paths.get("/Users/Oliver/Downloads/)")).start();
	}

}
