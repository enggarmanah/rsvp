package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.ScheduleService;
import com.infoklinik.rsvp.server.dao.ReservationDAO;
import com.infoklinik.rsvp.server.dao.ScheduleDAO;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ReservationSearchBean;
import com.infoklinik.rsvp.shared.ScheduleReservationBean;
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
	
	public ScheduleReservationBean getSchedulesAndReservations(ScheduleSearchBean scheduleSearch) {
		
		ScheduleReservationBean scheduleAppoinment = new ScheduleReservationBean();
		scheduleAppoinment.setDate(scheduleSearch.getDate());
		
		ScheduleDAO scheduleDao = new ScheduleDAO();
		List<ScheduleBean> schedules = scheduleDao.getSchedules(scheduleSearch);
		scheduleAppoinment.setSchedules(schedules);
		
		ReservationSearchBean apptSearch = new ReservationSearchBean();
		apptSearch.setDoctorId(scheduleSearch.getDoctorId());
		apptSearch.setInstId(scheduleSearch.getInstId());
		apptSearch.setApptDate(scheduleSearch.getDate());
		
		ReservationDAO apptDao = new ReservationDAO();
		List<ReservationBean> reservations = apptDao.getReservations(apptSearch);
		scheduleAppoinment.setReservations(reservations);
		
		return scheduleAppoinment;
	}
}
