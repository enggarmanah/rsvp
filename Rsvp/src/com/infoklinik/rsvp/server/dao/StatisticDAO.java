package com.infoklinik.rsvp.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

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
}