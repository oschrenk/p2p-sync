package org.hhu.cs.p2p.core;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.Attributes;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.AccessMode.*;

/**
 * Creates valid options for the startup of the sync application
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class OptionsBuilder {

	private String directory;

	/**
	 * Commodity method for setting all arguments at once using the JewelCLI
	 * interface
	 * 
	 * @param arguments
	 *            the arguments used for startup
	 * @return
	 */
	public OptionsBuilder setArguments(StartupArguments arguments) {
		this.directory = arguments.getDirectory();
		return this;
	}

	/**
	 * Build the startup options
	 * 
	 * @return valid startup options for the sync application
	 * @throws IllegalArgumentException
	 *             if the used arguments aren't semenatically correct
	 */
	public Options build() throws IllegalArgumentException {
		Path watchDirectory = getWatchDirectory(directory);

		return new Options(watchDirectory);
	}

	/**
	 * Returns the absolute {@link Path} to the directory that should be watched
	 * 
	 * @param path
	 * @return an absolute path to a directory that can be read and written to
	 * @throws IllegalArgumentException
	 *             if the path isn't a directory, or can't be read or written to
	 */
	private Path getWatchDirectory(String path) throws IllegalArgumentException {
		if (path == null) {
			throw new IllegalArgumentException("\"" + path
					+ "\" is not a valid directory.");
		}
		FileSystem fs = FileSystems.getDefault();
		Path directory = fs.getPath(path);
		BasicFileAttributes attributes;
		try {
			directory.checkAccess(READ, WRITE);
			attributes = Attributes.readBasicFileAttributes(directory,
					LinkOption.NOFOLLOW_LINKS);
		} catch (IOException e) {
			throw new IllegalArgumentException("\"" + path
					+ "\" is not a valid directory.");
		}
		if (!attributes.isDirectory()) {
			throw new IllegalArgumentException("\"" + path
					+ "\" is not a valid directory.");
		}
		return directory.toAbsolutePath();
	}
}