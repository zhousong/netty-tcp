package com.tel.tcp.msg.push;

import com.tel.tcp.msg.BaseMsg;

public class IM extends BaseMsg {

	private static final long serialVersionUID = -3827804037777420894L;

	private String fromAccount;
	private String toAccount;
	private long time;
	private int type;
	private String msg;

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IM [fromAccount=");
		builder.append(fromAccount);
		builder.append(", toAccount=");
		builder.append(toAccount);
		builder.append(", time=");
		builder.append(time);
		builder.append(", type=");
		builder.append(type);
		builder.append(", msg=");
		builder.append(msg);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
