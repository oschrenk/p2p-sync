package org.hhu.cs.p2p.core;

import java.util.Set;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.index.Analyser;
import org.hhu.cs.p2p.index.Analysis;
import org.hhu.cs.p2p.index.Attributes;
import org.hhu.cs.p2p.index.Change;
import org.hhu.cs.p2p.index.ConflictResolver;
import org.hhu.cs.p2p.index.LocalWinsConflictResolver;
import org.hhu.cs.p2p.index.TreeConflict;
import org.hhu.cs.p2p.local.LocalIndex;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;

/**
 * Startup sequence.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class Startup {

	private static Logger logger = Logger.getLogger(Startup.class);

	void run() {

		logger.info("Getting hazelcast map.");

		IMap<String, Attributes> map = Hazelcast.getMap("p2p");

		Set<Member> members = Hazelcast.getCluster().getMembers();
		if (members.size() == 1) {
			logger.info("Creating initial index.");
			logger.info("Locking map.");
			Lock lock = Hazelcast.getLock(map);
			lock.lock();
			logger.info("Map locked.");

			LocalIndex directoryIndex = Registry.getInstance().getLocalIndex();

			Analysis analysis = new Analyser().compare(directoryIndex.map(),
					map);
			ConflictResolver resolver = new LocalWinsConflictResolver();
			Set<Change> changes;
			Set<TreeConflict> conflicts;

			changes = analysis.getChanges();
			conflicts = analysis.getTreeConflicts();

			changes.addAll(resolver.resolve(conflicts));

			ChangeService changeService = Registry.getInstance()
					.getChangeService();
			changeService.accept(changes);

			while (!changeService.isEmpty()) {
				// block
			}

			lock.unlock();
			logger.info("Map unlocked.");
		}
	}

}
