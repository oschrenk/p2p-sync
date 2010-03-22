package org.hhu.cs.p2p.core;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.io.DirectoryIndex;
import org.hhu.cs.p2p.io.DirectoryWatcher;

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
		logger.info("Starting application.");

		logger.info("Parsing arguments.");
		Cli<StartupArguments> cli = null;
		StartupArguments parsedArguments;
		try {
			cli = CliFactory.createCli(StartupArguments.class);
			parsedArguments = cli.parseArguments(args);
		} catch (ArgumentValidationException e) {
			logger.fatal(cli.getHelpMessage());
			return;
		}

		logger.info("Validating arguments.");
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
			DirectoryIndex directoryIndex = new DirectoryIndex(options
					.getWatchDirectory());
			DirectoryWatcher directoryWatcher = new DirectoryWatcher(
					directoryIndex, options.getWatchDirectory());
			directoryWatcher.run();
		} catch (IOException e) {
			logger.fatal(e);
		}
	}
}
