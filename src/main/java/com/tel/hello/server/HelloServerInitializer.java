package com.tel.hello.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// 以("\n")为结尾分割的 解码器
		// pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
		// Delimiters.lineDelimiter()));

		pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(
				Integer.MAX_VALUE, 0, 4, 0, 4));
		pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));

		// 字符串解码 和 编码
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());

		// 自己的逻辑Handler
		pipeline.addLast("handler", new HelloServerHandler());
	}
}
