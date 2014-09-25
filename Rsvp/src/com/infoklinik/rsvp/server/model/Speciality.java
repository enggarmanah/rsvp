package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.infoklinik.rsvp.shared.SpecialityBean;

@Entity
@Table(name="speciality")
public class Speciality extends Base {
	
	private String title;
	private String description;
	private String type;
	private String status;

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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBean(SpecialityBean specialityBean) {
		
		id = specialityBean.getId();
		title = specialityBean.getTitle();
		description = specialityBean.getDescription();
		type = specialityBean.getType();
		status = specialityBean.getStatus();
		
		setAuditBean(specialityBean.getAuditBean());
	}
	
	public SpecialityBean getBean() {
		
		SpecialityBean specialityBean = new SpecialityBean();
		
		specialityBean.setId(id);
		specialityBean.setTitle(title);
		specialityBean.setDescription(description);
		specialityBean.setType(type);
		specialityBean.setStatus(status);
		
		specialityBean.setAuditBean(getAuditBean());
		
		return specialityBean;
	}
}
