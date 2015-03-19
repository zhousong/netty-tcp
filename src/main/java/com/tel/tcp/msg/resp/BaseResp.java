package com.tel.tcp.msg.resp;

import com.tel.tcp.msg.BaseMsg;

public class BaseResp extends BaseMsg {
	private static final long serialVersionUID = 738210650685204314L;

	private int code;
	private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseResp [code=");
		builder.append(code);
		builder.append(", desc=");
		builder.append(desc);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
