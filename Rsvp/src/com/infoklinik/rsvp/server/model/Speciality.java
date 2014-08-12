package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;

import com.infoklinik.rsvp.shared.SpecialityBean;

@Entity
public class Speciality extends Base {
	
	private String title;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setBean(SpecialityBean specialityBean) {
		
		id = specialityBean.getId();
		title = specialityBean.getTitle();
		description = specialityBean.getDescription();
		
		setAuditBean(specialityBean.getAuditBean());
	}
	
	public SpecialityBean getBean() {
		
		SpecialityBean specialityBean = new SpecialityBean();
		
		specialityBean.setId(id);
		specialityBean.setTitle(title);
		specialityBean.setDescription(description);
		
		specialityBean.setAuditBean(getAuditBean());
		
		return specialityBean;
	}
}
