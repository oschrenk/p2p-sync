package org.hhu.cs.p2p.local;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.core.Registry;
import org.hhu.cs.p2p.index.Attributes;
import org.hhu.cs.p2p.io.AttributesReader;
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

	private AttributesReader attributesReader;

	/**
	 * @param rootDirectory
	 *            the directory to build the index for
	 */
	public LocalIndex(Path rootDirectory) {
		this.rootDirectory = rootDirectory.toAbsolutePath();
		this.map = new HashMap<Path, Attributes>();
		this.attributesReader = new AttributesReader(rootDirectory);

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
		Attributes attributes = new Attributes(getAddVersion(relativePath),
				attributesReader.getHash(relativePath), attributesReader
						.getBasicFileAttributes(relativePath), Registry
						.getInstance().getAddress());
		logger.info(String.format("Adding \"%1s\" with attributes %2s",
				relativePath, attributes));
		map.put(relativePath, attributes);
	}

	// TODO get rid of this method, API smell
	/**
	 * Adds a new entry to the index
	 * 
	 * @param relativePath
	 *            path relative to root directory
	 * @param attributes
	 *            the {@link Attributes} of the {@link Path}
	 */
	public synchronized void addFromRemote(Path relativePath,
			Attributes attributes) {
		logger.info(String.format(
				"Adding from remote \"%1s\" with attributes %2s", relativePath,
				attributes));
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
		Attributes attributes = new Attributes(getUpdateVersion(relativePath),
				attributesReader.getHash(relativePath), attributesReader
						.getBasicFileAttributes(relativePath), Registry
						.getInstance().getAddress());

		logger.info(String.format("Updating \"%1s\" with attributes %2s",
				relativePath, attributes));
		map.put(relativePath, attributes);
	}

	// TODO get rid of this method, API smell
	/**
	 * Adds a new entry to the index
	 * 
	 * @param relativePath
	 *            the {@link Path} (relative to root)
	 * @param attributes
	 *            the {@link Attributes} of the {@link Path}
	 */
	public synchronized void updateFromRemote(Path relativePath,
			Attributes attributes) {
		logger.info(String.format(
				"Updating from remote \"%1s\" with attributes %2s",
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
			rootDirectory.resolve(relativePath).deleteIfExists();
		} catch (IOException e) {
			logger.error(String.format("Error deleting %1s", relativePath), e);
		}
	}

	private int getAddVersion(Path relativePath) {
		synchronized (map) {
			Attributes a = map.get(relativePath);
			if (a == null) {
				return 0;
			}

			return a.getVersion();
		}
	}

	private int getUpdateVersion(Path relativePath) {
		synchronized (map) {
			Attributes a = map.get(relativePath);
			if (a == null) {
				logger.error(String.format(
						"Updating, but not attributes on %1s found",
						relativePath));
				return 0;
			}

			return a.getVersion() + 1;
		}
	}

	/**
	 * @param relativePath
	 *            the relative path
	 * @return the path attributes
	 */
	public Attributes get(Path relativePath) {
		return map.get(relativePath);
	}

	/**
	 * @return the map
	 */
	public Map<Path, Attributes> map() {
		return map;
	}
}
