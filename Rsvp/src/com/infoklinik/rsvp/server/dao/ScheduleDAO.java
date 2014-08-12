package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Schedule;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class ScheduleDAO {

	public List<ScheduleBean> getDoctorSchedules(Long doctorId) {

		List<ScheduleBean> list = new ArrayList<ScheduleBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		Query query = em.createQuery("SELECT s FROM Schedule s WHERE s.doctor.id = :doctorId ORDER BY s.day", Schedule.class);
		
		query.setParameter("doctorId", doctorId);
		
		@SuppressWarnings("unchecked")
		List<Schedule> result = query.getResultList();
		
		for (Schedule schedule : result) {
			list.add(schedule.loadInstitution().getBean());
		}

		em.close();

		return list;
	}
}
