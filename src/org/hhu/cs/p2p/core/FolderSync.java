package org.hhu.cs.p2p.core;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.io.DirectoryWatcher;
import org.hhu.cs.p2p.net.IndexService;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.Cli;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

public class FolderSync {

	private static Logger logger = Logger.getLogger(FolderSync.class);

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
			IndexService indexService = new IndexService(d);
			new Thread(indexService).start();
		} catch (Throwable e) {
			logger.fatal(e);
		}
	}
}
