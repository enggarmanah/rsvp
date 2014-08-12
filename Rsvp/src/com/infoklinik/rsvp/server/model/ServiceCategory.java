package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="service_category")
public class ServiceCategory extends Base {
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
