package com.tel.im;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import com.alibaba.fastjson.JSON;

public class ByteBufTest {
	public static void main(String[] args) {
		ByteBuf buf = Unpooled.buffer(10);
		System.out.println(buf);

		buf.writeInt(50);
		System.out.println(buf);

		buf.writeInt(100);
		System.out.println(buf);

		buf.writeByte((byte) 1);
		System.out.println(buf);

		buf.writeByte((byte) -1);
		System.out.println(buf);

		System.out.println("==============");
		int a = buf.readInt();
		System.out.println(a);
		System.out.println(buf);

		int b = buf.readInt();
		System.out.println(b);
		System.out.println(buf);

		int c = buf.readByte();
		System.out.println(c);
		System.out.println(buf);

		int d = buf.readByte();
		System.out.println(d);
		System.out.println(buf);

		System.out.println("=======write=======");
		Person p = new Person();
		p.setId("12");
		p.setName("test");
		String json = JSON.toJSONString(p);
		byte[] bytes = json.getBytes();
		int len = bytes.length;
		System.out.println("len:" + len);
		ByteBuf bb = Unpooled.buffer(len + 4);
		bb.writeInt(len);
		bb.writeBytes(bytes);
		System.out.println(bb);

		System.out.println("=======read=======");
		int length = bb.readInt();
		int readableBytes = bb.readableBytes();
		byte[] dst = new byte[readableBytes];
		bb.readBytes(dst, 0, readableBytes);
		System.out.println(new String(dst));
	}

	static class Person {
		private String id;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
