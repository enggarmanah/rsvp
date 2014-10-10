package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Schedule;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ScheduleSearchBean;

public class ScheduleDAO {

	public List<ScheduleBean> getDoctorSchedules(Long doctorId) {

		List<ScheduleBean> list = new ArrayList<ScheduleBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		Query query = em.createQuery("SELECT s FROM Schedule s WHERE s.doctor.id = :doctorId ORDER BY s.day, s.op_start", Schedule.class);
		
		query.setParameter("doctorId", doctorId);
		
		@SuppressWarnings("unchecked")
		List<Schedule> result = query.getResultList();
		
		for (Schedule schedule : result) {
			list.add(schedule.loadInstitution().getBean());
		}

		em.close();

		return list;
	}
	
	public List<ScheduleBean> getSchedules(ScheduleSearchBean scheduleSearch) {

		List<ScheduleBean> list = new ArrayList<ScheduleBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		Query query = em.createQuery("SELECT s FROM Schedule s WHERE s.doctor.id = :doctorId AND s.institution.id = :instId AND day = :day", Schedule.class);
		
		query.setParameter("doctorId", scheduleSearch.getDoctorId());
		query.setParameter("instId", scheduleSearch.getInstId());
		query.setParameter("day", scheduleSearch.getDay());
		
		@SuppressWarnings("unchecked")
		List<Schedule> result = query.getResultList();
		
		for (Schedule schedule : result) {
			list.add(schedule.loadInstitution().getBean());
		}

		em.close();

		return list;
	}
}
