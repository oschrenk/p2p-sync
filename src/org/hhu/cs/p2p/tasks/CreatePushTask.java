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
public class CreatePushTask extends GenericTask {

	/**
	 * @param localIndex
	 * @param remoteIndex
	 * @param path
	 * @see GenericTask
	 */
	public CreatePushTask(LocalIndex localIndex, RemoteIndex remoteIndex,
			Path path) {
		super(localIndex, remoteIndex, path);
	}

	@Override
	public void execute() throws IOException {
		localIndex.add(path);
		remoteIndex.add(path, localIndex.get(path));
	}

}
