package com.tel.hello.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyEncoder extends MessageToByteEncoder<String> {
	private static final Logger log = LoggerFactory
			.getLogger(NettyEncoder.class);

	@Override
	public void encode(ChannelHandlerContext ctx, String msg, ByteBuf out)
			throws Exception {
		try {
			int length = msg.length();
			out.writeByte(length);

			byte[] body = msg.getBytes();
			if (body != null) {
				out.writeBytes(body);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}