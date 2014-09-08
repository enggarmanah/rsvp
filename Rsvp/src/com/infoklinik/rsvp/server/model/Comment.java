package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.infoklinik.rsvp.shared.CommentBean;

@Entity
@Table(name="comment")
public class Comment extends Base {

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
	private String text;

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
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setBean(CommentBean commentBean, EntityManager em) {
		
		id = commentBean.getId();
		
		if (commentBean.getInstitution() != null) {
			institution = em.find(Institution.class, commentBean.getInstitution().getId());
		}
		
		if (commentBean.getDoctor() != null) {
			doctor = em.find(Doctor.class, commentBean.getDoctor().getId());
		}
		
		if (commentBean.getService() != null) {
			service = em.find(Service.class, commentBean.getService().getId());
		}
		
		fb_id = commentBean.getFbId();
		fb_name = commentBean.getFbName();
		text = commentBean.getText();
		
		setAuditBean(commentBean.getAuditBean());
	}
	
	public CommentBean getBean() {
		
		CommentBean commentBean = new CommentBean();
		
		commentBean.setId(id);
	
		if (institution != null) {
			commentBean.setInstitution(institution.getBean());
		}
		
		if (doctor != null) {
			commentBean.setDoctor(doctor.getBean());
		}
		
		if (service != null) {
			commentBean.setService(service.getBean());
		}
		
		commentBean.setFbId(fb_id);
		commentBean.setFbName(fb_name);
		commentBean.setText(text);
		
		commentBean.setAuditBean(getAuditBean());
		
		return commentBean;
	}
}
