package com.infoklinik.rsvp.shared;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ScheduleReservationBean extends BaseBean {
	
	private Date date;
	private List<ScheduleBean> schedules;
	private List<ReservationBean> reservations;

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

	public List<ReservationBean> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationBean> reservations) {
		this.reservations = reservations;
	}
}
