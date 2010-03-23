package org.hhu.cs.p2p.net;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.io.PathAttributes;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;

/**
 * The remote index using hazelcast
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class RemoteIndex {

	private Logger logger = Logger.getLogger(RemoteIndex.class);

	private static final String MAP_NAME = "p2p";

	private IMap<String, PathAttributes> map;

	/**
	 * Default constructor
	 */
	public RemoteIndex() {
		logger.info("IndexService created.");

		logger.info("Getting hazelcast map.");
		map = Hazelcast.getMap(MAP_NAME);
		map.addEntryListener(new RemoteIndexWatcher<String, PathAttributes>(),
				true);

		Set<Member> members = Hazelcast.getCluster().getMembers();
		Iterator<Member> iter = members.iterator();
		while (iter.hasNext()) {
			Member m = iter.next();
			m.getInetSocketAddress();
		}

	}
}
