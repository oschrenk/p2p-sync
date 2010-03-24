package org.hhu.cs.p2p.tasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.core.Registry;
import org.hhu.cs.p2p.index.Attributes;
import org.hhu.cs.p2p.local.LocalIndex;
import org.hhu.cs.p2p.net.NetworkClient;
import org.hhu.cs.p2p.remote.RemoteIndex;

/**
 * A task that pushes a change that occured remotely and pushing it into local
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class CreatePullTask extends GenericTask {

	private static Logger logger = Logger.getLogger(CreatePullTask.class);

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
		Attributes attributes = remoteIndex.get(path);
		try {
			// TODO get rid of getRootDirectory, stupid (non)dependency
			new NetworkClient(Registry.getInstance().getRootDirectory())
					.request(attributes.getAddress(), path.toString());
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		localIndex.add(path);
	}
}