package org.hhu.cs.p2p.net;

import org.apache.log4j.Logger;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;

public class IndexService implements Runnable {

	private Logger logger = Logger.getLogger(IndexService.class);

	private IMap<String, String> map;

	public IndexService() {
		map = Hazelcast.getMap("global-index");
		logger.info("IndexService created.");
	}

	@Override
	public void run() {
		logger.info("IndexService running.");
	}

}
