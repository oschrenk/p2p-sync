package org.hhu.cs.p2p.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Static helper.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 */
public class IOUtils {

	private static final String DIGEST_ALGORITHM_SHA1 = "SHA1";

	/**
	 * Returns the SHA-1 hash value of the contents of the given path
	 * 
	 * @param file
	 *            the path to the file
	 * @return
	 * @throws IOException
	 *             if path is a directory or hash algorithm doesn't work
	 *             correctly
	 */
	public static String sha1(Path file) throws IOException {
		String hash = null;
		try {
			hash = calculateHash(MessageDigest
					.getInstance(DIGEST_ALGORITHM_SHA1), file);
		} catch (NoSuchAlgorithmException e) {
			// shouldn't happen
			// maybe on some weird JVM, but we do not care for that
		}
		return hash;
	}

	/**
	 * Computes the hash value of the content of the given file
	 * 
	 * @param algorithm
	 *            the hash algorithm
	 * @param file
	 *            path to the file
	 * @return the hash code of the the content of the file in hexadecimal
	 *         notation
	 * @throws IOException
	 *             if path is a directory or hash algorithm doesn't work
	 *             correctly
	 */
	private static String calculateHash(MessageDigest algorithm, Path file)
			throws IOException {

		BufferedInputStream bis = new BufferedInputStream(file.newInputStream());
		DigestInputStream dis = new DigestInputStream(bis, algorithm);

		while (dis.read() != -1)
			;

		return byteArray2Hex(algorithm.digest());
	}

	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}
}
