package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class UserProfileBean extends BaseBean {

	private String name;
	private String email;
	private String userType;
	private String status;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}
	
	public String getUserTypeDesc() {
		return Constant.SUPERUSER.equals(userType) ? Constant.SUPERUSER_DESC : Constant.OPERATION_DESC;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBean(UserProfileBean userProfileBean) {
		
		id = userProfileBean.getId();
		name = userProfileBean.getName();
		email = userProfileBean.getEmail();
		userType = userProfileBean.getUserType();
		
		setAuditBean(userProfileBean.getAuditBean());
	}
}
