package com.tel.tcp.util;

import io.netty.channel.Channel;

import java.net.SocketAddress;

/**
 * util
 *
 */
public class Util {

	public static String parseChannelRemoteAddr(Channel channel) {
		if (null == channel) {
			return "";
		}
		final SocketAddress remote = channel.remoteAddress();
		final String addr = remote != null ? remote.toString() : "";

		if (addr.length() > 0) {
			int index = addr.lastIndexOf("/");
			if (index >= 0) {
				return addr.substring(index + 1);
			}

			return addr;
		}

		return "";
	}
}
