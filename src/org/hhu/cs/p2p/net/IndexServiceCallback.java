package org.hhu.cs.p2p.net;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;

public class IndexServiceCallback implements EntryListener {

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
