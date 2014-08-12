package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Insurance;
import com.infoklinik.rsvp.shared.InsuranceBean;

public class InsuranceDAO {

	public List<InsuranceBean> getInsurances() {

		List<InsuranceBean> list = new ArrayList<InsuranceBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		List<Insurance> result = em.createQuery("SELECT i FROM Insurance i", Insurance.class).getResultList();

		for (Insurance insurance : result) {
			list.add(insurance.getBean());
		}

		em.close();

		return list;
	}
	
	public List<InsuranceBean> getInsurances(Long institutionId) {

		List<InsuranceBean> list = new ArrayList<InsuranceBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		String sql = "SELECT i FROM Insurance i JOIN i.institutions t WHERE t.id = :institutionId";
		
		TypedQuery<Insurance> query = em.createQuery(sql, Insurance.class);
		
		query.setParameter("institutionId", institutionId);
		
		List<Insurance> result = query.getResultList();

		for (Insurance insurance : result) {
			list.add(insurance.getBean());
		}

		em.close();

		return list;
	}
}
