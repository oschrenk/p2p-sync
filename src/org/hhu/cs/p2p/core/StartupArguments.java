package org.hhu.cs.p2p.core;

import uk.co.flamingpenguin.jewel.cli.CommandLineInterface;
import uk.co.flamingpenguin.jewel.cli.Option;
import uk.co.flamingpenguin.jewel.cli.Unparsed;

@CommandLineInterface(application = "sync")
public interface StartupArguments {

	@Option(shortName="d", description="starts the service as a daemon")
	boolean isDaemon();
	
	@Unparsed(name = "directory")
	String getDirectory();

}
