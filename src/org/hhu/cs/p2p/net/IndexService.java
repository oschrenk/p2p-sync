package org.hhu.cs.p2p.net;

import java.nio.file.Path;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;

public class IndexService {

	private final IMap<String, String> map = Hazelcast.getMap("global-index");
	
	private Path basePath;
	
	public IndexService(Path basePath) {
		this.basePath = basePath;
		 this.map.addEntryListener(new ServiceCallback(), true);
	}
	
	private class ServiceCallback implements EntryListener {
        
        public void entryAdded(EntryEvent event) {
           event.getKey();
        }

        public void entryRemoved(EntryEvent event) {
           
        }

        public void entryUpdated(EntryEvent event) {
            
        }

        public void entryEvicted(EntryEvent event) {
        }
    }
	
}
