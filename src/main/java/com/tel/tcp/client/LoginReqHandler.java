package com.tel.tcp.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.common.CMD;
import com.tel.tcp.common.MsgIdGen;
import com.tel.tcp.msg.BaseMsg;
import com.tel.tcp.msg.req.LoginReq;
import com.tel.tcp.msg.resp.LoginResp;

public class LoginReqHandler extends SimpleChannelInboundHandler<BaseMsg> {
	private static final Logger log = LoggerFactory
			.getLogger(LoginReqHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg)
			throws Exception {
		if (msg instanceof LoginResp) {
			log.info("login resp:" + msg);
			LoginResp resp = (LoginResp) msg;
			int code = resp.getCode();
			if (code == 200) {
				log.info("Login SUCCESS");

				// 登录成功后触发 HeartBeat 心跳
				((HeartBeatReqHandler) ctx.channel().pipeline()
						.get("heatBeatReqHandler")).fireHeartBeat(ctx);
			} else {
				log.info("Login FAIL");
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LoginReq loginReq = new LoginReq();
		loginReq.setId(MsgIdGen.id());
		loginReq.setCmd(CMD.Login.value());
		loginReq.setAccount("zhousong");
		loginReq.setPassword("password");
		ctx.channel().writeAndFlush(loginReq);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.close();
		log.info("channel Inactive");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		log.error("error", cause);
		super.exceptionCaught(ctx, cause);
	}
}
