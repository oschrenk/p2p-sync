package org.hhu.cs.p2p.tasks;

import org.hhu.cs.p2p.index.Change;
import org.hhu.cs.p2p.local.LocalIndex;
import org.hhu.cs.p2p.remote.RemoteIndex;

/**
 * Contains the logic for builiding the different {@link Task}s based on the
 * change.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class TaskBuilder {

	private LocalIndex localIndex;

	private RemoteIndex remoteIndex;

	/**
	 * Constructs a new {@link TaskBuilder}
	 * 
	 * @param localIndex
	 *            the {@link LocalIndex}
	 * @param remoteIndex
	 *            the {@link RemoteIndex}
	 */
	public TaskBuilder(LocalIndex localIndex, RemoteIndex remoteIndex) {
		super();
		this.localIndex = localIndex;
		this.remoteIndex = remoteIndex;
	}

	/**
	 * @param change
	 * @return the {@link Task}
	 */
	public Task build(Change change) {
		switch (change.getDirection()) {
		case PUSH:
			return buildPushTask(change);
		case PULL:
			return buildPullTask(change);

		default:
			throw new IllegalArgumentException("Guru Meditation Failure.");
		}
	}

	private Task buildPushTask(Change change) {
		switch (change.getType()) {
		case CREATE:
			return new CreatePushTask(localIndex, remoteIndex, change.getPath());
		case UPDATE:
			return new UpdatePushTask(localIndex, remoteIndex, change.getPath());
		case DELETE:
			return new DeletePushTask(localIndex, remoteIndex, change.getPath());

		default:
			throw new IllegalArgumentException("Guru Meditation Failure.");
		}
	}

	private Task buildPullTask(Change change) {
		switch (change.getType()) {
		case CREATE:
			return new CreatePullTask(localIndex, remoteIndex, change.getPath());
		case UPDATE:
			return new UpdatePullTask(localIndex, remoteIndex, change.getPath());
		case DELETE:
			return new DeletePullTask(localIndex, remoteIndex, change.getPath());

		default:
			throw new IllegalArgumentException("Guru Meditation Failure.");
		}
	}
}
