package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.ScheduleReservationBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ScheduleSearchBean;

@RemoteServiceRelativePath("scheduleService")
public interface ScheduleService extends RemoteService {
	
	List<ScheduleBean> getDoctorSchedules(Long doctorId);	
	
	List<ScheduleBean> getSchedules(ScheduleSearchBean scheduleSearch);
	
	ScheduleReservationBean getSchedulesAndReservations(ScheduleSearchBean scheduleSearch);
}
