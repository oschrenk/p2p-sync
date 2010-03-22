package org.hhu.cs.p2p.core;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.io.DirectoryWatcher;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.Cli;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

/**
 * FolderSync creates a service that watches a directory for changes and
 * communicates these changes with other computers running the same service on a
 * local directory of their own.
 * 
 * The goal is to sync directories with the help of peer 2 peer technology.
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

		// check argument syntax
		Cli<StartupArguments> cli = null;
		StartupArguments parsedArguments;
		try {
			cli = CliFactory.createCli(StartupArguments.class);
			parsedArguments = cli.parseArguments(args);
		} catch (ArgumentValidationException e) {
			logger.fatal(cli.getHelpMessage());
			return;
		}

		// check validity
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

		try {
			DirectoryWatcher d = new DirectoryWatcher(options
					.getWatchDirectory());
			d.run();
		} catch (Throwable e) {
			logger.fatal(e);
		}
	}
}
