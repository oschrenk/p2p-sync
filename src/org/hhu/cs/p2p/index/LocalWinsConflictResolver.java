package org.hhu.cs.p2p.index;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * All conflicts resolved with this {@link ConflictResolver} are won by the
 * local machine, meaning that
 * <ul>
 * <li>paths that exist remote but not local, are deleted on remote</li>
 * <li>paths that exsts local but not remote, are created on remote</li>
 * </ul>
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class LocalWinsConflictResolver implements ConflictResolver {

	@Override
	public Set<Change> resolve(final Set<TreeConflict> conflicts) {
		Set<Change> changes = new HashSet<Change>(conflicts.size());
		Iterator<TreeConflict> iter = conflicts.iterator();
		while (iter.hasNext()) {
			TreeConflict conflict = iter.next();

			if (conflict.getExistence() == Existence.LOCAL) {
				changes.add(new Change(conflict.getPath(), ChangeType.CREATE,
						Direction.PUSH));
			}

			else if (conflict.getExistence() == Existence.REMOTE) {
				changes.add(new Change(conflict.getPath(), ChangeType.DELETE,
						Direction.PUSH));
			}

		}
		return changes;
	}
}
