package org.hhu.cs.p2p.net;

import org.apache.log4j.Logger;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;

/**
 * @author Oliver
 * 
 * @param <Path>
 * @param <FileEntry>
 */
public class RemoteIndexWatcher<Path, FileEntry> implements
		EntryListener<Path, FileEntry> {

	private static Logger logger = Logger.getLogger(RemoteIndexWatcher.class);

	public void entryAdded(EntryEvent<Path, FileEntry> event) {
		logger.info(String.format("Adding entry %1s", event.getKey()));

		// who added the file?
		// ignore if added via network

		// connect to the initial owner of the file

		// request the file

		// open new connection for file transfer

		// handle callback when finished
	}

	public void entryRemoved(EntryEvent<Path, FileEntry> event) {
		logger.info(String.format("Deleting entry %1s", event.getKey()));
	}

	public void entryUpdated(EntryEvent<Path, FileEntry> event) {
		logger.info(String.format("Updating entry %1s", event.getKey()));
		// handle like entry added
	}

	@Override
	public void entryEvicted(EntryEvent<Path, FileEntry> event) {
		logger.info(String.format("Evecting entry %1s", event.getKey()));
	}

}
