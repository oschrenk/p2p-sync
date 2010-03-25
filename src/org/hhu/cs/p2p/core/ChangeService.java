package org.hhu.cs.p2p.core;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.index.Change;
import org.hhu.cs.p2p.local.LocalIndex;
import org.hhu.cs.p2p.remote.RemoteIndex;
import org.hhu.cs.p2p.tasks.TaskBuilder;

/**
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class ChangeService extends Thread {

	private Logger logger = Logger.getLogger(ChangeService.class);

	private final BlockingQueue<Change> queue;

	private boolean running;

	private TaskBuilder taskBuilder;

	/**
	 * @param remoteIndex
	 * @param localIndex
	 */
	public ChangeService(LocalIndex localIndex, RemoteIndex remoteIndex) {
		this.taskBuilder = new TaskBuilder(localIndex, remoteIndex);
		this.queue = new LinkedBlockingQueue<Change>();
		this.running = false;
	}

	@Override
	public void run() {
		logger.info("ChangeService running.");
		running = true;
		while (running)
			try {
				execute(queue.take());
			} catch (InterruptedException e) {
				logger.trace(e);
			}
		logger.info("ChangeService stopped.");
	}

	/**
	 * @param change
	 */
	public void accept(final Change change) {
		queue.add(change);
	}

	/**
	 * @param changes
	 */
	public void accept(final Set<Change> changes) {
		queue.addAll(changes);
	}

	/**
	 * @return
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	private void execute(final Change change) {
		new Thread(new Runnable() {
			public void run() {
				logger.info(String.format("Firing change: %1s", change));
				try {
					taskBuilder.build(change).execute();
				} catch (IOException e) {
					logger
							.error(String.format("%1s didn't execute", change),
									e);
				}

			}
		}).start();
	}

	/**
	 * Stops the service
	 */
	public void shutdown() {
		logger.info("Stopping ChangeService.");
		running = false;
		if (queue != null) {
			synchronized (queue) {
				queue.clear();
				running = false;
			}
		}

	}

	@Override
	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}
}
