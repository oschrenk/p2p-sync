package org.hhu.cs.p2p.index;

import java.net.InetSocketAddress;
import java.nio.file.Path;

/**
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
	public Change(Path path, InetSocketAddress address, ChangeType type,
			Direction direction) {
		super();
		this.path = path;
		this.address = address;
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
