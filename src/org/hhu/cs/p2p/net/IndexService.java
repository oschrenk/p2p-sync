package org.hhu.cs.p2p.net;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.io.DirectoryWatcher;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;

public class IndexService implements Runnable {

	private Logger logger = Logger.getLogger("wagga");

	private IMap<String, String> map;

	private DirectoryWatcher directoryWatcher;

	public IndexService(DirectoryWatcher directoryWatcher) {
		logger.info("Index Service created.");

		map = Hazelcast.getMap("global-index");

		logger.info("after Index Service created.");
		// Hazelcast.getCluster().addMembershipListener(
		// new SyncMembershipListener());

		// Hazelcast.getCluster().getMembers();

		this.directoryWatcher = directoryWatcher;
		// this.map.addEntryListener(new ServiceCallback(), true);
	}

	@Override
	public void run() {
		logger.info("Index Service running.");
		new Thread(directoryWatcher).start();
	}

	private class ServiceCallback implements EntryListener {

		public void entryAdded(EntryEvent event) {
			event.getKey();

			// who added the file?
			// ignore if added via network

			// connect to the initial owner of the file

			// request the file

			// open new connection for file transfer

			// handle callback when finished

			// write the file to temp dir, copy it next to target and switch

			// optimize
			// try to collect multiple changes?
			// keep old copies as shadows?

		}

		public void entryRemoved(EntryEvent event) {
			// delete file
		}

		public void entryUpdated(EntryEvent event) {
			// handle like entry added

			// if time try to build binary diff

		}

		// TODO doc what is the difference to remove
		public void entryEvicted(EntryEvent event) {
			entryRemoved(event);
		}
	}

}
