package org.hhu.cs.p2p.index;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

/**
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class ChangeService extends Thread {

	private Logger logger = Logger.getLogger(ChangeService.class);

	private final BlockingQueue<Change> queue;

	private boolean running;

	/**
	 * Default constructor
	 */
	public ChangeService() {
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
				logger.info("Executing change " + change);
				// TODO make the change
			}
		}).start();
	}

	/**
	 * Stops the service
	 */
	public void stopService() {
		logger.info("Stopping ChangeService.");
		running = false;
	}

	@Override
	protected void finalize() throws Throwable {
		stopService();
		super.finalize();
	}

}
