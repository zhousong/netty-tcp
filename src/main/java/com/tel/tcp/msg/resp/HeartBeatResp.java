package com.tel.tcp.msg.resp;

public class HeartBeatResp extends BaseResp {

	private static final long serialVersionUID = 2784402445790670674L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HeartBeatResp [toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
