package com.tel.tcp.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.msg.BaseMsg;
import com.tel.tcp.msg.push.IM;

public class IMHandler extends SimpleChannelInboundHandler<BaseMsg> {

	private static final Logger log = LoggerFactory.getLogger(IMHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg)
			throws Exception {
		if (msg instanceof IM) {
			IM im = (IM) msg;
			log.debug("receiveMSG:" + im);

		} else {
			ctx.fireChannelRead(msg);
		}
	}
}
