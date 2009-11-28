package org.hhu.cs.p2p.core;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.Attributes;
import java.nio.file.attribute.BasicFileAttributes;

public class OptionsBuilder {

	private Path watchDirectory;

	public OptionsBuilder setArguments(CommandLineArguments arguments) {

		return build(arguments);
	}

	private OptionsBuilder build(CommandLineArguments arguments) {
		setWatchDirectory(arguments.getDirectory());
		return this;
	}

	public OptionsBuilder setWatchDirectory(String path) {
		FileSystem fs = FileSystems.getDefault();
		Path directory = fs.getPath(path);
		BasicFileAttributes attributes;
		try {
			attributes = Attributes.readBasicFileAttributes(directory,
					LinkOption.NOFOLLOW_LINKS);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		if (!attributes.isDirectory()) {
			throw new IllegalArgumentException(
					"Not a valid directory. Change path.");
		}

		this.watchDirectory = directory;
		return this;
	}

	public Options build() throws IllegalArgumentException {
		// TODO check options
		return new Options(watchDirectory);
	}

}