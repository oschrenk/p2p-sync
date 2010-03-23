package org.hhu.cs.p2p.io;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Holds information about a path.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class PathAttributes implements Serializable {

	private static final long serialVersionUID = 1L;

	private long lastModifiedTime;

	private boolean isDirectory;

	/**
	 * Construct a new {@link FileEntry}
	 * 
	 * @param attributes
	 *            {@link BasicFileAttributes} of the {@link Path}
	 */
	public PathAttributes(BasicFileAttributes attributes) {
		this.lastModifiedTime = attributes.lastModifiedTime().toMillis();
		this.isDirectory = attributes.isDirectory();
	}

	/**
	 * @return the time the path was last modified
	 */
	public long lastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * @return <code>true</code> if path is directory, <code>false</code>
	 *         otherwise
	 */
	public boolean isDirectory() {
		return isDirectory;
	}
}
