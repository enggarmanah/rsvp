package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.AppointmentSearchBean;

@RemoteServiceRelativePath("appointmentService")
public interface AppointmentService extends RemoteService {
	
	String sendVerificationCode(String mobile);
	
	AppointmentBean addAppointment(AppointmentBean appointmentBean);

	AppointmentBean updateAppointment(AppointmentBean appointmentBean);
	
	AppointmentBean deleteAppointment(AppointmentBean appointmentBean);
	
	List<AppointmentBean> getAppointments(AppointmentSearchBean apptSearch);
}
