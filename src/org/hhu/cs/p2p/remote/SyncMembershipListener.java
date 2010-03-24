package org.hhu.cs.p2p.remote;

import org.apache.log4j.Logger;

import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

public class SyncMembershipListener implements MembershipListener {

	private static Logger logger = Logger
			.getLogger(SyncMembershipListener.class);

	@Override
	public void memberAdded(MembershipEvent event) {
		String message = "Welcome " + event.getMember().toString() + "!";
		logger.info(message);
	}

	@Override
	public void memberRemoved(MembershipEvent event) {
		// do nothing
	}

}
