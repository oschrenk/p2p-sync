package org.hhu.cs.p2p.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.local.LocalIndex;
import org.hhu.cs.p2p.local.LocalIndexWatcher;
import org.hhu.cs.p2p.net.NetworkService;
import org.hhu.cs.p2p.remote.RemoteIndex;
import org.hhu.cs.p2p.remote.RemoteIndexWatcher;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.Cli;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

/**
 * FolderSync creates a service that watches a directory for changes and
 * communicates these changes with other computers running the same service on a
 * local directory of their own.
 * 
 * The goal is to sync directories with the help of p2p technology.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class FolderSync {

	private static Logger logger = Logger.getLogger(FolderSync.class);

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Application started.");

		Cli<StartupArguments> cli = null;
		StartupArguments parsedArguments;
		try {
			cli = CliFactory.createCli(StartupArguments.class);
			parsedArguments = cli.parseArguments(args);
		} catch (ArgumentValidationException e) {
			logger.fatal(cli.getHelpMessage(), e);
			return;
		}

		Options options;
		try {
			options = new OptionsBuilder().setArguments(parsedArguments)
					.build();
		} catch (IllegalArgumentException e) {
			logger.fatal("Startup failed.", e);
			return;
		}

		// redirect hazelcast logging output
		System.setProperty("hazelcast.logging.type", "log4j");
		Registry registry = Registry.getInstance();

		try {
			Path rootDirectory = options.getWatchDirectory();

			// we need the address as hazelcast doesn't offer API
			registry.setAddress(new InetSocketAddress(InetAddress
					.getLocalHost(), options.getPort()));

			// root directory is needed globally
			registry.setRootDirecory(rootDirectory);

			final NetworkService networkService = new NetworkService(options
					.getPort(), rootDirectory);
			networkService.start();

			final LocalIndex localIndex = new LocalIndex(rootDirectory);

			final LocalIndexWatcher localIndexWatcher = new LocalIndexWatcher(
					rootDirectory);
			final RemoteIndex remoteIndex = new RemoteIndex();

			final ChangeService changeService = new ChangeService(localIndex,
					remoteIndex);
			new Thread(changeService).start();

			registry.setLocalIndex(localIndex);
			registry.setRemoteIndex(remoteIndex);
			registry.setChangeService(changeService);
			registry.setAddress(new InetSocketAddress(InetAddress
					.getLocalHost(), options.getPort()));

			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					changeService.shutdown();
					networkService.shutdown();
					localIndexWatcher.shutdown();
				}

			});

			// startup sequence
			new Startup().run();

			while (!changeService.isEmpty()) {
				// wait
			}

			// start watching
			remoteIndex.addEntryListener(new RemoteIndexWatcher());
			new Thread(localIndexWatcher).start();

		} catch (IOException e) {
			logger.fatal(e);
		}
	}
}
