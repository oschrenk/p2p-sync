package org.hhu.cs.p2p.tasks;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.local.LocalIndex;
import org.hhu.cs.p2p.remote.RemoteIndex;

/**
 * A task that pushes a change that occured remotely and pushing it into local
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class DeletePullTask extends GenericTask {

	private static Logger logger = Logger.getLogger(DeletePullTask.class);

	/**
	 * @param localIndex
	 * @param remoteIndex
	 * @param path
	 * @see GenericTask
	 */
	public DeletePullTask(LocalIndex localIndex, RemoteIndex remoteIndex,
			Path path) {
		super(localIndex, remoteIndex, path);
	}

	@Override
	public void execute() throws IOException {
		logger.info(String.format("Executing %1s on %2s", DeletePullTask.class,
				path));
		localIndex.delete(path);
	}
}