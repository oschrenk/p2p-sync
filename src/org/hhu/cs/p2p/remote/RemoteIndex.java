package org.hhu.cs.p2p.remote;

import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.index.Attributes;

import com.hazelcast.core.EntryListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;

/**
 * The remote index using hazelcast
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class RemoteIndex {

	private Logger logger = Logger.getLogger(RemoteIndex.class);

	private static final String MAP_NAME = "p2p";

	private IMap<String, Attributes> map;

	/**
	 * Default constructor
	 */
	public RemoteIndex() {
		logger.info("Getting hazelcast map.");

		map = Hazelcast.getMap(MAP_NAME);

		logger.info("IndexService created.");
	}

	/**
	 * @param path
	 * @param attributes
	 */
	public void add(Path path, Attributes attributes) {
		synchronized (map) {
			put(path.toString(), attributes);
		}
	}

	/**
	 * @param path
	 * @param attributes
	 */
	public void update(Path path, Attributes attributes) {
		synchronized (map) {
			put(path.toString(), attributes);
		}
	}

	/**
	 * @param path
	 */
	public void delete(Path path) {
		synchronized (map) {
			map.remove(path.toString());
		}
	}

	/**
	 * @param path
	 * @return
	 */
	public Attributes get(Path path) {
		synchronized (map) {
			return map.get(path.toString());
		}
	}

	/**
	 * @param entryListener
	 */
	public void addEntryListener(EntryListener<String, Attributes> entryListener) {
		map.addEntryListener(entryListener, true);
	}

	private void put(String path, Attributes attributes) {
		map.put(path, attributes);
	}

	@Override
	protected void finalize() throws Throwable {
		Hazelcast.shutdownAll();
		super.finalize();
	}
}
