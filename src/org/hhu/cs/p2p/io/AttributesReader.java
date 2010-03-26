package org.hhu.cs.p2p.io;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.local.LocalIndexWatcher;
import org.hhu.cs.p2p.util.IOUtils;

/**
 * Reads the attributes from disk
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class AttributesReader {

	private static final Logger logger = Logger
			.getLogger(LocalIndexWatcher.class);

	private Path rootDirectory;

	/**
	 * @param rootDirectory
	 */
	public AttributesReader(Path rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**
	 * Returns the hash value of the path
	 * 
	 * @param relativePath
	 * @return the hash value of the path
	 * @throws IOException
	 *             if file can't be read
	 */
	public String getHash(Path relativePath) throws IOException {
		synchronized (rootDirectory) {
			Path absolutePath = rootDirectory.resolve(relativePath);
			if (logger.isTraceEnabled())
				logger.trace(String.format("Hashing %1s", absolutePath));

			return IOUtils.sha1(absolutePath);
		}
	}

	/**
	 * Returns the {@link BasicFileAttributes} of the {@link Path}
	 * 
	 * @param relativePath
	 * @return the {@link BasicFileAttributes} of the {@link Path}
	 * @throws IOException
	 *             if file can't be read
	 */
	public BasicFileAttributes getBasicFileAttributes(Path relativePath)
			throws IOException {
		synchronized (rootDirectory) {
			Path absolutePath = rootDirectory.resolve(relativePath);
			if (logger.isTraceEnabled())
				logger.trace(String.format(
						"Getting BasicFileAttributes of %1s", absolutePath));

			return java.nio.file.attribute.Attributes.readBasicFileAttributes(
					absolutePath, LinkOption.NOFOLLOW_LINKS);
		}

	}

}
