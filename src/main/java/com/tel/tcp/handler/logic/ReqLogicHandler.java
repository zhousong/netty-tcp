package com.tel.tcp.handler.logic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.common.CMD;
import com.tel.tcp.msg.BaseMsg;

public class ReqLogicHandler extends SimpleChannelInboundHandler<BaseMsg> {
	private static final Logger log = LoggerFactory
			.getLogger(ReqLogicHandler.class);

	private HashMap<Integer, MessageHandler> handlerMap = new HashMap<Integer, MessageHandler>();

	public ReqLogicHandler() {
		handlerMap.put(CMD.Login.value(), new LoginReqHandler());
		handlerMap.put(CMD.HeartBeat.value(), new HeartBeatReqHandler());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg)
			throws Exception {
		int cmd = msg.getCmd();

		MessageHandler handler = handlerMap.get(cmd);
		log.info("CMD:" + msg.getCmd() + ",handler:" + handler);

		if (handler != null) {
			handler.messageReceived(ctx, msg);
		}
	}
}
