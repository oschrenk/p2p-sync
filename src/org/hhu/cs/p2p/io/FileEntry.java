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

	private long lastAccessTime;

	public FileEntry(BasicFileAttributes attributes) {
		lastAccessTime = attributes.lastAccessTime().toMillis();
	}
}
