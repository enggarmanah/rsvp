
package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.AppointmentSearchBean;

public interface AppointmentServiceAsync {
	
	public void sendVerificationCode(String mobile, AsyncCallback<String> callback);
	
	public void addAppointment(AppointmentBean appointment, AsyncCallback<AppointmentBean> callback);

	public void updateAppointment(AppointmentBean appointment, AsyncCallback<AppointmentBean> callback);
	
	public void deleteAppointment(AppointmentBean appointment, AsyncCallback<AppointmentBean> callback);

	public void getAppointments(AppointmentSearchBean apptSearch, AsyncCallback<List<AppointmentBean>> callback);
}
