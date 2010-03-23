package org.hhu.cs.p2p.core;

import org.hhu.cs.p2p.index.ChangeService;
import org.hhu.cs.p2p.io.LocalIndex;
import org.hhu.cs.p2p.net.RemoteIndex;

/**
 * Serves as a lightweight "service locator"
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class Registry {

	private Registry() {
	}

	private static class SingletonHolder {
		private static final Registry INSTANCE = new Registry();
	}

	/**
	 * @return Singelton instance
	 */
	public static Registry getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private LocalIndex localIndex;

	private RemoteIndex remoteIndex;

	private ChangeService changeService;

	/**
	 * @param changeService
	 */
	protected void setChangeService(ChangeService changeService) {
		this.changeService = changeService;
	}

	/**
	 * @param localIndex
	 */
	protected void setDirectoryIndex(LocalIndex localIndex) {
		this.localIndex = localIndex;
	}

	/**
	 * @param remoteIndex
	 */
	protected void setRemoteIndex(RemoteIndex remoteIndex) {
		this.remoteIndex = remoteIndex;
	}

	/**
	 * @return the local index
	 */
	public LocalIndex getLocalIndex() {
		return localIndex;
	}

	/**
	 * @return the remote index
	 */
	public RemoteIndex getRemoteIndex() {
		return remoteIndex;
	}

	/**
	 * @return the change service
	 */
	public ChangeService getChangeService() {
		return changeService;
	}

}
