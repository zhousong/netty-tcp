package com.tel.hello.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class HelloClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		/*
		 * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
		 * 
		 * 解码和编码 我将会在下一张为大家详细的讲解。再次暂时不做详细的描述
		 */
		// 以("\n")为结尾分割的 解码器
		// pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
		// Delimiters.lineDelimiter()));

		pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(
				Integer.MAX_VALUE, 0, 4, 0, 4));
		pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
		
		// 字符串解码 和 编码
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());

		// 客户端的逻辑
		pipeline.addLast("handler", new HelloClientHandler());
	}

}
