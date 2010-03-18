package org.hhu.cs.p2p.core;

import uk.co.flamingpenguin.jewel.cli.CommandLineInterface;
import uk.co.flamingpenguin.jewel.cli.Unparsed;

@CommandLineInterface(application = "sync")
public interface StartupArguments {

	@Unparsed(name = "directory")
	String getDirectory();

}
