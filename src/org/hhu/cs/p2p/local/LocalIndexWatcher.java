package org.hhu.cs.p2p.local;

import static java.nio.file.StandardWatchEventKind.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKind.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKind.ENTRY_MODIFY;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKind;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.Attributes;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class LocalIndexWatcher implements Runnable {

	private static final Logger logger = Logger
			.getLogger(LocalIndexWatcher.class);

	private final WatchService watchService;

	private final Map<WatchKey, Path> keys;

	private final LocalIndex localIndex;

	private final Path rootDirectory;

	/**
	 * Creates a {@link DirectoryWatcher}
	 * 
	 * @param localIndex
	 * 
	 * @param rootDirectory
	 * @throws IOException
	 */
	public LocalIndexWatcher(LocalIndex localIndex, Path rootDirectory)
			throws IOException {
		this.watchService = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey, Path>();
		this.rootDirectory = rootDirectory;
		this.localIndex = localIndex;

		logger.info(String.format("Created DirectoryWatcher on %1s",
				rootDirectory));

		try {
			registerRecursively(rootDirectory);
		} catch (IOException e) {
			throw new IOException("Error registering WatchService.", e);
		}

		logger.info(String.format("Registered watchService on %1s",
				rootDirectory));
	}

	public void run() {
		logger.info(String.format("Started watching on %1s", rootDirectory));
		try {
			watch();
		} catch (IOException e) {
			logger.fatal(String.format("Error watching %1s", rootDirectory), e);
		}
	}

	private void register(Path directory) throws IOException {
		WatchKey key = directory.register(watchService, ENTRY_CREATE,
				ENTRY_DELETE, ENTRY_MODIFY);
		keys.put(key, directory);
	}

	private void registerRecursively(final Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir) {
				try {
					register(dir);
				} catch (IOException x) {
					throw new IOError(x);
				}
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void watch() throws IOException {
		logger.info(String.format("Watching %1s", rootDirectory));

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

				Path parentDirectory = keys.get(key);
				Path child = parentDirectory.resolve(event.context());

				if (e.kind() == StandardWatchEventKind.ENTRY_CREATE) {
					BasicFileAttributes pathAttributes = Attributes
							.readBasicFileAttributes(child,
									LinkOption.NOFOLLOW_LINKS);
					if (logger.isTraceEnabled())
						logger.trace("Path was created: " + child);
					if (pathAttributes.isDirectory()) {
						register(child);
					} else {
						localIndex.add(child);
					}
				} else if (e.kind() == StandardWatchEventKind.ENTRY_MODIFY) {
					BasicFileAttributes pathAttributes = Attributes
							.readBasicFileAttributes(child,
									LinkOption.NOFOLLOW_LINKS);
					if (logger.isTraceEnabled())
						logger.trace("Path was modified: " + child);

					if (pathAttributes.isDirectory()) {
						// do nothing
					} else {
						localIndex.update(child);
					}

				} else if (e.kind() == StandardWatchEventKind.ENTRY_DELETE) {
					if (logger.isTraceEnabled())
						logger.trace("Path was deleted: " + child);
				} else if (e.kind() == StandardWatchEventKind.OVERFLOW) {
					logger.info("Overflow occurred");
					continue;
				}

				boolean valid = key.reset();
				if (!valid) {
					keys.remove(key);

					// all directories are inaccessible
					if (keys.isEmpty()) {
						break;
					}

					logger.info("object no longer registered");
					break;
				}
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		for (WatchKey key : keys.keySet()) {
			key.cancel();
		}

		watchService.close();
	}
}