package org.hhu.cs.p2p.io;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Holds the index of all files within a directory
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class DirectoryIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(DirectoryIndex.class);

	private Map<Path, FileEntry> map;

	/**
	 * Default constructor
	 */
	public DirectoryIndex() {
		this.map = new HashMap<Path, FileEntry>();
	}

	/**
	 * Adds a new entry to the index
	 * 
	 * @param relativePath
	 *            the relativePath (in respect to the directory that is being
	 *            watched)
	 * @param entry
	 *            infos about the entry
	 */
	public void add(Path relativePath, FileEntry entry) {
		logger.info(String.format("Adding \"%1s\" with attributes %2s",
				relativePath, entry));
		map.put(relativePath, entry);
	}

	/**
	 * Updates an entry in the index with new infos
	 * 
	 * @param relativePath
	 *            the relativePath (in respect to the directory that is being
	 *            watched)
	 * @param entry
	 *            new infos about the entry
	 */
	public void update(Path relativePath, FileEntry entry) {
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
	public void delete(Path relativePath) {
		logger.info(String.format("Deleting \"%1s\".", relativePath));
		map.remove(relativePath);
	}

	/**
	 * @return all relative paths in this index
	 */
	public Set<Path> paths() {
		return map.keySet();
	}

	/**
	 * @param key
	 *            the relative path
	 * @return the infos about the file ath that path
	 */
	public FileEntry get(Path key) {
		return map.get(key);
	}
}
