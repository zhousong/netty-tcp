package com.tel.hello.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyDecoder extends LengthFieldBasedFrameDecoder {
	private static final Logger log = LoggerFactory
			.getLogger(NettyDecoder.class);

	// 8192 k
	private static final int FRAME_MAX_LENGTH = 8388608;

	public NettyDecoder() {
		super(FRAME_MAX_LENGTH, 0, 4, 0, 4);
	}

	@Override
	public Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		try {
			ByteBuf frame = (ByteBuf) super.decode(ctx, in);
			if (frame == null) {
				return null;
			}

			byte[] tmpBuf = new byte[frame.capacity()];
			frame.getBytes(0, tmpBuf);
			frame.release();

			String s = new String(tmpBuf);
			return s;
		} catch (Exception e) {
			log.error("ex", e);
		}

		return null;
	}
}