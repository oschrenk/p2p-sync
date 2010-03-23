package org.hhu.cs.p2p.net;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.io.DirectoryIndex;
import org.hhu.cs.p2p.io.FileEntry;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;

/**
 * The network services using hazelcast
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class IndexService implements Runnable {

	private Logger logger = Logger.getLogger(IndexService.class);

	private IMap<String, FileEntry> map;

	/**
	 * Default constructor
	 * 
	 * @param directoryIndex
	 */
	public IndexService(DirectoryIndex directoryIndex) {
		logger.info("IndexService created.");

		logger.info("Getting hazelcast map.");
		map = Hazelcast.getMap("p2p");
		map.addEntryListener(new IndexServiceCallback<String, FileEntry>(),
				true);

		Set<Member> members = Hazelcast.getCluster().getMembers();
		if (members.size() == 1) {
			logger.info("Creating initial index.");
			logger.info("Locking map.");
			Lock lock = Hazelcast.getLock(map);
			lock.lock();
			logger.info("Map locked.");
			Iterator<Path> iter = directoryIndex.iterator();
			while (iter.hasNext()) {
				Path p = iter.next();
				map.put(p.toString(), directoryIndex.get(p));
			}
			lock.unlock();
			logger.info("Map unlocked.");
		}
		
		Iterator<Member> iter = members.iterator();
		while (iter.hasNext()) {
			Member m = iter.next();
			m.getInetSocketAddress();
		}

	}

	@Override
	public void run() {

		logger.info("IndexService running.");
	}

}
