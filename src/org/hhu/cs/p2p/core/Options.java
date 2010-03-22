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

	/**
	 * Creates new {@link Options}
	 * 
	 * @param watchDirectory
	 */
	protected Options(Path watchDirectory) {
		this.watchDirectory = watchDirectory;
	}

	/**
	 * @return the directory that should be watched
	 */
	public Path getWatchDirectory() {
		return watchDirectory;
	}

}
