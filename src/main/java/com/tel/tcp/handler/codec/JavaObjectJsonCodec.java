package com.tel.tcp.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tel.tcp.common.CMD;
import com.tel.tcp.handler.logic.IMPushHandler;
import com.tel.tcp.msg.BaseMsg;
import com.tel.tcp.msg.push.IM;
import com.tel.tcp.msg.req.HeartBeatReq;
import com.tel.tcp.msg.req.LoginReq;
import com.tel.tcp.msg.resp.HeartBeatResp;
import com.tel.tcp.msg.resp.LoginResp;

public class JavaObjectJsonCodec implements Codec {
	private static final Logger log = LoggerFactory
			.getLogger(JavaObjectJsonCodec.class);

	@Override
	public void encode(BaseMsg msg, ByteBuf out) {
		String json = JSON.toJSONString(msg);
		// 消息Json占用的byte字节数
		byte[] bytes = json.getBytes();
		int len = bytes.length;

		// 需要加上后面的id 和 cmd 占据的12个字节数
		out.writeInt(len + 12);
		out.writeLong(msg.getId());
		out.writeInt(msg.getCmd());
		out.writeBytes(bytes);
	}

	@Override
	public BaseMsg decode(ByteBuf buf) {
		long id = buf.readLong();
		int cmd = buf.readInt();

		BaseMsg msg = null;
		int readableBytes = buf.readableBytes();
		byte[] bytes = new byte[readableBytes];
		buf.readBytes(bytes, 0, readableBytes);
		log.debug("decodeMSG:" + new String(bytes));

		if (cmd == CMD.Login.value()) {// Login
			msg = JSON.parseObject(bytes, LoginReq.class);
		} else if (cmd == CMD.Login.value() + 1) {// LoginResp
			msg = JSON.parseObject(bytes, LoginResp.class);
		} else if (cmd == CMD.HeartBeat.value()) {// HeartBeat
			msg = JSON.parseObject(bytes, HeartBeatReq.class);
		} else if (cmd == CMD.HeartBeat.value() + 1) {// HeartBeatReq
			msg = JSON.parseObject(bytes, HeartBeatResp.class);
		} else if (cmd == CMD.PushIM.value()) {// PushIM
			msg = JSON.parseObject(bytes, IM.class);
		}

		msg.setId(id);
		return msg;
	}

	public static void main(String[] args) {
		byte b = (byte) 112;
		System.out.println((char) b);
		byte bb = (byte) 101;
		System.out.println((char) bb);

		String s = "{\"cmd\":3002,\"fromAccount\":\"leiyu\",\"id\":0,\"msg\":\"Hello 易信\",\"time\":1426731996679,\"toAccount\":\"zhousong\",\"type\":1}";

		System.out.println(s.getBytes().length);
		// testLoginReq();
		// testIM();
	}

	private static void testLoginReq() {
		LoginReq loginReq = new LoginReq();
		loginReq.setId(1234567890l);
		loginReq.setCmd(1000);
		loginReq.setAccount("zhousong");
		loginReq.setPassword("pwd");
		ByteBuf buffer = Unpooled.buffer();
		JavaObjectJsonCodec codec = new JavaObjectJsonCodec();
		codec.encode(loginReq, buffer);

		// 先读取head 4个字节
		int length = buffer.readInt();
		System.out.println("loginReqLength:" + length);
		System.out.println(codec.decode(buffer));
	}

	private static void testIM() {
		System.out.println("*******************************");
		IM im = IMPushHandler.makeIM();
		ByteBuf buffer = Unpooled.buffer();
		JavaObjectJsonCodec codec = new JavaObjectJsonCodec();
		codec.encode(im, buffer);

		// 先读取head 4个字节
		int length = buffer.readInt();
		System.out.println("imMSGLength:" + length);
		System.out.println(codec.decode(buffer));
	}
}
