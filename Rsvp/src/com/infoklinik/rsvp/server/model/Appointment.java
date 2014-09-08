package com.infoklinik.rsvp.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.shared.AppointmentBean;

@Entity
@Table(name="appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inst_id")
	private Institution institution;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	private String reservation_code;
	private String patient_name;
	private String patient_sex;
	private String patient_birth_year;
	private String patient_mobile;
	private String patient_email;
	private String remarks;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date appt_date;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date appt_create_date;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getReservation_code() {
		return reservation_code;
	}

	public void setReservation_code(String reservation_code) {
		this.reservation_code = reservation_code;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getPatient_sex() {
		return patient_sex;
	}

	public void setPatient_sex(String patient_sex) {
		this.patient_sex = patient_sex;
	}

	public String getPatient_birth_year() {
		return patient_birth_year;
	}

	public void setPatient_birth_year(String patient_birth_year) {
		this.patient_birth_year = patient_birth_year;
	}

	public String getPatient_mobile() {
		return patient_mobile;
	}

	public void setPatient_mobile(String patient_mobile) {
		this.patient_mobile = patient_mobile;
	}

	public String getPatient_email() {
		return patient_email;
	}

	public void setPatient_email(String patient_email) {
		this.patient_email = patient_email;
	}

	public Date getAppt_date() {
		return appt_date;
	}

	public void setAppt_date(Date appt_date) {
		this.appt_date = appt_date;
	}

	public Date getAppt_create_date() {
		return appt_create_date;
	}

	public void setAppt_create_date(Date appt_create_date) {
		this.appt_create_date = appt_create_date;
	}
	
	public AppointmentBean getBean() {
		
		AppointmentBean appointmentBean = new AppointmentBean();
		appointmentBean.setId(id);
		appointmentBean.setDoctor(doctor.getBean());
		appointmentBean.setInstitution(institution.getBean());
		appointmentBean.setReservationCode(reservation_code);
		appointmentBean.setPatientName(patient_name);
		appointmentBean.setPatientSex(patient_sex);
		appointmentBean.setPatientBirthYear(patient_birth_year);
		appointmentBean.setPatientMobile(patient_mobile);
		appointmentBean.setPatientEmail(patient_email);
		appointmentBean.setRemarks(remarks);
		appointmentBean.setApptDate(ServerUtil.toDate(appt_date));
		appointmentBean.setApptCreateDate(ServerUtil.toDate(appt_create_date));
		
		return appointmentBean;
	}
	
	public void setBean(AppointmentBean appointmentBean, EntityManager em) {
		
		id = appointmentBean.getId();
		
		if (appointmentBean.getDoctor() != null) {
			doctor = em.find(Doctor.class, appointmentBean.getDoctor().getId());
		}
		
		if (appointmentBean.getInstitution() != null) {
			institution = em.find(Institution.class, appointmentBean.getInstitution().getId());
		}
		
		reservation_code = appointmentBean.getReservationCode();
		patient_name = appointmentBean.getPatientName();
		patient_sex = appointmentBean.getPatientSex();
		patient_birth_year = appointmentBean.getPatientBirthYear();
		patient_mobile = appointmentBean.getPatientMobile();
		patient_email = appointmentBean.getPatientEmail();
		remarks = appointmentBean.getRemarks();
		appt_date = appointmentBean.getApptDate();
		appt_create_date = appointmentBean.getApptCreateDate();
	}
}