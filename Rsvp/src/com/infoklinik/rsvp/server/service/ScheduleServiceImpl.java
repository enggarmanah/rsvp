package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.ScheduleService;
import com.infoklinik.rsvp.server.dao.ReservationDAO;
import com.infoklinik.rsvp.server.dao.ScheduleDAO;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ReservationSearchBean;
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
		
		ReservationSearchBean apptSearch = new ReservationSearchBean();
		apptSearch.setDoctorId(scheduleSearch.getDoctorId());
		apptSearch.setInstId(scheduleSearch.getInstId());
		apptSearch.setApptDate(scheduleSearch.getDate());
		
		ReservationDAO apptDao = new ReservationDAO();
		List<ReservationBean> appointments = apptDao.getReservations(apptSearch);
		scheduleAppoinment.setAppointments(appointments);
		
		return scheduleAppoinment;
	}
}
