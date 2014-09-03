package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.ScheduleAppointmentBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ScheduleSearchBean;

public interface ScheduleServiceAsync {

	public void getDoctorSchedules(Long doctorId, AsyncCallback<List<ScheduleBean>> callback);
	
	public void getSchedules(ScheduleSearchBean scheduleSearch, AsyncCallback<List<ScheduleBean>> callback);
	
	public void getSchedulesAndAppointments(ScheduleSearchBean scheduleSearch, AsyncCallback<ScheduleAppointmentBean> callback);
}
