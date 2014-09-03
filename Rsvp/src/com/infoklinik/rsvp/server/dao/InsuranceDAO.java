package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.Insurance;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.InsuranceSearchBean;

public class InsuranceDAO {
	
	public InsuranceBean addInsurance(InsuranceBean insuranceBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Insurance insurance = new Insurance();
		insurance.setBean(insuranceBean);
		
		em.persist(insurance);
		insuranceBean = insurance.getBean();

		em.close();

		return insuranceBean;
	}
	
	public InsuranceBean updateInsurance(InsuranceBean insuranceBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Insurance insurance = em.find(Insurance.class, insuranceBean.getId());
		insurance.setBean(insuranceBean);

		em.close();
		
		return insuranceBean;
	}
	
	public InsuranceBean deleteInsurance(InsuranceBean insuranceBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Insurance insurance = em.find(Insurance.class, insuranceBean.getId());
		em.remove(insurance);

		em.close();
		
		return insuranceBean;
	}
	
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
	
	public List<InsuranceBean> getInsurances(InsuranceSearchBean searchBean) {
		
		List<InsuranceBean> list = new ArrayList<InsuranceBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		ArrayList<String> filters = new ArrayList<String>();
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		StringBuffer sql = new StringBuffer("SELECT s FROM Insurance s");
		
		if (!ServerUtil.isEmpty(searchBean.getName())) {
			
			filters.add("UPPER(s.name) LIKE UPPER(:name)");
			parameters.put("name", "%" + searchBean.getName() + "%");
		}
		
		ServerUtil.setFilter(sql, filters);
		
		sql.append(" ORDER BY s.name ");
		
		TypedQuery<Insurance> query = em.createQuery(sql.toString(), Insurance.class);
		
		for (String parameter : parameters.keySet()) {
			
			String value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<Insurance> result = query.getResultList();

		for (Insurance insurance : result) {
			list.add(insurance.getBean());
		}

		em.close();

		return list;
	}
}
