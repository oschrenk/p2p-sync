package org.hhu.cs.p2p.tasks;

import java.io.IOException;
import java.nio.file.Path;

import org.hhu.cs.p2p.local.LocalIndex;
import org.hhu.cs.p2p.remote.RemoteIndex;

/**
 * A task that pushes a change that occured locally into the local index and
 * then into the remote index
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public abstract class GenericTask implements Task {

	protected LocalIndex localIndex;

	protected RemoteIndex remoteIndex;

	protected Path path;

	/**
	 * Creates a new task that pushes change that occured locally into the local
	 * index and then into the remote index
	 * 
	 * @param localIndex
	 *            the local index
	 * @param remoteIndex
	 *            the remote index
	 * @param path
	 *            the path
	 */
	public GenericTask(LocalIndex localIndex, RemoteIndex remoteIndex, Path path) {
		this.localIndex = localIndex;
		this.remoteIndex = remoteIndex;
		this.path = path;
	}

	@Override
	abstract public void execute() throws IOException;

}
