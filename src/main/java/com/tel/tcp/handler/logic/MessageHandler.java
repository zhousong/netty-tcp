package com.tel.tcp.handler.logic;

import io.netty.channel.ChannelHandlerContext;

import com.tel.tcp.msg.BaseMsg;

public interface MessageHandler {

	void messageReceived(ChannelHandlerContext ctx, BaseMsg msg)
			throws Exception;
}
