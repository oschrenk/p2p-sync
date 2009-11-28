package org.hhu.cs.p2p.core;

import java.io.IOException;

import org.hhu.cs.p2p.io.DirectoryWatcher;
import org.hhu.cs.p2p.net.IndexService;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

public class FolderSync {

	// Ordner anzeigen, ändern
	// Clients auflisten, hinzufügen, entfernen
	// Service stoppen
	// Debugging output an/aus

	public static void main(String[] args) {

		// check argument syntax
		CommandLineArguments parsedArguments;
		try {
			parsedArguments = CliFactory.parseArguments(
					CommandLineArguments.class, args);
		} catch (ArgumentValidationException e) {
			System.err.println(e.getMessage());
			return;
		}

		// check validity
		Options options;
		try {
			options = new OptionsBuilder().setArguments(parsedArguments)
					.build();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return;
		}

		try {
			new IndexService(new DirectoryWatcher(options.getWatchDirectory()));
		} catch (IOException e) {
			// can't happen because of validity check
		}
	}
}
