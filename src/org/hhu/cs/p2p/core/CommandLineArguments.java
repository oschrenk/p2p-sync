package org.hhu.cs.p2p.core;

import uk.co.flamingpenguin.jewel.cli.Option;

public interface CommandLineArguments {

	 @Option(shortName="d", longName = "directory", description="The directory to watch")
	 String getDirectory();
	
}
