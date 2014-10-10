package com.infoklinik.rsvp.shared;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DoctorBean extends BaseBean {

	private String name;
	private String regNo;
	private SpecialityBean speciality;
	private String mobile;
	private String email;
	private CityBean city;
	private String address;
	private String profile;
	private String notificationStatus;
	private Long viewCount;
	private Long likeCount;
	private Long commentCount;
	private Long imageId;
	private String status;
	
	private List<ScheduleBean> schedules;
	
	public DoctorBean() {
		schedules = new ArrayList<ScheduleBean>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getNameWithTitle() {
		
		if (Constant.DR_SPECIALITY_DENTIST == getSpeciality().getId()) {
			return "Drg. " + name;
		} else if (Constant.DR_SPECIALITY_GP == getSpeciality().getId()) {
			return "Dr. " + name;
		} else {
			return "Dr. " + name + ", " + speciality.getTitle();
		}
	}
	
	public String getNameWithSpeciality() {
		
		return name + ", " + speciality.getDescription();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public SpecialityBean getSpeciality() {
		return speciality;
	}

	public void setSpeciality(SpecialityBean speciality) {
		this.speciality = speciality;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CityBean getCityBean() {
		return city;
	}

	public void setCityBean(CityBean cityBean) {
		this.city = cityBean;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ScheduleBean> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ScheduleBean> schedules) {
		this.schedules = schedules;
	}
	
	public void setBean(DoctorBean doctorBean) {

		id = doctorBean.getId();
		name = doctorBean.getName();
		regNo = doctorBean.getRegNo();
		
		if (doctorBean.getSpeciality() != null) {
			speciality = doctorBean.getSpeciality();
		}
		
		mobile = doctorBean.getMobile();
		email = doctorBean.getEmail();
		
		if (doctorBean.getCityBean() != null) {
			city = doctorBean.getCityBean();
		}
		
		address = doctorBean.getAddress();
		profile = doctorBean.getProfile();
		notificationStatus = doctorBean.getNotificationStatus();
		viewCount = doctorBean.getViewCount();
		likeCount = doctorBean.getLikeCount();
		commentCount = doctorBean.getCommentCount();
		imageId = doctorBean.getImageId();
		status = doctorBean.getStatus();
		
		setAuditBean(doctorBean.getAuditBean());
	}
}
