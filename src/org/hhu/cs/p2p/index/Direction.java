package org.hhu.cs.p2p.index;

/**
 * {@link Direction} specifies in which direction changes are being made.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public enum Direction {

	/**
	 * Push changes to remote
	 */
	PUSH,

	/**
	 * Pull changes from remote
	 */
	PULL;
}
