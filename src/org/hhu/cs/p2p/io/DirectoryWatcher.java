package org.hhu.cs.p2p.io;

import static java.nio.file.StandardWatchEventKind.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKind.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKind.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKind.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKind;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Logger;

public class DirectoryWatcher {

	static final Logger logger = Logger.getLogger(DirectoryWatcher.class
			.getName());

	private final WatchService watchService;
	private final Path directory;

	public DirectoryWatcher(String searchDir) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		watchService = fs.newWatchService();
		directory = fs.getPath(searchDir);
		directory.register(watchService, ENTRY_CREATE, ENTRY_MODIFY,
				ENTRY_DELETE, OVERFLOW);
		logger.info("registered watchService on " + directory);
	}

	public void run() throws IOException {

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