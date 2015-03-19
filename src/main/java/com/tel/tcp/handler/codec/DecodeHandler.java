package com.tel.tcp.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tel.tcp.msg.BaseMsg;

public class DecodeHandler extends LengthFieldBasedFrameDecoder {
	private static final Logger log = LoggerFactory
			.getLogger(DecodeHandler.class);
	private JavaObjectJsonCodec codec = new JavaObjectJsonCodec();

	public DecodeHandler(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);

		if (frame == null) {
//			throw new NullPointerException("frame is null");
			return null;
		}

		BaseMsg msg = codec.decode(frame);

		log.debug("decode msg:" + msg);
		return msg;
	}
}
