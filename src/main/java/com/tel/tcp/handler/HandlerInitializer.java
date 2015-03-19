package com.tel.tcp.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import com.tel.tcp.handler.codec.DecodeHandler;
import com.tel.tcp.handler.codec.EncodeHandler;
import com.tel.tcp.handler.logic.ReqLogicHandler;
import com.tel.tcp.handler.logic.NetStateChangeHandler;

public class HandlerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// 序列化
		pipeline.addLast("encodeHandler", new EncodeHandler());
		// 反序列化
		pipeline.addLast("decodeHandler", new DecodeHandler(8 * 1024 * 1024, 0,
				4, 0, 4));
		// 通讯空闲监测
		pipeline.addLast("idleMonitor", new IdleStateHandler(60, 0, 0));
		pipeline.addLast("idleHandler", new NetStateChangeHandler());
		// 业务逻辑处理Handler
		pipeline.addLast("logicHandler", new ReqLogicHandler());
	}
}
