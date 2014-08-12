package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.infoklinik.rsvp.shared.LikeBean;

@Entity
public class Like extends Base {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inst_id")
	private Institution institution;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id")
	private Service service;
	
	private String fb_id;
	private String fb_name;

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getFb_id() {
		return fb_id;
	}

	public void setFb_id(String fb_id) {
		this.fb_id = fb_id;
	}

	public String getFb_name() {
		return fb_name;
	}

	public void setFb_name(String fb_name) {
		this.fb_name = fb_name;
	}
	
	public void setBean(LikeBean likeBean, EntityManager em) {
		
		id = likeBean.getId();
		
		if (likeBean.getInstitution() != null) {
			institution = em.find(Institution.class, likeBean.getInstitution().getId());
		}
		
		if (likeBean.getDoctor() != null) {
			doctor = em.find(Doctor.class, likeBean.getDoctor().getId());
		}
		
		if (likeBean.getService() != null) {
			service = em.find(Service.class, likeBean.getService().getId());
		}
		
		fb_id = likeBean.getFbId();
		fb_name = likeBean.getFbName();
		
		setAuditBean(likeBean.getAuditBean());
	}
	
	public LikeBean getBean() {
		
		LikeBean likeBean = new LikeBean();
		
		likeBean.setId(id);
	
		if (institution != null) {
			likeBean.setInstitution(institution.getBean());
		}
		
		if (doctor != null) {
			likeBean.setDoctor(doctor.getBean());
		}
		
		if (service != null) {
			likeBean.setService(service.getBean());
		}
		
		likeBean.setFbId(fb_id);
		likeBean.setFbName(fb_name);
		
		likeBean.setAuditBean(getAuditBean());
		
		return likeBean;
	}
}
