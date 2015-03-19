package com.tel.tcp.msg.resp;

public class LoginResp extends BaseResp {
	private static final long serialVersionUID = 8933909853040611592L;

	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResp [phone=");
		builder.append(phone);
		builder.append("]");
		return builder.toString();
	}

}
