package com.infoklinik.rsvp.shared;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class AppointmentBean implements Serializable {

	private Long id;
	private DoctorBean doctor;
	private InstitutionBean institution;
	private String eventId;
	private String verificationCode;
	private String reservationCode;
	private String patientName;
	private String patientSex;
	private String patientBirthYear;
	private String patientMobile;
	private String patientEmail;
	private String remarks;
	private Date apptDate;
	private Date apptCreateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public DoctorBean getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorBean doctor) {
		this.doctor = doctor;
	}

	public InstitutionBean getInstitution() {
		return institution;
	}

	public void setInstitution(InstitutionBean institution) {
		this.institution = institution;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}

	public String getPatientBirthYear() {
		return patientBirthYear;
	}

	public void setPatientBirthYear(String patientBirthYear) {
		this.patientBirthYear = patientBirthYear;
	}

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getApptDate() {
		return apptDate;
	}

	public void setApptDate(Date apptDate) {
		this.apptDate = apptDate;
	}

	public Date getApptCreateDate() {
		return apptCreateDate;
	}

	public void setApptCreateDate(Date apptCreateDate) {
		this.apptCreateDate = apptCreateDate;
	}
}
