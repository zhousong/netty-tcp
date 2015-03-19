package com.tel.tcp.msg.req;

import com.tel.tcp.msg.BaseMsg;

public class LoginReq extends BaseMsg {
	private static final long serialVersionUID = 2729508353324200467L;

	private String account;
	private String password;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginReq [account=");
		builder.append(account);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}

}
