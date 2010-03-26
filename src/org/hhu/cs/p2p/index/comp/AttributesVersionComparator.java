package org.hhu.cs.p2p.index.comp;

import java.util.Comparator;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.index.Attributes;

/**
 * Compares {@link Attributes} via the version number
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class AttributesVersionComparator implements Comparator<Attributes> {

	private static Logger logger = Logger
			.getLogger(DateModifiedComparator.class);

	/**
	 * @return -1 if local is older, 0 if same, 1 if local newer than remote
	 */
	@Override
	public int compare(Attributes localAttributes, Attributes remoteAttributes) {
		int localVersion = localAttributes.getVersion();
		int remoteVersion = remoteAttributes.getVersion();

		if (logger.isTraceEnabled())
			logger.trace(String.format(
					"Comparing versions, local: %1s, remote %2s", localVersion,
					remoteVersion));

		if (localVersion < remoteVersion) {
			return -1;
		} else if (localVersion == remoteVersion) {
			return 0;
		} else {
			return 1;
		}
	}

}