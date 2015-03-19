package com.tel.tcp.handler.logic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.common.CMD;
import com.tel.tcp.manager.ClientChannelManager;
import com.tel.tcp.msg.BaseMsg;
import com.tel.tcp.msg.req.LoginReq;
import com.tel.tcp.msg.resp.LoginResp;
import com.tel.tcp.util.Util;

/**
 * 登录
 * 
 */
public class LoginReqHandler implements MessageHandler {
	private static final Logger log = LoggerFactory
			.getLogger(LoginReqHandler.class);

	@Override
	public void messageReceived(ChannelHandlerContext ctx, BaseMsg msg)
			throws Exception {
		if (msg instanceof LoginReq) {
			log.info("channelRead0", msg);

			LoginReq loginReq = (LoginReq) msg;

			LoginResp loginResp = new LoginResp();
			loginResp.setId(loginReq.getId());
			// ！！！注意：特殊处理，cmd + 1 ，与codec中解析时对应
			loginResp.setCmd(CMD.Login.value() + 1);
			// 用户名密码是否正确
			if ("zhousong".equals(loginReq.getAccount())
					&& "password".equals(loginReq.getPassword())) {
				// 登录成功后，缓存用户
				ClientChannelManager.onLoginSuccess(loginReq.getAccount(),
						Util.parseChannelRemoteAddr(ctx.channel()),
						ctx.channel());

				// success
				loginResp.setCode(200);
				loginResp.setPhone("13588009900");
			} else {
				// fail
				loginResp.setCode(100);
			}

			// 响应登录结果
			ctx.channel().writeAndFlush(loginResp);

			// 启动线程定时发送IM
			sendIM(loginReq.getAccount());
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	private void sendIM(final String account) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					IMPushHandler.pushIM(account);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
