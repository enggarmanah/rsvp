package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class RegionBean extends BaseBean {

	private String name;
	private CityBean cityBean;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CityBean getCityBean() {
		return cityBean;
	}

	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}
}
