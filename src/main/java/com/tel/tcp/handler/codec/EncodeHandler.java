package com.tel.tcp.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.msg.BaseMsg;

public class EncodeHandler extends MessageToByteEncoder<BaseMsg> {
	private static final Logger log = LoggerFactory
			.getLogger(EncodeHandler.class);
	private JavaObjectJsonCodec codec = new JavaObjectJsonCodec();

	@Override
	protected void encode(ChannelHandlerContext ctx, BaseMsg msg, ByteBuf out)
			throws Exception {
		log.debug("encode msg:" + msg);

		codec.encode(msg, out);
	}
}
