package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.City;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;

public class CityDAO {

	public List<CityBean> getCities(CitySearchBean citySearch) {

		List<CityBean> list = new ArrayList<CityBean>();
		
		ArrayList<String> filters = new ArrayList<String>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT c FROM City c");
		
		if (!ServerUtil.isEmpty(citySearch.getName())) {
			
			filters.add("UPPER(c.name) LIKE :name");
			parameters.put("name", "%" + citySearch.getName().toUpperCase() + "%");
		}
		
		ServerUtil.setFilter(sql, filters);
		
		TypedQuery<City> query = em.createQuery(sql.toString(), City.class);
		
		for (String parameter : parameters.keySet()) {
			
			Object value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<City> result = query.getResultList();
		
		for (City city : result) {
			list.add(city.getBean());
		}

		em.close();

		return list;
	}
}