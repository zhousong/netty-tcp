package com.tel.tcp.manager;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientChannelManager {
	private static final Logger log = LoggerFactory
			.getLogger(ClientChannelManager.class);

	private static ConcurrentHashMap<String, Channel> userIpChannelMap = new ConcurrentHashMap<String, Channel>();
	private static ConcurrentHashMap<String, String> accountUserIpMap = new ConcurrentHashMap<String, String>();
	private static ConcurrentHashMap<String, String> userIpAccountMap = new ConcurrentHashMap<String, String>();

	public static void onLoginSuccess(String account, String userIp,
			Channel channel) {
		userIpChannelMap.put(userIp, channel);
		userIpAccountMap.put(userIp, account);
		accountUserIpMap.put(account, userIp);

		log.debug("onLoginSuccess:userIpChannelMap:"
				+ userIpChannelMap.toString());
		log.debug("onLoginSuccess:userIpAccountMap:"
				+ userIpAccountMap.toString());
		log.debug("onLoginSuccess:accountUserIpMap:"
				+ accountUserIpMap.toString());
	}

	public static void onChannelClosed(String userIp) {
		userIpChannelMap.remove(userIp);
		String account = userIpAccountMap.remove(userIp);
		if (account != null) {
			accountUserIpMap.remove(account);
		}
		log.debug("onLoginSuccess:userIpChannelMap:"
				+ userIpChannelMap.toString());
		log.debug("onLoginSuccess:userIpAccountMap:"
				+ userIpAccountMap.toString());
		log.debug("onLoginSuccess:accountUserIpMap:"
				+ accountUserIpMap.toString());
	}

	public static Channel getChannelByAccount(String account) {
		String userIp = accountUserIpMap.get(account);
		if (userIp != null) {
			return userIpChannelMap.get(userIp);
		}
		return null;
	}

	public static void main(String[] args) {
		HashMap<String, NioServerSocketChannel> m = new HashMap<String, NioServerSocketChannel>();

		m.put("127.0.0.1:58932", new NioServerSocketChannel());

		System.out.println(m.get("127.0.0.1:58932"));
	}
}
