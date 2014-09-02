package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.Appointment;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.AppointmentSearchBean;

public class AppointmentDAO {
	
	public AppointmentBean addAppointment(AppointmentBean appointmentBean) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Appointment appointment = new Appointment();
		appointment.setBean(appointmentBean, em);
		
		em.persist(appointment);

		em.close();

		return appointment.getBean();
	}
	
	public AppointmentBean updateAppointment(AppointmentBean appointmentBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Appointment appointment = em.find(Appointment.class, appointmentBean.getId());
		appointment.setBean(appointmentBean, em);

		em.close();
		
		return appointmentBean;
	}
	
	public AppointmentBean deleteAppointment(AppointmentBean appointmentBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Appointment appointment = em.find(Appointment.class, appointmentBean.getId());
		em.remove(appointment);

		em.close();
		
		return appointmentBean;
	}
	
	public List<AppointmentBean> getAppointments(AppointmentSearchBean apptSearch) {

		List<AppointmentBean> list = new ArrayList<AppointmentBean>();
		
		ArrayList<String> joins = new ArrayList<String>();
		ArrayList<String> filters = new ArrayList<String>();
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT a FROM Appointment a");
		
		if (apptSearch.getDoctorId() != null) {
			joins.add("a.doctor d");
			filters.add("d.id = :doctorId");
			parameters.put("doctorId", apptSearch.getDoctorId());
		}
		
		if (apptSearch.getInstId() != null) {
			joins.add("a.institution i");
			filters.add("i.id = :instId");
			parameters.put("instId", apptSearch.getInstId());
		}
		
		if (apptSearch.getApptDate() != null) {
			filters.add("a.appt_date = :apptDate");
			parameters.put("apptDate", apptSearch.getApptDate());
		}
		
		ServerUtil.setJoin(sql, joins);
		ServerUtil.setFilter(sql, filters);
		
		sql.append(" ORDER BY a.appt_date");
		
		TypedQuery<Appointment> query = em.createQuery(sql.toString(), Appointment.class);
		
		for (String parameter : parameters.keySet()) {
			
			Object value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<Appointment> result = query.getResultList();
		
		for (Appointment appointment : result) {
			list.add(appointment.getBean());
		}

		em.close();

		return list;
	}
}