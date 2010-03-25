package org.hhu.cs.p2p.io;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.Attributes;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;



public class Test {

	public static void main(String[] args) {
		Path path = Paths.get("/Users/Oliver/Downloads/mail.txt");

		long time = 1269534100000l;

		Date d = new Date(time);
		System.out.println(d);

		try {

			BasicFileAttributes w = Attributes.readBasicFileAttributes(path, LinkOption.NOFOLLOW_LINKS);
			System.out.println(w.lastModifiedTime());
			
			BasicFileAttributeView v = path.getFileAttributeView(
					BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);

			System.out.println(v);
			v.setTimes(FileTime.fromMillis(time), FileTime.fromMillis(time),
					null);
			FileTime f = FileTime.fromMillis(time);
			System.out.println(f);
			
			w = Attributes.readBasicFileAttributes(path, LinkOption.NOFOLLOW_LINKS);
			System.out.println("After: " + w.lastModifiedTime());
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
