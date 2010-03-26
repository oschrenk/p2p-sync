package org.hhu.cs.p2p.index.comp;

import java.util.Comparator;

import org.apache.log4j.Logger;
import org.hhu.cs.p2p.index.Attributes;

/**
 * Compares {@link Attributes} via the has value. As hash values have no natural
 * order, they are two {@link Comparator}, one where the remote is by defintion
 * the winner {@link RemoteFavoredHashComparator}, and one where the local is
 * the winner {@link LocalFavoredHashComparator}.
 * 
 * @author Oliver Schrenk <oliver.schrenk@uni-duesseldorf.de>
 * 
 */
public class LocalFavoredHashComparator implements Comparator<Attributes> {

	private static Logger logger = Logger
			.getLogger(LocalFavoredHashComparator.class);

	/**
	 * returns 0 if hash values are equal, 1 otherwise, signaling that local is
	 * "newer"
	 */
	@Override
	public int compare(Attributes localAttributes, Attributes remoteAttributes) {
		String localHash = localAttributes.getHash();
		String remoteHash = remoteAttributes.getHash();

		if (logger.isTraceEnabled())
			logger
					.trace(String
							.format(
									"Comparing on hash favoring remote, local: %1s, remote %2s",
									localHash, remoteHash));

		if (localHash.equals(remoteHash)) {
			return 0;
		}
		return 1;
	}

}
