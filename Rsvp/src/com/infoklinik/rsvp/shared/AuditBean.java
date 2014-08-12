package com.infoklinik.rsvp.shared;

import java.util.Date;

public class AuditBean {

	protected String createBy;
	protected Date createDate;
	protected String updateBy;
	protected Date updateDate;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String create_by) {
		this.createBy = create_by;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date create_date) {
		this.createDate = create_date;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String update_by) {
		this.updateBy = update_by;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date update_date) {
		this.updateDate = update_date;
	}
}
