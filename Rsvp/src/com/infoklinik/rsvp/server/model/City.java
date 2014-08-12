package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;

import com.infoklinik.rsvp.shared.CityBean;

@Entity
public class City extends Base {
	
	private String name;
	private Double location_lat;
	private Double location_lng;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLocation_lat() {
		return location_lat;
	}

	public void setLocation_lat(Double locationLat) {
		this.location_lat = locationLat;
	}

	public Double getLocation_lng() {
		return location_lng;
	}

	public void setLocation_lng(Double locationLng) {
		this.location_lng = locationLng;
	}

	public void setBean(CityBean cityBean) {
		
		id = cityBean.getId();
		name = cityBean.getName();
		location_lat = cityBean.getLocationLat();
		location_lng = cityBean.getLocationLng();
		
		setAuditBean(cityBean.getAuditBean());
	}
	
	public CityBean getBean() {
		
		CityBean cityBean = new CityBean();
		
		cityBean.setId(id);
		cityBean.setName(name);
		cityBean.setLocationLat(location_lat);
		cityBean.setLocationLng(location_lng);
		
		cityBean.setAuditBean(getAuditBean());
		
		return cityBean;
	}
}
