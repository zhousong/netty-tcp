package com.tel.tcp.handler.codec;

import io.netty.buffer.ByteBuf;

import com.tel.tcp.msg.BaseMsg;

public interface Codec {
	void encode(BaseMsg msg, ByteBuf out);

	BaseMsg decode(ByteBuf buf);
}
