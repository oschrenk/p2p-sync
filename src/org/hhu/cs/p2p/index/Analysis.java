package org.hhu.cs.p2p.index;

import java.util.Set;

/**
 * A {@link Analysis} holds the changes (and conflicts) to sync two different
 * sets of paths
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class Analysis {

	Set<Change> changes;
	Set<TreeConflict> treeConflicts;

	/**
	 * @param changes
	 * @param treeConflicts
	 */
	protected Analysis(Set<Change> changes, Set<TreeConflict> treeConflicts) {
		super();
		this.changes = changes;
		this.treeConflicts = treeConflicts;
	}

	/**
	 * @return changes that should be made
	 */
	public Set<Change> getChanges() {
		return changes;
	}

	/**
	 * @return tree conflicts
	 */
	public Set<TreeConflict> getTreeConflicts() {
		return treeConflicts;
	}
}
