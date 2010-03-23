package org.hhu.cs.p2p.core;

import java.nio.file.Path;

/**
 * Holds valid global options for the application.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class Options {

	private Path watchDirectory;

	private int port;

	/**
	 * Should only be constructed by {@link OptionsBuilder}
	 */
	protected Options() {
	}

	/**
	 * Sets the directory that should be watched.
	 * 
	 * @param watchDirectory
	 *            the directory that should be watched
	 */
	protected void setWatchDirectory(Path watchDirectory) {
		this.watchDirectory = watchDirectory;
	}

	/**
	 * Sets the port of the embedded webserver to serve files
	 * 
	 * @param port
	 *            the port of the embedded webserver to serve files
	 */
	protected void setPort(int port) {
		this.port = port;
	}

	/**
	 * Returns the port of the embedded webserver to serve files
	 * 
	 * @return the port of the embedded webserver to serve files
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the directory that should be watched
	 */
	public Path getWatchDirectory() {
		return watchDirectory;
	}

}
