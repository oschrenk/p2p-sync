package org.hhu.cs.p2p.local;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.core.Registry;
import org.hhu.cs.p2p.index.Attributes;
import org.hhu.cs.p2p.io.DirectoryVisitor;

/**
 * Holds the index of all files within a directory
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class LocalIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(LocalIndex.class);

	private Path rootDirectory;

	private Map<Path, Attributes> map;

	/**
	 * @param rootDirectory
	 *            the directory to build the index for
	 */
	public LocalIndex(Path rootDirectory) {
		this.rootDirectory = rootDirectory.toAbsolutePath();
		this.map = new HashMap<Path, Attributes>();

		logger.info(String.format("Creating initial index of \"%1s\".",
				rootDirectory));
		DirectoryVisitor visitor = new DirectoryVisitor(this, rootDirectory);

		// will block io
		Files.walkFileTree(rootDirectory, visitor);

		logger.info(String.format("Done creating initial index of \"%1s\".",
				rootDirectory));
	}

	/**
	 * Adds a new entry to the index
	 * 
	 * @param relativePath
	 *            the {@link Path} (relative to root)
	 * @throws IOException
	 *             if error reading file attributes
	 */
	public synchronized void add(Path relativePath) throws IOException {
		Attributes attributes = new Attributes(getAttributes(rootDirectory,
				relativePath), Registry.getInstance().getAddress());
		logger.info(String.format("Adding \"%1s\" with attributes %2s",
				relativePath, attributes));
		map.put(relativePath, attributes);
	}

	/**
	 * Updates an entry in the index with new infos
	 * 
	 * @param relativePath
	 *            the {@link Path} (relative to root)
	 * @throws IOException
	 *             if error reading file attributes
	 */
	public synchronized void update(Path relativePath) throws IOException {
		Attributes attributes = new Attributes(getAttributes(rootDirectory,
				relativePath), Registry.getInstance().getAddress());

		logger.info(String.format("Updating \"%1s\" with attributes %2s",
				relativePath, attributes));
		map.put(relativePath, attributes);
	}

	/**
	 * Deletes an entry in the index
	 * 
	 * @param relativePath
	 *            the relativePath (in respect to the directory that is being
	 *            watched)
	 */
	public synchronized void delete(Path relativePath) {
		logger.info(String.format("Deleting \"%1s\".", relativePath));
		map.remove(relativePath);
		try {
			relativePath.deleteIfExists();
		} catch (IOException e) {
			logger.error(String.format("Error deleting %1s", relativePath), e);
		}
	}

	/**
	 * @param key
	 *            the relative path
	 * @return the path attributes
	 */
	public Attributes get(Path key) {
		return map.get(key);
	}

	private BasicFileAttributes getAttributes(Path rootDirectory,
			Path relativePath) throws IOException {
		Path absolutePath = rootDirectory.resolve(relativePath);
		if (logger.isTraceEnabled())
			logger.trace(String
					.format("Getting attributes of %s", absolutePath));

		return java.nio.file.attribute.Attributes.readBasicFileAttributes(
				absolutePath, LinkOption.NOFOLLOW_LINKS);
	}

	/**
	 * @return the map
	 */
	public Map<Path, Attributes> map() {
		return map;
	}
}
