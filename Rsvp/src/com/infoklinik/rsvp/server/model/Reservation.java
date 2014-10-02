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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.shared.ReservationBean;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inst_id")
	private Institution institution;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id")
	private Service service;
	
	private String event_id;
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

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
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
	
	public ReservationBean getBean() {
		
		ReservationBean reservationBean = new ReservationBean();
		reservationBean.setId(id);
		
		if (doctor != null) {
			reservationBean.setDoctor(doctor.getBean());
		}
		
		if (service != null) {
			reservationBean.setService(service.getBean());
		}
		
		reservationBean.setInstitution(institution.getBean());
		reservationBean.setEventId(event_id);
		reservationBean.setReservationCode(reservation_code);
		reservationBean.setPatientName(patient_name);
		reservationBean.setPatientSex(patient_sex);
		reservationBean.setPatientBirthYear(patient_birth_year);
		reservationBean.setPatientMobile(patient_mobile);
		reservationBean.setPatientEmail(patient_email);
		reservationBean.setRemarks(remarks);
		reservationBean.setApptDate(ServerUtil.toDate(appt_date));
		reservationBean.setApptCreateDate(ServerUtil.toDate(appt_create_date));
		
		return reservationBean;
	}
	
	public void setBean(ReservationBean reservationBean, EntityManager em) {
		
		id = reservationBean.getId();
		
		if (reservationBean.getDoctor() != null) {
			doctor = em.find(Doctor.class, reservationBean.getDoctor().getId());
		}
		
		if (reservationBean.getService() != null) {
			service = em.find(Service.class, reservationBean.getService().getId());
		}
		
		if (reservationBean.getInstitution() != null) {
			institution = em.find(Institution.class, reservationBean.getInstitution().getId());
		}
		
		event_id = reservationBean.getEventId();
		reservation_code = reservationBean.getReservationCode();
		patient_name = reservationBean.getPatientName();
		patient_sex = reservationBean.getPatientSex();
		patient_birth_year = reservationBean.getPatientBirthYear();
		patient_mobile = reservationBean.getPatientMobile();
		patient_email = reservationBean.getPatientEmail();
		remarks = reservationBean.getRemarks();
		appt_date = reservationBean.getApptDate();
		appt_create_date = reservationBean.getApptCreateDate();
	}
}