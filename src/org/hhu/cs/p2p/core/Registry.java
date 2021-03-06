package org.hhu.cs.p2p.core;

import java.net.InetSocketAddress;
import java.nio.file.Path;

import org.hhu.cs.p2p.local.LocalIndex;
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

	private Path rootDirectory;

	private InetSocketAddress address;

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
	 * @return the root directory
	 */
	public Path getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * @return {@link State} of application
	 */
	public State getState() {
		return state;
	}

	/**
	 * @return {@link InetSocketAddress} of the embedded webserver
	 */
	public InetSocketAddress getAddress() {
		return address;
	}

	/**
	 * @param state
	 *            {@link State} of application
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @param rootDirectory
	 */
	public void setRootDirecory(Path rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**
	 * @param address
	 *            the {@link InetSocketAddress} of this webserver
	 */
	public void setAddress(InetSocketAddress address) {
		this.address = address;
	}

}
