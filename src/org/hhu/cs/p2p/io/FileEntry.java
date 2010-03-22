package org.hhu.cs.p2p.io;

import java.io.Serializable;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class FileEntry implements Serializable {

	private static final long serialVersionUID = 1L;

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

	@Override
	public String toString() {
		return "FileEntry [attributes=" + attributes + ", hash=" + hash + "]";
	}
}
