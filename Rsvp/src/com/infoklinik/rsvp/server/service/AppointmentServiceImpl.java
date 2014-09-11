package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.AppointmentService;
import com.infoklinik.rsvp.server.CalendarUtil;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.SmsUtil;
import com.infoklinik.rsvp.server.dao.AppointmentDAO;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.AppointmentSearchBean;

@SuppressWarnings("serial")
public class AppointmentServiceImpl extends BaseServiceServlet implements AppointmentService {
	
	public String sendVerificationCode(String mobile) {
		
		String code = String.valueOf((new Date()).getTime());
		code = code.substring(code.length()-6);
		
		String message = "Silahkan input kode verifikasi : " + code + " ke sistem. -Info Klinik-";
		
		SmsUtil.sendSms(mobile, message);
		
		return code;
	}
	
	public synchronized AppointmentBean addAppointment(AppointmentBean appointment) {
		
		AppointmentDAO appointmentDAO = new AppointmentDAO();
		 
		if (!appointmentDAO.isConflictWithOtherApt(appointment)) {
			
			appointment.setReservationCode(ServerUtil.generateReservationCode());
			
			String eventId = CalendarUtil.addCalendarEntry(appointment);
			if (eventId != null) {
				appointment.setEventId(eventId);
				appointment = appointmentDAO.addAppointment(appointment);
			}
		}
		
		return appointment;
	}
	
	public AppointmentBean updateAppointment(AppointmentBean appointmentBean) {
		
		AppointmentDAO appointmentDAO = new AppointmentDAO();
		AppointmentBean appointment = appointmentDAO.updateAppointment(appointmentBean);
		
		return appointment;
	}
	
	public AppointmentBean deleteAppointment(AppointmentBean appointmentBean) {
		
		AppointmentDAO appointmentDAO = new AppointmentDAO();
		AppointmentBean appointment = appointmentDAO.deleteAppointment(appointmentBean);
		
		return appointment;
	}
	
	public List<AppointmentBean> getAppointments(AppointmentSearchBean apptSearch) {
		
		AppointmentDAO appointmentDao = new AppointmentDAO();
		List<AppointmentBean> appointments = appointmentDao.getAppointments(apptSearch);
		
		List<Long> ids = new ArrayList<Long>();
		for (AppointmentBean instBean : appointments) {
			ids.add(instBean.getId());
		}
		
		return appointments;
	}
}
