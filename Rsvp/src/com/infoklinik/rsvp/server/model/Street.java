package com.infoklinik.rsvp.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.infoklinik.rsvp.shared.StreetBean;

@Entity
@Table(name="street")
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

	public void setBean(StreetBean streetBean, EntityManager em) {
		
		id = streetBean.getId();
		name = streetBean.getName();
		
		if (streetBean.getCity() != null) {
			city = em.find(City.class, streetBean.getCity().getId());
		}
		
		setAuditBean(streetBean.getAuditBean());
	}
	
	public StreetBean getBean() {
		
		StreetBean streetBean = new StreetBean();
		
		streetBean.setId(id);
		streetBean.setName(name);
		
		if (city != null) {
			streetBean.setCity(city.getBean());
		}
		
		streetBean.setAuditBean(getAuditBean());
		
		return streetBean;
	}
}
