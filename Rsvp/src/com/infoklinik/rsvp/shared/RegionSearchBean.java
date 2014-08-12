package com.infoklinik.rsvp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RegionSearchBean implements Serializable {

	private String name;
	private Long cityId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
}
