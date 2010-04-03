package org.hhu.cs.p2p.index;

import java.net.InetSocketAddress;
import java.nio.file.Path;

import com.hazelcast.core.EntryListener;

/**
 * Holds information about what has changed. Unfortunately we have to include
 * information about the originating source, as hazelcast offers no PAI top get
 * member info from {@link EntryListener}
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class Change {

	private Path path;

	private InetSocketAddress address;

	private ChangeType type;

	private Direction direction;

	/**
	 * @param path
	 * @param address
	 * @param type
	 * @param direction
	 */
	public Change(InetSocketAddress address, Path path, ChangeType type,
			Direction direction) {
		super();
		this.address = address;
		this.path = path;
		this.type = type;
		this.direction = direction;
	}

	/**
	 * @return {@link Path}
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @return {@link ChangeType}
	 */
	public ChangeType getType() {
		return type;
	}

	/**
	 * @return {@link Direction}
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @return {@link InetSocketAddress}
	 */
	public InetSocketAddress getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "Change [path=" + path + ", type=" + type + ", direction="
				+ direction + "]";
	}
}
