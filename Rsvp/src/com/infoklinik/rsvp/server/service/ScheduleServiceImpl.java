package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.ScheduleService;
import com.infoklinik.rsvp.server.dao.ScheduleDAO;
import com.infoklinik.rsvp.shared.ScheduleBean;

@SuppressWarnings("serial")
public class ScheduleServiceImpl extends BaseServiceServlet implements ScheduleService {
	
	public List<ScheduleBean> getDoctorSchedules(Long doctorId) {
		
		ScheduleDAO scheduleDao = new ScheduleDAO();
		List<ScheduleBean> schedules = scheduleDao.getDoctorSchedules(doctorId);
		
		return schedules;
	}
}
