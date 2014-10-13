package com.infoklinik.rsvp.server.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.shared.Constant;

public class StatisticDAO {
	
	public Map<String, Long> getDataStatistic() {

		Map<String, Long> map = new HashMap<String, Long>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<Object[]> result = em.createQuery("SELECT i.category category, COUNT(i) AS total FROM Institution i GROUP BY i.category ORDER BY i.category ASC").getResultList();

		for (Object[] objects : result) {
			map.put(objects[0].toString(), (Long)objects[1]);
		}
		
		Long count = (Long) em.createQuery("SELECT COUNT(d) AS total FROM Doctor d").getSingleResult();
		map.put(Constant.CATEGORY_DOCTOR, count);
		
		count = (Long) em.createQuery("SELECT COUNT(s) AS total FROM Service s").getSingleResult();
		map.put(Constant.CATEGORY_SERVICE, count);
		
		count = (Long) em.createQuery("SELECT COUNT(i) AS total FROM Insurance i").getSingleResult();
		map.put(Constant.CATEGORY_INSURANCE, count);

		em.close();
		
		return map;
	}
	
	public Map<String, Long> getSearchTypeStatistic() {

		Map<String, Long> map = new HashMap<String, Long>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<Object[]> result = em.createQuery("SELECT s.type type, COUNT(s) AS total FROM Search s GROUP BY s.type ORDER BY s.type ASC").getResultList();

		for (Object[] objects : result) {
			map.put(objects[0].toString(), (Long)objects[1]);
		}
		
		em.close();
		
		return map;
	}
	
	public Map<String, Long> getSearchMethodStatistic() {

		Map<String, Long> map = new HashMap<String, Long>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		Long count = (Long) em.createQuery("SELECT COUNT(s) AS total FROM Search s WHERE s.name IS NOT NULL").getSingleResult();
		map.put(Constant.SEARCH_BY_NAME, count);
		
		count = (Long) em.createQuery("SELECT COUNT(s) AS total FROM Search s WHERE s.street_name IS NOT NULL").getSingleResult();
		map.put(Constant.SEARCH_BY_STREET, count);
		
		count = (Long) em.createQuery("SELECT COUNT(s) AS total FROM Search s WHERE s.region_name IS NOT NULL").getSingleResult();
		map.put(Constant.SEARCH_BY_REGION, count);
		
		count = (Long) em.createQuery("SELECT COUNT(s) AS total FROM Search s WHERE s.distance IS NOT NULL").getSingleResult();
		map.put(Constant.SEARCH_BY_DISTANCE, count);

		em.close();
		
		return map;
	}
	
	public Map<Long, Long> getApptStatistic() {

		Map<Long, Long> map = new HashMap<Long, Long>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		cal.add(Calendar.MONTH, 1);
		
		for (int i = 0; i < 12; i++) {
			
			Date enddate = cal.getTime();
			cal.add(Calendar.MONTH, -1);
			Date startdate = cal.getTime();
			
			Query query = em.createQuery("SELECT COUNT(a) AS total FROM Reservation a WHERE a.appt_create_date BETWEEN :startdate AND :enddate");
			query.setParameter("startdate", startdate);
			query.setParameter("enddate", enddate);
			Long count = (Long) query.getSingleResult();
			map.put(startdate.getTime(), count);
		}
		
		em.close();
		
		return map;
	}
}