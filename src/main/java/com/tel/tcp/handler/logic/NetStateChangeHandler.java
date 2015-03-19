package com.tel.tcp.handler.logic;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.manager.ClientChannelManager;
import com.tel.tcp.util.Util;

public class NetStateChangeHandler extends ChannelDuplexHandler {
	private static final Logger log = LoggerFactory
			.getLogger(NetStateChangeHandler.class);

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		log.info("channelRegistered");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		log.info("channelUnregistered");
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("channelActive");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("channelInactive");
		closeChannel(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;

			if (event.state() == IdleState.READER_IDLE) {
				log.info("IdleState.READER_IDLE");
				// 将触发channelInactive事件
				ctx.disconnect();

			} else if (event.state() == IdleState.WRITER_IDLE) {
				log.info("IdleState.WRITER_IDLE");

			} else if (event.state() == IdleState.ALL_IDLE) {
				log.info("IdleState.ALL_IDLE");

			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		log.info("exceptionCaught",cause);
		closeChannel(ctx);
	}

	private void closeChannel(ChannelHandlerContext ctx) {
		Channel channel = ctx.channel();
		final String addrRemote = Util.parseChannelRemoteAddr(channel);
		channel.close().addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				log.info(
						"closeChannel: close the connection to remote address[{}] result: {}",
						addrRemote, future.isSuccess());

				// 断网后，删除缓存的用户
				if (future.isSuccess()) {
					ClientChannelManager.onChannelClosed(addrRemote);
				}
			}
		});
	}

}
