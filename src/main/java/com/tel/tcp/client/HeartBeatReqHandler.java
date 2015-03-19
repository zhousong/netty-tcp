package com.tel.tcp.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.common.CMD;
import com.tel.tcp.common.Config.HeartBeat;
import com.tel.tcp.common.MsgIdGen;
import com.tel.tcp.msg.BaseMsg;
import com.tel.tcp.msg.req.HeartBeatReq;
import com.tel.tcp.msg.resp.HeartBeatResp;

public class HeartBeatReqHandler extends SimpleChannelInboundHandler<BaseMsg> {
	private static final Logger log = LoggerFactory
			.getLogger(HeartBeatReqHandler.class);
	private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
			1);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg)
			throws Exception {
		if (msg instanceof HeartBeatResp) {
			log.debug("HeartBeatResp:" + msg);
			HeartBeatResp heartBeatResp = (HeartBeatResp) msg;

			if (200 == heartBeatResp.getCode()) {
				log.debug("HeartBeatResp:SUCCESS");
			} else {
				log.debug("HeartBeatResp:FAIL");
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	public void fireHeartBeat(final ChannelHandlerContext ctx) {
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				HeartBeatReq heartBearReq = new HeartBeatReq();
				heartBearReq.setId(MsgIdGen.id());
				heartBearReq.setCmd(CMD.HeartBeat.value());

				log.info("heartBeat:" + heartBearReq);
				ctx.channel().writeAndFlush(heartBearReq);
			}
		}, 0, HeartBeat.HeartBeatTime, TimeUnit.SECONDS);
	}
}
