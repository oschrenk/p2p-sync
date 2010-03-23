package org.hhu.cs.p2p.io;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.log4j.Logger;

/**
 * 
 * Traverses a directory and adds them to a {@link DirectoryCache}.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class DirectoryVisitor implements FileVisitor<Path> {

	private static Logger logger = Logger.getLogger(DirectoryVisitor.class);

	private LocalIndex localIndex;

	/**
	 * Creates a new {@link DirectoryVisitor}
	 * 
	 * @param directoryIndex
	 *            the {@link DirectoryIndex} to hold the index
	 * 
	 * @param parentDirectory
	 *            the root of the directory to traverse
	 */
	protected DirectoryVisitor(LocalIndex directoryIndex, Path parentDirectory) {
		logger.info("Walking " + parentDirectory);
		this.localIndex = directoryIndex;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException e) {
		return CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir) {
		return CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectoryFailed(Path dir, IOException e) {
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
		if (logger.isTraceEnabled())
			logger.trace(String.format("Visiting %1s", file.toAbsolutePath()));
		try {
			localIndex.add(file);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException e) {
		return CONTINUE;
	}
}
