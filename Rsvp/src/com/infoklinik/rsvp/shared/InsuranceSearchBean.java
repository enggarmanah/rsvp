package com.infoklinik.rsvp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class InsuranceSearchBean implements Serializable {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
