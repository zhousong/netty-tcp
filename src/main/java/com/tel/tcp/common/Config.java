package com.tel.tcp.common;

public interface Config {

	public interface MsgType {
		int p2pMsg = 1;
		int tempGroupMsg = 2;
		int constGroupMsg = 3;
	}

	public interface HeartBeat {
		int HeartBeatTime = 10;
	}
}
