package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.ScheduleBean;

public interface ScheduleServiceAsync {

	public void getDoctorSchedules(Long doctorId, AsyncCallback<List<ScheduleBean>> callback);
}
