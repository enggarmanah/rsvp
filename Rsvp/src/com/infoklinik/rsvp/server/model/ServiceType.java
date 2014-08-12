package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.infoklinik.rsvp.shared.ServiceTypeBean;

@Entity
@Table(name="service_type")
public class ServiceType extends Base {
	
	private String name;
	private String category;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setBean(ServiceTypeBean serviceTypeBean) {
		
		id = serviceTypeBean.getId();
		name = serviceTypeBean.getName();
		category = serviceTypeBean.getCategory();
		
		setAuditBean(serviceTypeBean.getAuditBean());
	}
	
	public ServiceTypeBean getBean() {
		
		ServiceTypeBean serviceBean = new ServiceTypeBean();
		
		serviceBean.setId(id);
		serviceBean.setName(name);
		serviceBean.setCategory(category);
		
		serviceBean.setAuditBean(getAuditBean());
		
		return serviceBean;
	}
}
