package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infoklinik.rsvp.shared.ScheduleBean;

@Entity
@Table(name="schedule")
public class Schedule extends Base {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inst_id")
	private Institution institution;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	private Integer day;
	private Integer op_start;
	private Integer op_end;
	
	@Transient
	private boolean isLoadInstitution = false;
	
	@Transient
	private boolean isLoadDoctor = false;

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

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getOp_start() {
		return op_start;
	}

	public void setOp_start(Integer opStart) {
		this.op_start = opStart;
	}

	public Integer getOp_end() {
		return op_end;
	}

	public void setOp_end(Integer opEnd) {
		this.op_end = opEnd;
	}
	
	public Schedule loadInstitution() {
		this.isLoadInstitution = true;
		return this;
	}

	public Schedule loadDoctor() {
		this.isLoadDoctor = true;
		return this;
	}

	public ScheduleBean getBean() {
		
		ScheduleBean scheduleBean = new ScheduleBean();
		scheduleBean.setId(id);
		
		if (institution != null && isLoadInstitution) {
			scheduleBean.setInstitution(institution.getBean());
		}
		
		if (doctor != null && isLoadDoctor) {
			scheduleBean.setDoctor(doctor.getBean());
		}
		
		scheduleBean.setDay(day);
		scheduleBean.setOpStart(op_start);
		scheduleBean.setOpEnd(op_end);
		
		scheduleBean.setAuditBean(getAuditBean());
		
		return scheduleBean;
	} 
	
	public void setBean(ScheduleBean scheduleBean, EntityManager em) {
		
		id = scheduleBean.getId();
		
		if (scheduleBean.getInstitutionBean() != null) {
			institution = em.find(Institution.class, scheduleBean.getInstitutionBean().getId());
		}
		
		if (scheduleBean.getDoctor() != null) {
			doctor = em.find(Doctor.class, scheduleBean.getDoctor().getId());
		}
		
		day = scheduleBean.getDay();
		op_start = scheduleBean.getOpStart();
		op_end = scheduleBean.getOpEnd();
		
		setAuditBean(scheduleBean.getAuditBean());
	}
}