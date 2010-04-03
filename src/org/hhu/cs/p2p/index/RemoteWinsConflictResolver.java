package org.hhu.cs.p2p.index;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * All conflicts resolved with this {@link ConflictResolver} are won by remote,
 * meaning that
 * <ul>
 * <li>paths that exist remote but not local, are created on local</li>
 * <li>paths that exsts local but not remote, are deleted on local</li>
 * </ul>
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class RemoteWinsConflictResolver implements ConflictResolver {

	private static Logger logger = Logger
			.getLogger(RemoteWinsConflictResolver.class);

	@Override
	public Set<Change> resolve(final Set<TreeConflict> conflicts) {
		Set<Change> changes = new HashSet<Change>(conflicts.size());
		Iterator<TreeConflict> iter = conflicts.iterator();
		while (iter.hasNext()) {
			TreeConflict conflict = iter.next();

			logger.info(String.format("Resolving conflict: %1s", conflict));

			if (conflict.getExistence() == Existence.LOCAL) {
				changes.add(new Change(conflict.getLocalAttributes()
						.getAddress(), conflict.getPath(), ChangeType.DELETE,
						Direction.PULL));
			}

			else if (conflict.getExistence() == Existence.REMOTE) {
				changes.add(new Change(conflict.getRemoteAttributes()
						.getAddress(), conflict.getPath(), ChangeType.CREATE,
						Direction.PULL));
			}

		}
		return changes;
	}
}
