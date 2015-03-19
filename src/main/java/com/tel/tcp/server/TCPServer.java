package com.tel.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.handler.HandlerInitializer;

public class TCPServer {
	private static final Logger log = LoggerFactory.getLogger(TCPServer.class);
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 8088;

	public static void main(String[] args) {
		log.debug("================ start ===================");

		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workGroup = new NioEventLoopGroup(4);
		bootstrap.group(bossGroup, workGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.option(ChannelOption.SO_REUSEADDR, true)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_RCVBUF, 65535)
				.option(ChannelOption.SO_SNDBUF, 65535)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new HandlerInitializer());

		try {
			ChannelFuture future = bootstrap.bind(SERVER_IP, SERVER_PORT)
					.sync();
			log.debug("================ start end ===================");
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			log.debug("exception", e);
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
