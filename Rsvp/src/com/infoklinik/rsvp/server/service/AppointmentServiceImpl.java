package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base32;

import com.infoklinik.rsvp.client.rpc.AppointmentService;
import com.infoklinik.rsvp.server.SmsUtil;
import com.infoklinik.rsvp.server.dao.AppointmentDAO;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.AppointmentSearchBean;

@SuppressWarnings("serial")
public class AppointmentServiceImpl extends BaseServiceServlet implements AppointmentService {
	
	private String generateReservationCode() {
		
		String code = String.valueOf(new Date().getTime());
		Base32 base32 = new Base32();
		return base32.encodeAsString(code.getBytes());
	}
	
	public String sendVerificationCode(String mobile) {
		
		String code = String.valueOf((new Date()).getTime());
		code = code.substring(code.length()-6);
		
		SmsUtil.sendSms(mobile, code);
		
		return code;
	}
	
	public synchronized AppointmentBean addAppointment(AppointmentBean appointment) {
		
		AppointmentDAO appointmentDAO = new AppointmentDAO();
		
		AppointmentSearchBean apptSearch = new AppointmentSearchBean();
		apptSearch.setDoctorId(appointment.getDoctor().getId());
		apptSearch.setInstId(appointment.getInstitution().getId());
		apptSearch.setApptDate(appointment.getApptDate());
		
		List<AppointmentBean> appointments = appointmentDAO.getAppointments(apptSearch);
		if (appointments.size() == 0) {
			appointment.setReservationCode(generateReservationCode());
			appointment = appointmentDAO.addAppointment(appointment);
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
