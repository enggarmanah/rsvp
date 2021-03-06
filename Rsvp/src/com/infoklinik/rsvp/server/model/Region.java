package com.infoklinik.rsvp.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.infoklinik.rsvp.shared.RegionBean;

@Entity
@Table(name="region")
public class Region extends Base {
	
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

	public void setBean(RegionBean regionBean, EntityManager em) {
		
		id = regionBean.getId();
		name = regionBean.getName();
		
		if (regionBean.getCity() != null) {
			city = em.find(City.class, regionBean.getCity().getId());
		}
		
		setAuditBean(regionBean.getAuditBean());
	}
	
	public RegionBean getBean() {
		
		RegionBean regionBean = new RegionBean();
		
		regionBean.setId(id);
		regionBean.setName(name);
		
		if (city != null) {
			regionBean.setCity(city.getBean());
		}
		
		regionBean.setAuditBean(getAuditBean());
		
		return regionBean;
	}
}