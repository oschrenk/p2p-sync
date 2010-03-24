package org.hhu.cs.p2p.tasks;

import java.io.IOException;
import java.nio.file.Path;

import org.hhu.cs.p2p.local.LocalIndex;
import org.hhu.cs.p2p.remote.RemoteIndex;

/**
 * A task that pushes a change that occured remotely and pushing it into local
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class CreatePullTask extends GenericTask {

	/**
	 * @param localIndex
	 * @param remoteIndex
	 * @param path
	 * @see GenericTask
	 */
	public CreatePullTask(LocalIndex localIndex, RemoteIndex remoteIndex,
			Path path) {
		super(localIndex, remoteIndex, path);
	}

	@Override
	public void execute() throws IOException {
		// Registry.getInstance().getNetworkClient().request(socketAdress,
		// path);
		localIndex.add(path);
	}

}
