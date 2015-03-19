package com.tel.tcp.msg;

import java.io.Serializable;

public class BaseMsg implements Serializable {

	private static final long serialVersionUID = -5955921822349445352L;

	private long id;
	private int cmd;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseMsg [id=");
		builder.append(id);
		builder.append(", cmd=");
		builder.append(cmd);
		builder.append("]");
		return builder.toString();
	}

}
