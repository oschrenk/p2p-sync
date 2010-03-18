package org.hhu.cs.p2p.io;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class DirectoryVisitor implements FileVisitor<Path> {

	private static Logger logger = Logger.getLogger(DirectoryVisitor.class);

	private static final String DIGEST_ALGORITHM = "SHA1";

	private Index fileIndex;

	private MessageDigest messageDigest;

	private Path parentDirectory;

	public DirectoryVisitor(Path parentDirectory) {
		this.parentDirectory = parentDirectory.toAbsolutePath();
		this.fileIndex = new Index();
		try {
			this.messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			// doesn't happen
		}
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
		logger.info("Visting and analyzing " + file.toAbsolutePath().toString()
				+ " ...");
		try {
			Path absoluteFile = file.toAbsolutePath();
			fileIndex.put(parentDirectory.relativize(file.toAbsolutePath())
					.toString(), new FileAttributes(attributes, Hash
					.calculateHash(messageDigest, file)));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		logger.info("done.");
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException e) {
		return CONTINUE;
	}

	public Index getIndex() {
		return fileIndex;
	}

}
