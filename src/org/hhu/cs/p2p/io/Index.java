package org.hhu.cs.p2p.io;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Index {

	private Map<String, FileAttributes> map;

	private Path parentDirectory;

	public Index() {
		this.map = new HashMap<String, FileAttributes>();
	}

	public void put(String relativePath, FileAttributes attributes) {
		map.put(relativePath, attributes);
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public FileAttributes get(String key) {
		return map.get(key);
	}
}
