package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.infoklinik.rsvp.shared.MasterCodeBean;

@Entity
@Table(name="master_code")
public class MasterCode extends Base {
	
	private String code;
	private String value;
	private String type;
	private String display_order;
	private String status;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisplay_order() {
		return display_order;
	}

	public void setDisplay_order(String display_order) {
		this.display_order = display_order;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBean(MasterCodeBean masterCodeBean) {
		
		id = masterCodeBean.getId();
		code = masterCodeBean.getCode();
		value = masterCodeBean.getValue();
		type = masterCodeBean.getType();
		
		setAuditBean(masterCodeBean.getAuditBean());
	}
	
	public MasterCodeBean getBean() {
		
		MasterCodeBean masterCodeBean = new MasterCodeBean();
		
		masterCodeBean.setId(id);
		masterCodeBean.setCode(code);
		masterCodeBean.setValue(value);
		masterCodeBean.setType(type);
		
		masterCodeBean.setAuditBean(getAuditBean());
		
		return masterCodeBean;
	}
}
