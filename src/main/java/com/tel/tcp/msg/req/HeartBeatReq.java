package com.tel.tcp.msg.req;

import com.tel.tcp.msg.BaseMsg;

public class HeartBeatReq extends BaseMsg {

	private static final long serialVersionUID = 6105514519085749854L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HeartBeatReq [toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
