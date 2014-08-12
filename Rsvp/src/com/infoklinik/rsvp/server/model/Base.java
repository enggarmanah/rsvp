package com.infoklinik.rsvp.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.infoklinik.rsvp.shared.AuditBean;
import com.infoklinik.rsvp.shared.SharedUtil;

@Entity
@MappedSuperclass
public class Base {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	protected String create_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date create_date;
	
	protected String update_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date update_date;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private void setUpdate_by(String update_by) {
		
		this.update_by = update_by;
		this.update_date = SharedUtil.getCurrentDate();
		this.create_by = this.create_by == null ? String.valueOf(this.update_by) : this.create_by;
		this.create_date = this.create_date == null ? new Date(this.update_date.getTime()) : this.create_date;
	}

	public void setAuditBean(AuditBean auditBean) {
		
		setUpdate_by(auditBean.getUpdateBy());
	}
	
	public AuditBean getAuditBean() {
		
		AuditBean auditBean = new AuditBean();
		
		auditBean.setCreateBy(create_by);
		auditBean.setCreateDate(create_date != null ? new Date(create_date.getTime()) : null);
		auditBean.setUpdateBy(update_by);
		auditBean.setUpdateDate(update_date != null ? new Date(update_date.getTime()) : null);
		
		return auditBean;
	} 
}
