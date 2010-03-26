package org.hhu.cs.p2p.core;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.index.Analyser;
import org.hhu.cs.p2p.index.Analysis;
import org.hhu.cs.p2p.index.Attributes;
import org.hhu.cs.p2p.index.Change;
import org.hhu.cs.p2p.index.ConflictResolver;
import org.hhu.cs.p2p.index.LocalWinsConflictResolver;
import org.hhu.cs.p2p.index.RemoteWinsConflictResolver;
import org.hhu.cs.p2p.index.TreeConflict;
import org.hhu.cs.p2p.index.comp.AttributesVersionComparator;
import org.hhu.cs.p2p.index.comp.LocalFavoredHashComparator;
import org.hhu.cs.p2p.index.comp.RemoteFavoredHashComparator;
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

		IMap<String, Attributes> map = Hazelcast.getMap("p2p");

		Set<Member> members = Hazelcast.getCluster().getMembers();
		int size = members.size();
		if (size == 1) {
			init(map, size, new LocalWinsConflictResolver(),
					new LocalFavoredHashComparator());
		} else {
			init(map, size, new RemoteWinsConflictResolver(),
					new RemoteFavoredHashComparator());
		}
	}

	private void init(IMap<String, Attributes> map, int size,
			ConflictResolver conflictResolver, Comparator<Attributes> comparator) {
		Lock lock = Hazelcast.getLock(map);
		lock.lock();

		LocalIndex localIndex = Registry.getInstance().getLocalIndex();

		Analyser analyser = new Analyser(new AttributesVersionComparator());
		Analysis analysis = analyser.compare(localIndex.map(), map);
		Set<Change> changes;
		Set<TreeConflict> conflicts;

		changes = analysis.getChanges();
		conflicts = analysis.getTreeConflicts();

		changes.addAll(conflictResolver.resolve(conflicts));

		ChangeService changeService = Registry.getInstance().getChangeService();
		changeService.accept(changes);

		while (!changeService.isEmpty()) {
			// block
		}

		lock.unlock();
		logger.info("Initial index created.");
	}

}
