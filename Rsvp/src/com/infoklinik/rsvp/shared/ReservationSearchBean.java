package com.infoklinik.rsvp.shared;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ReservationSearchBean implements IsSerializable, Serializable {

	private Long doctorId;
	private Long instId;
	private Date apptDate;

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getInstId() {
		return instId;
	}

	public void setInstId(Long instId) {
		this.instId = instId;
	}

	public Date getApptDate() {
		return apptDate;
	}

	public void setApptDate(Date apptDate) {
		this.apptDate = apptDate;
	}
}
