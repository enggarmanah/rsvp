package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.ServiceType;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;

public class ServiceTypeDAO {

	public List<ServiceTypeBean> getServiceTypes(ServiceTypeSearchBean searchBean) {
		
		List<ServiceTypeBean> list = new ArrayList<ServiceTypeBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		ArrayList<String> filters = new ArrayList<String>();
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		StringBuffer sql = new StringBuffer("SELECT s FROM ServiceType s");
		
		if (!ServerUtil.isEmpty(searchBean.getName())) {
			
			filters.add("UPPER(s.name) LIKE UPPER(:name)");
			parameters.put("name", "%" + searchBean.getName() + "%");
		}
		
		ServerUtil.setFilter(sql, filters);
		
		sql.append(" ORDER BY s.name ");
		
		TypedQuery<ServiceType> query = em.createQuery(sql.toString(), ServiceType.class);
		
		for (String parameter : parameters.keySet()) {
			
			String value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<ServiceType> result = query.getResultList();

		for (ServiceType serviceType : result) {
			list.add(serviceType.getBean());
		}

		em.close();

		return list;
	}
	
	public ServiceTypeBean addServiceType(ServiceTypeBean serviceTypeBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		ServiceType serviceType = new ServiceType();
		serviceType.setBean(serviceTypeBean);
		
		em.persist(serviceType);

		em.close();

		return serviceType.getBean();
	}
	
	public ServiceTypeBean updateServiceType(ServiceTypeBean serviceTypeBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		ServiceType serviceType = em.find(ServiceType.class, serviceTypeBean.getId());
		serviceType.setBean(serviceTypeBean);

		em.close();
		
		return serviceTypeBean;
	}
	
	public ServiceTypeBean deleteServiceType(ServiceTypeBean serviceTypeBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		ServiceType serviceType = em.find(ServiceType.class, serviceTypeBean.getId());
		em.remove(serviceType);

		em.close();
		
		return serviceTypeBean;
	}
}
