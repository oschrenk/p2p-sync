package org.hhu.cs.p2p.io;

import static java.nio.file.StandardWatchEventKind.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKind.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKind.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKind.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKind;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.apache.log4j.Logger;

/**
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class DirectoryWatcher implements Runnable {

	private static final Logger logger = Logger
			.getLogger(DirectoryWatcher.class);

	private WatchService watchService;
	private final Path directory;

	/**
	 * Creates
	 * 
	 * @param directory
	 * @throws IOException
	 */
	public DirectoryWatcher(Path directory) {
		logger
				.info(String.format("Created DirectoryWatcher on %1s",
						directory));
		this.directory = directory;

		try {
			watchService = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			// shouldn't happen
			logger.fatal("Error retrieving FileSystem.", e);
		}

		DirectoryVisitor visitor = new DirectoryVisitor(directory);
		Files.walkFileTree(directory, visitor);
		DirectoryCache directoryCache = visitor.getDirectoryCache();

		logger.info("Done walking files.");

		try {
			directory.register(watchService, ENTRY_CREATE, ENTRY_MODIFY,
					ENTRY_DELETE, OVERFLOW);
		} catch (IOException e) {
			// shouldn't happen
			logger.fatal("Error registering WatchService.", e);
		}

		logger.info(String.format("Registered watchService on %1s", directory));
	}

	public void run() {
		logger.info(String.format("Started watching on %1s", directory));
		try {
			watch();
		} catch (IOException e) {
			logger.fatal(String.format("Error watching %1s", directory), e);
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void watch() throws IOException {
		logger.info(String.format("Watching %1s", directory));

		for (;;) {
			WatchKey key;
			try {
				key = watchService.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}

			for (WatchEvent<?> e : key.pollEvents()) {

				@SuppressWarnings("unchecked")
				WatchEvent<Path> event = (WatchEvent<Path>) e;

				Path fileName = event.context();
				Path child = directory.resolve(fileName);
				String contentType = Files.probeContentType(child);

				if (e.kind() == StandardWatchEventKind.ENTRY_CREATE) {
					logger.info("file was created: " + fileName
							+ " ; contentType: " + contentType);
				} else if (e.kind() == StandardWatchEventKind.ENTRY_MODIFY) {
					logger.info("file was modified: " + fileName
							+ " ; contentType: " + contentType);
				} else if (e.kind() == StandardWatchEventKind.ENTRY_DELETE) {
					logger.info("file was deleted: " + fileName);
				} else if (e.kind() == StandardWatchEventKind.OVERFLOW) {
					logger.info("overflow occurred");
					continue;
				}

				boolean valid = key.reset();
				if (!valid) {
					logger.info("object no longer registered");
					break;
				}
			}
		}
	}
}