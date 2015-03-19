package com.tel.tcp.handler.logic;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.common.CMD;
import com.tel.tcp.msg.BaseMsg;
import com.tel.tcp.msg.req.HeartBeatReq;
import com.tel.tcp.msg.resp.HeartBeatResp;

/**
 * 心跳
 * 
 */
public class HeartBeatReqHandler implements MessageHandler {

	private static final Logger log = LoggerFactory
			.getLogger(HeartBeatReqHandler.class);

	@Override
	public void messageReceived(ChannelHandlerContext ctx, BaseMsg msg)
			throws Exception {
		if (msg instanceof HeartBeatReq) {
			log.debug("HeartBeatReq:" + msg);
			HeartBeatReq heartBeatReq = (HeartBeatReq) msg;
			// 组装响应数据
			HeartBeatResp heartBeatResp = new HeartBeatResp();
			heartBeatResp.setId(heartBeatReq.getId());
			heartBeatResp.setCmd(CMD.HeartBeat.value() + 1);
			heartBeatResp.setCode(200);

			ctx.writeAndFlush(heartBeatResp);
		} else {
			ctx.fireChannelRead(msg);
		}
	}
}
