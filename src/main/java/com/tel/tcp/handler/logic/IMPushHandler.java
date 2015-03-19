package com.tel.tcp.handler.logic;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.common.CMD;
import com.tel.tcp.common.Config.MsgType;
import com.tel.tcp.common.MsgIdGen;
import com.tel.tcp.manager.ClientChannelManager;
import com.tel.tcp.msg.push.IM;

public class IMPushHandler {

	private static final Logger log = LoggerFactory
			.getLogger(IMPushHandler.class);

	public static void pushIM(String account) {
		IM im = makeIM();

		log.debug("pushIM:" + im);
		Channel channel = ClientChannelManager.getChannelByAccount(account);
		log.debug("pushIM:" + channel);
		if (channel != null) {
			channel.writeAndFlush(im);
		}
	}

	public static IM makeIM() {
		IM im = new IM();
		im.setId(MsgIdGen.id());
		im.setCmd(CMD.PushIM.value());
		im.setFromAccount("leiyu");
		im.setToAccount("zhousong");
		im.setType(MsgType.p2pMsg);
		im.setTime(System.currentTimeMillis());
		im.setMsg("Hello 易信");

		return im;
	}

}
