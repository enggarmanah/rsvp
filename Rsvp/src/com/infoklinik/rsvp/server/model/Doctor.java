package com.infoklinik.rsvp.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.ScheduleBean;

@Entity
@Table(name="doctor")
public class Doctor extends Base {

	private String name;
	private String reg_no;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "speciality_id")
	private Speciality speciality;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy="doctor", targetEntity=Schedule.class)
	private List<Schedule> schedules;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy="doctor", targetEntity=Schedule.class)
	private List<Appointment> appointments;

	private String mobile;
	private String email;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;
	
	private String address;
	
	@Column(length=4000)
	private String profile;
	
	private String notification_status;
	private Long view_count;
	private Long like_count;
	private Long comment_count;
	private Long image_id;
	private String status;
	
	@Transient
	private boolean isLoadSchedule = false;
	
	@Transient
	private Long instId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
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

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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

	public String getNotification_status() {
		return notification_status;
	}

	public void setNotification_status(String notification_status) {
		this.notification_status = notification_status;
	}
	
	public Long getView_count() {
		return view_count;
	}

	public void setView_count(Long view_count) {
		this.view_count = view_count;
	}

	public Long getLike_count() {
		return like_count;
	}

	public void setLike_count(Long like_count) {
		this.like_count = like_count;
	}

	public Long getComment_count() {
		return comment_count;
	}

	public void setComment_count(Long comment_count) {
		this.comment_count = comment_count;
	}

	public Long getImage_id() {
		return image_id;
	}

	public void setImage_id(Long image_id) {
		this.image_id = image_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBean(DoctorBean doctorBean, EntityManager em) {

		id = doctorBean.getId();
		name = doctorBean.getName();
		reg_no = doctorBean.getRegNo();
		
		if (doctorBean.getSpeciality() != null) {
			speciality = em.find(Speciality.class, doctorBean.getSpeciality().getId());
		}
		
		mobile = doctorBean.getMobile();
		email = doctorBean.getEmail();
		
		if (doctorBean.getCityBean() != null) {
			city = em.find(City.class, doctorBean.getCityBean().getId());
		}
		
		address = doctorBean.getAddress();
		profile = doctorBean.getProfile();
		notification_status = doctorBean.getNotificationStatus();
		view_count = doctorBean.getViewCount();
		like_count = doctorBean.getLikeCount();
		comment_count = doctorBean.getCommentCount();
		image_id = doctorBean.getImageId();
		status = doctorBean.getStatus();
		
		setAuditBean(doctorBean.getAuditBean());
	}

	public DoctorBean getBean() {

		DoctorBean doctorBean = new DoctorBean();
		doctorBean.setId(id);
		doctorBean.setName(name);
		doctorBean.setRegNo(reg_no);
		
		if (speciality != null) {
			doctorBean.setSpeciality(speciality.getBean());
		}
		
		doctorBean.setMobile(mobile);
		doctorBean.setEmail(email);
		
		if (city != null) {
			doctorBean.setCityBean(city.getBean());
		}
		
		doctorBean.setAddress(address);
		doctorBean.setProfile(profile);
		doctorBean.setNotificationStatus(notification_status);
		doctorBean.setViewCount(view_count);
		doctorBean.setLikeCount(like_count);
		doctorBean.setCommentCount(comment_count);
		doctorBean.setImageId(image_id);
		doctorBean.setStatus(status);
		
		if (isLoadSchedule) {
			
			List<ScheduleBean> list = new ArrayList<ScheduleBean>();
			for (Schedule schedule : schedules) {
				
				if (instId != null) {
					if (instId.equals(schedule.getInstitution().getId())) {
						list.add(schedule.loadInstitution().getBean());
					}
				} else {
					list.add(schedule.loadInstitution().getBean());
				}
			}
			
			doctorBean.setSchedules(list);
		}
		
		doctorBean.setAuditBean(getAuditBean());

		return doctorBean;
	}
	
	public Doctor loadSchedules() {
		
		isLoadSchedule = true;
		return this;
	}
	
	public Doctor loadSchedules(Long instId) {
		
		isLoadSchedule = true;
		this.instId = instId;
		
		return this;
	}
}
