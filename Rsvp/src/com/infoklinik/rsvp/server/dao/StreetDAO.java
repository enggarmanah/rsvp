package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.Street;
import com.infoklinik.rsvp.shared.StreetBean;
import com.infoklinik.rsvp.shared.StreetSearchBean;

public class StreetDAO {

	public List<StreetBean> getStreets(StreetSearchBean streetSearch) {

		List<StreetBean> list = new ArrayList<StreetBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		ArrayList<String> filters = new ArrayList<String>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("SELECT s FROM Street s");
		
		if (!ServerUtil.isEmpty(streetSearch.getName())) {
			
			filters.add("UPPER(s.name) LIKE :name");
			parameters.put("name", "%" + streetSearch.getName().toUpperCase() + "%");
		}
		
		if (!ServerUtil.isEmpty(streetSearch.getCityId())) {
			
			filters.add("s.city.id = :cityId");
			parameters.put("cityId", streetSearch.getCityId());
		}
		
		ServerUtil.setFilter(sql, filters);
		
		TypedQuery<Street> query = em.createQuery(sql.toString(), Street.class);
		
		for (String parameter : parameters.keySet()) {
			
			Object value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<Street> result = query.getResultList();

		for (Street street : result) {
			list.add(street.getBean());
		}

		em.close();

		return list;
	}
}