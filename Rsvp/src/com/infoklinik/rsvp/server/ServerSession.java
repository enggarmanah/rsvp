package com.infoklinik.rsvp.server;

import java.io.Serializable;

import com.infoklinik.rsvp.shared.UserBean;

@SuppressWarnings("serial")
public class ServerSession implements Serializable {

	private UserBean userInfo;

	public UserBean getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserBean userBean) {
		this.userInfo = userBean;
	}
}
