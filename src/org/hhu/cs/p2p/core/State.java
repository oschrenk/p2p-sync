package org.hhu.cs.p2p.core;

/**
 * Hols the state of the application.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public enum State {

	/**
	 * Application has started
	 */
	START,

	/**
	 * Application is done indexing the directory.
	 */
	INDEXING,

	/**
	 * Application is in cold synchronization phase with a peer, meaning that
	 * they exchange complete infos about their respective indices.
	 */
	COLD_SYNC,

	/**
	 * Application is in hot synchronization phasewith a peer, meaning that only
	 * changes to the index are propagated.
	 */
	HOT_SYNC;

}
