package org.hhu.cs.p2p.core;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

public class FolderSync {

	// Ordner anzeigen, ändern
	// Clients auflisten, hinzufügen, entfernen
	// Service stoppen
	// Debugging output an/aus

	public static void main(String[] args) {

		CommandLineArguments parsedArguments;
		try {
			parsedArguments = CliFactory.parseArguments(
					CommandLineArguments.class, args);
		} catch (ArgumentValidationException e) {
			System.err.println(e.getMessage());
			return;
		}

		Options options;
		try {
			options = new OptionsBuilder().setArguments(parsedArguments)
					.build();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return;
		}
		
	}

}
