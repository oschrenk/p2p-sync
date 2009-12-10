package org.hhu.cs.p2p.core;

import java.io.File;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.hhu.cs.p2p.io.DirectoryWatcher;
import org.hhu.cs.p2p.net.IndexService;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.Cli;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

public class FolderSync {

	private static final String DAEMON_PID = "daemon.pid";

	private static Logger logger = Logger.getLogger(FolderSync.class);

	private static boolean shutdownRequest;

	private static Thread indexService;

	public static void main(String[] args) {

		Appender startupAppender = new ConsoleAppender(new SimpleLayout(),
				"System.err");

		logger.addAppender(startupAppender);

		// check argument syntax
		Cli<CommandLineArguments> cli = null;
		CommandLineArguments parsedArguments;
		try {
			cli = CliFactory.createCli(CommandLineArguments.class);
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

		// daemonize
		getPidFile().deleteOnExit();
		System.out.close();
		System.err.close();

		// make sure system shutdown softly
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				shutdown();
			}
		});

		// we don't need the console appender anymore
		logger.removeAppender(startupAppender);

		try {
			indexService = new Thread(new IndexService(new DirectoryWatcher(
					options.getWatchDirectory())));
			indexService.run();
		} catch (Throwable e) {
			// TODO log it

		}

		while (!isShutdownRequested()) {
			// just wait
		}

		// shutdown actions

	}

	/**
	 * Returns the path to the pid file
	 * 
	 * @return the path to the pid file
	 */
	private static File getPidFile() {
		return new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + DAEMON_PID);
	}

	/**
	 * Request a shutdown
	 */
	private static void shutdown() {
		shutdownRequest = true;
		try {
			indexService.join();
		} catch (InterruptedException e) {
			logger
					.error("Interrupted which waiting on main daemon thread to complete.");
		}
	}

	private static boolean isShutdownRequested() {
		return shutdownRequest;
	}

}
