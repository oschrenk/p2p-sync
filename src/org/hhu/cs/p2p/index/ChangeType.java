package org.hhu.cs.p2p.index;

/**
 * Type of change. Duh.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public enum ChangeType {

	/**
	 * Path will be created.
	 */
	CREATE,

	/**
	 * Content of oath has changed.
	 */
	UPDATE,

	/**
	 * Path will be deleted.
	 */
	DELETE;

}
