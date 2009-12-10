package org.hhu.cs.p2p.net;

import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

public class SyncMembershipListener implements MembershipListener {

	@Override
	public void memberAdded(MembershipEvent event) {
		String message = "Welcome " + event.getMember().toString() + "!";
		System.out.println(message);
	}

	@Override
	public void memberRemoved(MembershipEvent event) {
		// do nothing
	}

}
