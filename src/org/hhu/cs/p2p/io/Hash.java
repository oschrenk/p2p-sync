package org.hhu.cs.p2p.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;

public class Hash {

	 public static String calculateHash(MessageDigest algorithm,
	            Path file) throws IOException {
		 
	        BufferedInputStream bis = new BufferedInputStream(file.newInputStream());
	        DigestInputStream   dis = new DigestInputStream(bis, algorithm);

	        while (dis.read() != -1);
	        
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
