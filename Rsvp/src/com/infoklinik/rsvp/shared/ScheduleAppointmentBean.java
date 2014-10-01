package com.infoklinik.rsvp.shared;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ScheduleAppointmentBean extends BaseBean {
	
	private Date date;
	private List<ScheduleBean> schedules;
	private List<ReservationBean> appointments;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ScheduleBean> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ScheduleBean> schedules) {
		this.schedules = schedules;
	}

	public List<ReservationBean> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<ReservationBean> appointments) {
		this.appointments = appointments;
	}
}
