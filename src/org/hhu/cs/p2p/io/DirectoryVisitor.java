package org.hhu.cs.p2p.io;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Vector;

public class DirectoryVisitor implements FileVisitor<Path> {

	private List<Path> paths;

	public DirectoryVisitor() {
		this.paths = new Vector<Path>();
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
		paths.add(file);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException e) {
		return CONTINUE;
	}

	public List<Path> getPaths() {
		return paths;
	}

}
