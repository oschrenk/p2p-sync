package org.hhu.cs.p2p.io;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Index {

	private Map<String, FileEntry> map;

	private Path parentDirectory;

	public Index() {
		this.map = new HashMap<String, FileEntry>();
	}

	public void put(String relativePath, FileEntry attributes) {
		map.put(relativePath, attributes);
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public FileEntry get(String key) {
		return map.get(key);
	}
}
