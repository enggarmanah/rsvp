package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.Region;
import com.infoklinik.rsvp.shared.RegionSearchBean;
import com.infoklinik.rsvp.shared.RegionBean;

public class RegionDAO {

	public List<RegionBean> getRegions(RegionSearchBean regionSearch) {
		
		List<RegionBean> list = new ArrayList<RegionBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		ArrayList<String> filters = new ArrayList<String>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("SELECT r FROM Region r");
		
		if (!ServerUtil.isEmpty(regionSearch.getName())) {
			
			filters.add("UPPER(r.name) LIKE :name");
			parameters.put("name", "%" + regionSearch.getName().toUpperCase() + "%");
		}
		
		if (!ServerUtil.isEmpty(regionSearch.getCityId())) {
			
			filters.add("r.city.id = :cityId");
			parameters.put("cityId", regionSearch.getCityId());
		}
		
		ServerUtil.setFilter(sql, filters);
		
		TypedQuery<Region> query = em.createQuery(sql.toString(), Region.class);
		
		for (String parameter : parameters.keySet()) {
			
			Object value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<Region> result = query.getResultList();

		for (Region region : result) {
			list.add(region.getBean());
		}

		em.close();

		return list;
	}
}