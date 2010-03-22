package org.hhu.cs.p2p.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Network {

	public static String getMacAddress() throws IllegalStateException {
		try {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);

			StringBuffer sb = new StringBuffer();
			byte[] mac = ni.getHardwareAddress();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i],
						(i < mac.length - 1) ? "-" : ""));
			}
			return sb.toString();
		} catch (NullPointerException e) {
			throw new IllegalStateException();
		} catch (UnknownHostException e) {
			throw new IllegalStateException();
		} catch (SocketException e) {
			throw new IllegalStateException();
		}
	}
}
