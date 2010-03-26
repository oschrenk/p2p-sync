package org.hhu.cs.p2p.index.comp;

import java.util.Comparator;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.index.Attributes;

/**
 * Compares {@link Attributes} via the last modified date
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class DateModifiedComparator implements Comparator<Attributes> {

	private static Logger logger = Logger
			.getLogger(DateModifiedComparator.class);

	/**
	 * @return -1 if local is older, 0 if same, 1 if local newer than remote
	 */
	@Override
	public int compare(Attributes localAttributes, Attributes remoteAttributes) {
		long localModified = localAttributes.lastModifiedTime();
		long remoteModified = remoteAttributes.lastModifiedTime();

		if (logger.isTraceEnabled())
			logger.trace(String.format(
					"Comparing times, local: %1s, remote %2s", localModified,
					remoteModified));

		if (localModified < remoteModified) {
			return -1;
		} else if (localModified == remoteModified) {
			return 0;
		} else {
			return 1;
		}
	}

}
