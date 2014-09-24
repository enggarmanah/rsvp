package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.infoklinik.rsvp.shared.UserProfileBean;

@Entity
@Table(name="user_profile")
public class UserProfile extends Base {
	
	private String name;
	private String email;
	private String user_type;
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

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String userType) {
		this.user_type = userType;
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
		user_type = userProfileBean.getUserType();
		status = userProfileBean.getStatus();
		
		setAuditBean(userProfileBean.getAuditBean());
	}
	
	public UserProfileBean getBean() {
		
		UserProfileBean userProfileBean = new UserProfileBean();
		
		userProfileBean.setId(id);
		userProfileBean.setName(name);
		userProfileBean.setEmail(email);
		userProfileBean.setUserType(user_type);
		userProfileBean.setStatus(status);
		
		userProfileBean.setAuditBean(getAuditBean());
		
		return userProfileBean;
	}
}