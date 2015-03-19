package com.tel.hello.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelloClient {
	public static String host = "127.0.0.1";
	public static int port = 7878;

	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.channel(NioSocketChannel.class);
			b.handler(new HelloClientInitializer());

			// 连接服务端
			ChannelFuture connect = b.connect(host, port);
			connect.sync();
			Channel ch = connect.channel();

			// 控制台输入
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			for (;;) {
				String line = in.readLine();
				if (line == null) {
					continue;
				}
				/*
				 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾 之所以用\r\n结尾 是因为我们在handler中添加了
				 * DelimiterBasedFrameDecoder 帧解码。
				 */
				ch.writeAndFlush(line + "\ra\n");

				if ("quit".equalsIgnoreCase(line)) {
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// The connection is closed automatically on shutdown.
			group.shutdownGracefully();
		}
	}
}
