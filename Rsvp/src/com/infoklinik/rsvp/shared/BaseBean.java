package com.infoklinik.rsvp.shared;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BaseBean implements Serializable {
	
	protected Long id;
	
	protected String createBy;
	protected Date createDate;
	protected String updateBy;
	protected Date updateDate;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setAuditBean(AuditBean auditBean) {
		
		createBy = auditBean.getCreateBy();
		createDate = auditBean.getCreateDate();
		updateBy = auditBean.getUpdateBy();
		updateDate = auditBean.getUpdateDate();
	}
	
	public AuditBean getAuditBean() {
		
		AuditBean auditBean = new AuditBean();
		
		auditBean.setCreateBy(createBy);
		auditBean.setCreateDate(createDate);
		auditBean.setUpdateBy(updateBy);
		auditBean.setUpdateDate(updateDate);
		
		return auditBean;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
