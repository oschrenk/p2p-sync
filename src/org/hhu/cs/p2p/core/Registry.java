package org.hhu.cs.p2p.core;

import org.hhu.cs.p2p.local.LocalIndex;
import org.hhu.cs.p2p.net.NetworkClient;
import org.hhu.cs.p2p.remote.RemoteIndex;

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

	private State state;

	private NetworkClient networkClient;

	/**
	 * @param changeService
	 */
	protected void setChangeService(ChangeService changeService) {
		this.changeService = changeService;
	}

	/**
	 * @param localIndex
	 */
	protected void setLocalIndex(LocalIndex localIndex) {
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

	/**
	 * @return the network client
	 */
	public NetworkClient getNetworkClient() {
		return networkClient;
	}

	/**
	 * @return {@link State} of application
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state
	 *            {@link State} of application
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @param networkClient
	 *            {@link NetworkClient}
	 */
	public void setNetworkClient(NetworkClient networkClient) {
		this.networkClient = networkClient;
	}

}
