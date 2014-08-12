package com.infoklinik.rsvp.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.infoklinik.rsvp.shared.StreetBean;

@Entity
public class Street extends Base {
	
	private String name;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void setBean(StreetBean streetBean) {
		
		id = streetBean.getId();
		name = streetBean.getName();
		
		if (streetBean.getCityBean() != null) {
			city = new City();
			city.setBean(streetBean.getCityBean());
		}
		
		setAuditBean(streetBean.getAuditBean());
	}
	
	public StreetBean getBean() {
		
		StreetBean streetBean = new StreetBean();
		
		streetBean.setId(id);
		streetBean.setName(name);
		
		if (city != null) {
			streetBean.setCityBean(city.getBean());
		}
		
		streetBean.setAuditBean(getAuditBean());
		
		return streetBean;
	}
}
