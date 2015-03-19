package com.tel.tcp.common;

import java.util.concurrent.atomic.AtomicLong;

public class MsgIdGen {
	private static final AtomicLong l = new AtomicLong();

	public static long id() {
		return l.getAndIncrement();
	}
}
