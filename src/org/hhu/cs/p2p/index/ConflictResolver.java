package org.hhu.cs.p2p.index;

import java.util.Set;

/**
 * 
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public interface ConflictResolver {

	/**
	 * @param conflicts
	 * @return
	 */
	Set<Change> resolve(Set<TreeConflict> conflicts);

}
