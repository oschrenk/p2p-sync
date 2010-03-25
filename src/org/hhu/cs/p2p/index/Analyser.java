package org.hhu.cs.p2p.index;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hhu.cs.p2p.core.Registry;

/**
 * The {@link Analyser} returns an {@link Analysis} by comparing two different
 * {@link Map}s of {@link Path} to {@link Attributes}.
 * 
 * Changes occur if a path exists on both maps but one is newer.
 * 
 * Conflicts occur if a path exist in one map but not the other.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class Analyser {

	/**
	 * @param local
	 *            the map of the local machine
	 * @param remote
	 *            the map of the cloud
	 * @return
	 */
	public Analysis compare(final Map<Path, Attributes> local,
			final Map<String, Attributes> remote) {
		Set<TreeConflict> conflicts = new HashSet<TreeConflict>();
		Set<Change> changes = new HashSet<Change>();

		// we need a copy of the paths
		Set<String> clonedRemoteKeys = new HashSet<String>();
		for (String s : remote.keySet()) {
			clonedRemoteKeys.add(s);
		}

		Iterator<Path> iter;
		Path localIteratorPath;
		Attributes remoteAttributes;

		iter = local.keySet().iterator();
		while (iter.hasNext()) {
			localIteratorPath = iter.next();
			remoteAttributes = remote.get(localIteratorPath.toString());
			// does not exist in remote
			if (remoteAttributes == null) {
				conflicts.add(new TreeConflict(localIteratorPath, local
						.get(localIteratorPath), null, Existence.LOCAL));
			}
			// exists in remote
			else {
				changes.add(compareAttributes(localIteratorPath, local
						.get(localIteratorPath), remoteAttributes));

				// remove key from cloned list to reduce costs of next loop
				clonedRemoteKeys.remove(localIteratorPath);
			}
		} // done iterating local

		// WARNING reusing objects from above in while loop!
		// iterate rest of remote keys
		Iterator<String> clonedKeysIterator = clonedRemoteKeys.iterator();
		Path remoteIteratorClonedPath;
		Attributes localAttributes;
		while (clonedKeysIterator.hasNext()) {
			remoteIteratorClonedPath = Paths.get(clonedKeysIterator.next());
			localAttributes = local.get(remoteIteratorClonedPath);
			// does not exist in local
			if (localAttributes == null) {
				conflicts.add(new TreeConflict(remoteIteratorClonedPath, null,
						remote.get(remoteIteratorClonedPath.toString()),
						Existence.REMOTE));
			}
		}

		return new Analysis(changes, conflicts);
	}

	private Change compareAttributes(Path path, Attributes localAttributes,
			Attributes remoteAttributes) {
		long localModified = localAttributes.lastModifiedTime();
		long remoteModified = remoteAttributes.lastModifiedTime();

		if (localModified < remoteModified) {
			return new Change(path, remoteAttributes.getAddress(),
					ChangeType.UPDATE, Direction.PULL);
		} else {
			return new Change(path, Registry.getInstance().getAddress(),
					ChangeType.UPDATE, Direction.PUSH);
		}
	}
}
