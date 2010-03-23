package org.hhu.cs.p2p.core;

import org.hhu.cs.p2p.net.NetworkService;

import uk.co.flamingpenguin.jewel.cli.CommandLineInterface;
import uk.co.flamingpenguin.jewel.cli.Option;
import uk.co.flamingpenguin.jewel.cli.Unparsed;

/**
 * Collects the commandline arguments when starting the application, might still
 * contain invalid arguments.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
@CommandLineInterface(application = "sync")
public interface StartupArguments {

	/**
	 * Returns the port of the embedded webserver to serve files
	 * 
	 * @return the port of the embedded webserver to serve files
	 */
	@Option(shortName = "p", longName = "port", defaultValue = NetworkService.DEFAULT_PORT
			+ "", description = "the port of the embedded webserver to serve files")
	int getPort();

	/**
	 * Returns the directory that should be watched
	 * 
	 * @return the directory that should be watched
	 */
	@Unparsed(name = "directory")
	String getDirectory();

}
