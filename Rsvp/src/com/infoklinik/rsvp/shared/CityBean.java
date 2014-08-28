package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class CityBean extends BaseBean {

	private String name;
	private Double locationLat;
	private Double locationLng;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(Double locationLat) {
		this.locationLat = locationLat;
	}

	public Double getLocationLng() {
		return locationLng;
	}

	public void setLocationLng(Double locationLng) {
		this.locationLng = locationLng;
	}
	
	public void setBean(CityBean cityBean) {
		
		id = cityBean.getId();
		name = cityBean.getName();
		locationLat = cityBean.getLocationLat();
		locationLng = cityBean.getLocationLng();
		
		setAuditBean(cityBean.getAuditBean());
	}
}
