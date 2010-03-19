package org.hhu.cs.p2p.io;

import java.nio.file.attribute.BasicFileAttributes;

public class FileEntry {

	private BasicFileAttributes attributes;

	private String hash;

	public FileEntry(BasicFileAttributes attributes, String hash) {
		this.attributes = attributes;
		this.hash = hash;
	}

	public BasicFileAttributes getAttributes() {
		return attributes;
	}

	public void setLastModified(BasicFileAttributes attributes) {
		this.attributes = attributes;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
