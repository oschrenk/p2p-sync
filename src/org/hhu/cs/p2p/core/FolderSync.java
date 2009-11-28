package org.hhu.cs.p2p.core;

import java.io.IOException;

import org.hhu.cs.p2p.io.DirectoryWatcher;

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
		
		// fire up service
		new FolderSync(options);
	}
	
	public FolderSync (Options options) {
		try {
			new DirectoryWatcher(options.getWatchDirectory()).run();
		} catch (IOException e) {
			// can't happen because of validity check
		}
	}

}
