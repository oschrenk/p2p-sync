package org.hhu.cs.p2p.index;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Holds information about a path.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class Attributes implements Serializable {

	private static final long serialVersionUID = 1L;

	private long lastModifiedTime;

	private boolean isDirectory;

	private InetSocketAddress address;

	private String hash;

	/**
	 * Construct a new {@link Attributes}
	 * 
	 * @param attributes
	 *            {@link BasicFileAttributes} of the {@link Path}
	 * @param address
	 *            the address of the member responsible
	 * @param hash
	 *            the hash value of the file, empty string if directory
	 */
	public Attributes(String hash, BasicFileAttributes attributes,
			InetSocketAddress address) {
		this.hash = hash;
		this.lastModifiedTime = attributes.lastModifiedTime().toMillis();
		this.isDirectory = attributes.isDirectory();
		this.address = address;
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

	/**
	 * @return the {@link InetSocketAddress} for the member that made the change
	 */
	public InetSocketAddress getAddress() {
		return address;
	}

	/**
	 * @return the hash value of the file, empty String if path is directory
	 */
	public String getHash() {
		return hash;
	}

	@Override
	public String toString() {
		return "Attributes [hash=" + hash + ", isDirectory=" + isDirectory
				+ ", lastModifiedTime=" + lastModifiedTime + ", address="
				+ address + "]";
	}

}
