package org.hhu.cs.p2p.index;

import java.nio.file.Path;

/**
 * A {@link TreeConflict} describes the problem, that you cannot automatically
 * decide if a path should be deleted or kept, when comparing two different sets
 * of paths, of which you don't know the time of change.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class TreeConflict {

	private Path path;

	private Existence existence;

	/**
	 * @param path
	 * @param existence
	 */
	protected TreeConflict(Path path, Existence existence) {
		super();
		this.path = path;
		this.existence = existence;
	}

	/**
	 * Returns which path has the conflict
	 * 
	 * @return the path of the conflict
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Returns where the path currently exists
	 * 
	 * @return where the path currently exists
	 */
	public Existence getExistence() {
		return existence;
	}

}
