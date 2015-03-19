package com.tel.tcp.common;

public enum CMD {
	Login(1000),
	Logout(1002),
	HeartBeat(2001),
	SendIM(3001),
	PushIM(3003);

	private int value;

	private CMD(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
