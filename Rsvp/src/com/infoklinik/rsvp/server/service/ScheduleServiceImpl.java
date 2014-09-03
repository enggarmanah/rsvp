package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.ScheduleService;
import com.infoklinik.rsvp.server.dao.AppointmentDAO;
import com.infoklinik.rsvp.server.dao.ScheduleDAO;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.AppointmentSearchBean;
import com.infoklinik.rsvp.shared.ScheduleAppointmentBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ScheduleSearchBean;

@SuppressWarnings("serial")
public class ScheduleServiceImpl extends BaseServiceServlet implements ScheduleService {
	
	public List<ScheduleBean> getDoctorSchedules(Long doctorId) {
		
		ScheduleDAO scheduleDao = new ScheduleDAO();
		List<ScheduleBean> schedules = scheduleDao.getDoctorSchedules(doctorId);
		
		return schedules;
	}
	
	public List<ScheduleBean> getSchedules(ScheduleSearchBean scheduleSearch) {
		
		ScheduleDAO scheduleDao = new ScheduleDAO();
		List<ScheduleBean> schedules = scheduleDao.getSchedules(scheduleSearch);
		
		return schedules;
	}
	
	public ScheduleAppointmentBean getSchedulesAndAppointments(ScheduleSearchBean scheduleSearch) {
		
		ScheduleAppointmentBean scheduleAppoinment = new ScheduleAppointmentBean();
		scheduleAppoinment.setDate(scheduleSearch.getDate());
		
		ScheduleDAO scheduleDao = new ScheduleDAO();
		List<ScheduleBean> schedules = scheduleDao.getSchedules(scheduleSearch);
		scheduleAppoinment.setSchedules(schedules);
		
		AppointmentSearchBean apptSearch = new AppointmentSearchBean();
		apptSearch.setDoctorId(scheduleSearch.getDoctorId());
		apptSearch.setInstId(scheduleSearch.getInstId());
		apptSearch.setApptDate(scheduleSearch.getDate());
		
		AppointmentDAO apptDao = new AppointmentDAO();
		List<AppointmentBean> appointments = apptDao.getAppointments(apptSearch);
		scheduleAppoinment.setAppointments(appointments);
		
		return scheduleAppoinment;
	}
}
