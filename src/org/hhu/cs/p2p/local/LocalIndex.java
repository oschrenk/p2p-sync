package org.hhu.cs.p2p.local;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.Attributes;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.io.DirectoryVisitor;
import org.hhu.cs.p2p.io.PathAttributes;

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

	private Map<Path, PathAttributes> map;

	/**
	 * @param rootDirectory
	 *            the directory to build the index for
	 */
	public LocalIndex(Path rootDirectory) {
		this.rootDirectory = rootDirectory.toAbsolutePath();
		this.map = new HashMap<Path, PathAttributes>();

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
	 * @param path
	 *            the path (in respect to the directory that is being watched)
	 * @param entry
	 *            infos about the entry
	 * @throws IOException
	 *             if reading file attributes, or creating SHA-1 won't work
	 */
	public synchronized void add(Path path) throws IOException {
		PathAttributes entry = new PathAttributes(getAttributes(path));
		logger.info(String.format("Adding \"%1s\" with attributes %2s",
				path, entry));
		map.put(path, entry);
	}

	/**
	 * Updates an entry in the index with new infos
	 * 
	 * @param path
	 *            the path (in respect to the directory that is being watched)
	 * @param entry
	 *            new infos about the entry
	 * @throws IOException
	 */
	public synchronized void update(Path path) throws IOException {
		Path relativePath = rootDirectory.relativize(path.toAbsolutePath());
		PathAttributes entry = new PathAttributes(getAttributes(path));

		logger.info(String.format("Updating \"%1s\" with attributes %2s",
				relativePath, entry));
		map.put(relativePath, entry);
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
	}

	/**
	 * @param key
	 *            the relative path
	 * @return the path attributes
	 */
	public PathAttributes get(Path key) {
		return map.get(key);
	}

	private BasicFileAttributes getAttributes(Path relativePath)
			throws IOException {
		return Attributes.readBasicFileAttributes(rootDirectory
				.resolve(relativePath), LinkOption.NOFOLLOW_LINKS);
	}

	/**
	 * @return the map
	 */
	public Map<Path, PathAttributes> map() {
		return map;
	}
}