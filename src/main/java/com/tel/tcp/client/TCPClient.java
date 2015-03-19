package com.tel.tcp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.handler.codec.DecodeHandler;
import com.tel.tcp.handler.codec.EncodeHandler;
import com.tel.tcp.server.TCPServer;

public class TCPClient {
	private static final Logger log = LoggerFactory.getLogger(TCPServer.class);
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 8088;

	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();

		EventLoopGroup group = new NioEventLoopGroup();
		bootstrap.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true);

		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("encoder", new EncodeHandler());
				pipeline.addLast("decoder", new DecodeHandler(8 * 1024 * 1024,
						0, 4, 0, 4));
				pipeline.addLast("loginReqHandler", new LoginReqHandler());
				pipeline.addLast("heatBeatReqHandler",
						new HeartBeatReqHandler());
				pipeline.addLast("receiveIMHandler", new IMHandler());
			}
		});

		try {
			ChannelFuture future = bootstrap.connect(SERVER_IP, SERVER_PORT)
					.sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			log.error("error", e);
		} finally {
			group.shutdownGracefully();
		}
	}
}
