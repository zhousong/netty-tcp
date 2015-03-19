package com.tel.hello.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {
	private static final int portNumber = 7878;

	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new HelloServerInitializer());

			// 服务器绑定端口监听
			ChannelFuture channelFuture = b.bind(portNumber);
			channelFuture.sync();
			// 监听服务器关闭监听
			Channel channel = channelFuture.channel();
			ChannelFuture closeFuture = channel.closeFuture();
			closeFuture.sync();

			// 可以简写为
			/* b.bind(portNumber).sync().channel().closeFuture().sync(); */
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
