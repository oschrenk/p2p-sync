package org.hhu.cs.p2p.local;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LocalWork {

	/**
	 * Holds the files currently being worked on
	 */
	private final Map<Path, Boolean> taskMap;

	public LocalWork() {
		this.taskMap = new HashMap<Path, Boolean>();
	}

	public void create() {
		// check if already working on
		
		// if so cancel the task
		
		// schedule attributes reader
		
		// if task done
		
			// add to LocalIndex
		
		// local index adds to remote index
		
		// remote index calls RemoteWork.create(Path)
		
		// new pulltask, delay a few seconds (might get quickly deleted)
	
		// if hash exists in localindex (moving files is delete/create combo)
			// move task
		
		// else download file to .filename-rnd
	
		// move file to location
	}

}
