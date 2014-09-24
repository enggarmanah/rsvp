package com.infoklinik.rsvp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserProfileSearchBean implements Serializable {

	private String name;
	private String status;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
