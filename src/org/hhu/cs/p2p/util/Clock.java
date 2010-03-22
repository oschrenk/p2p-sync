package org.hhu.cs.p2p.util;

import java.io.Serializable;


public class Clock implements Serializable {

	private static final long serialVersionUID = 1L;

	private int time;
	
	public Clock() {
		time = 0;
	}
	
	public synchronized void tick() {
		time++;
	}
	
	public synchronized void set (int time) {
		if (time > 0 && this.time < time)
			this.time = time;
	}
	
	public synchronized int get() {
		return time;
	}
	
}
