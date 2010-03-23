package org.hhu.cs.p2p.index;

import java.nio.file.Path;

/**
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class Change {

	private Path path;

	private ChangeType type;

	private Direction direction;

	/**
	 * @param path
	 * @param type
	 * @param direction
	 */
	protected Change(Path path, ChangeType type, Direction direction) {
		super();
		this.path = path;
		this.type = type;
		this.direction = direction;
	}

	/**
	 * @return
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @return
	 */
	public ChangeType getType() {
		return type;
	}

	/**
	 * @return
	 */
	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "Change [path=" + path + ", type=" + type + ", direction="
				+ direction + "]";
	}
}
