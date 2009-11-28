package org.hhu.cs.p2p.core;

import java.nio.file.Path;

public class Options {

	private Path watchDirectory;

	protected Options(Path watchDirecory) {
		this.watchDirectory = watchDirecory;
	}

	public Path getWatchDirectory() {
		return watchDirectory;
	}

}
