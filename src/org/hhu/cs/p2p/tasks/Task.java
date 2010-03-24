package org.hhu.cs.p2p.tasks;

import java.io.IOException;

/**
 * Generic task interface
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public interface Task {

	/**
	 * Executes the task
	 * 
	 * @throws IOException
	 */
	void execute() throws IOException;

}
