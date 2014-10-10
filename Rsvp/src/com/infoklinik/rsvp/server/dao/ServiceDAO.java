package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.Service;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceSearchBean;

public class ServiceDAO {
	
	public ServiceBean getService(Long serviceId) {
		
		ServiceBean serviceBean = null;
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Service service = em.find(Service.class, serviceId);

		if (service != null) {
			service.loadInstitution();
			serviceBean = service.getBean();
		}

		em.close();

		return serviceBean;
	}
	
	public ServiceBean addService(ServiceBean serviceBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Service service = new Service();
		service.setBean(serviceBean, em);
		
		em.persist(service);
		
		serviceBean = service.getBean();

		em.close();
		
		return serviceBean;
	}
	
	public ServiceBean updateService(ServiceBean serviceBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Service service = em.find(Service.class, serviceBean.getId());
		service.setBean(serviceBean, em);

		em.close();
		
		return serviceBean;
	}
	
	public List<ServiceBean> getServices() {

		List<ServiceBean> list = new ArrayList<ServiceBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		List<Service> result = em.createQuery("SELECT s FROM Service s", Service.class).getResultList();

		for (Service service : result) {
			list.add(service.loadInstitution().getBean());
		}

		em.close();

		return list;
	}
	
	public List<ServiceBean> getPromotions() {

		List<ServiceBean> list = new ArrayList<ServiceBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		List<Service> result = em.createQuery("SELECT s FROM Service s WHERE s.is_promo='Y' AND s.status='A'", Service.class).getResultList();

		for (Service service : result) {
			
			service.loadServiceType();
			service.loadInstitution();
			list.add(service.getBean());
		}

		em.close();

		return list;
	}
	
	public List<ServiceBean> getServices(Long institutionId) {

		List<ServiceBean> list = new ArrayList<ServiceBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		String sql = "SELECT s FROM Service s WHERE s.institution.id = :institutionId AND s.status='A'";
		
		TypedQuery<Service> query = em.createQuery(sql, Service.class);
		
		query.setParameter("institutionId", institutionId);
		
		List<Service> result = query.getResultList();		
		
		for (Service service : result) {
			
			service.loadServiceType();
			service.loadInstitution();
			list.add(service.getBean());
		}

		em.close();

		return list;
	}
	
	public List<ServiceBean> getPromotions(Long institutionId) {

		List<ServiceBean> list = new ArrayList<ServiceBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		String sql = "SELECT s FROM Service s WHERE s.institution.id = :institutionId AND s.promo_start_date <= CURRENT_DATE AND CURRENT_DATE <= s.promo_end_date AND s.is_promo='Y' AND s.status='A'";
		
		TypedQuery<Service> query = em.createQuery(sql, Service.class);
		
		query.setParameter("institutionId", institutionId);
		
		List<Service> result = query.getResultList();		
		
		for (Service service : result) {
			
			service.loadServiceType();
			service.loadInstitution();
			list.add(service.getBean());
		}

		em.close();

		return list;
	}
	
	public List<ServiceBean> getServices(ServiceSearchBean serviceSearch) {
		
		List<ServiceBean> list = new ArrayList<ServiceBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		ArrayList<String> filters = new ArrayList<String>();
		ArrayList<String> joins = new ArrayList<String>();
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("SELECT DISTINCT s FROM Service s");
		
		if (!ServerUtil.isEmpty(serviceSearch.getServiceTypeId())) {
			
			filters.add("UPPER(s.serviceType.id) = :serviceTypeId");
			parameters.put("serviceTypeId", serviceSearch.getServiceTypeId());
		}
		
		if (!ServerUtil.isEmpty(serviceSearch.getName())) {
			
			filters.add("UPPER(s.name) LIKE :name");
			parameters.put("name", "%" + serviceSearch.getName().toUpperCase() + "%");
		}
		
		if (!ServerUtil.isEmpty(serviceSearch.getStreetName())) {
			
			filters.add("UPPER(s.street.name) LIKE :streetName");
			parameters.put("streetName", "%" + serviceSearch.getStreetName().toUpperCase() + "%");
		}
		
		if (!ServerUtil.isEmpty(serviceSearch.getRegionName())) {
			
			filters.add("UPPER(s.region.name) LIKE :regionName");
			parameters.put("regionName", "%" + serviceSearch.getRegionName().toUpperCase() + "%");
		}
		
		if (serviceSearch.getLocation() != null) {
			
			LocationBean location = serviceSearch.getLocation();
			
			filters.add("s.institution.location_lat >= :locationLatMin");
			parameters.put("locationLatMin", location.getLat() - location.getDistance() * Constant.MAP_METER_LENGTH);
			
			filters.add("s.institution.location_lat <= :locationLatMax");
			parameters.put("locationLatMax", location.getLat() + location.getDistance() * Constant.MAP_METER_LENGTH);
			
			filters.add("s.institution.location_lng >= :locationLngMin");
			parameters.put("locationLngMin", location.getLng() - location.getDistance() * Constant.MAP_METER_LENGTH);
			
			filters.add("s.institution.location_lng <= :locationLngMax");
			parameters.put("locationLngMax", location.getLng() + location.getDistance() * Constant.MAP_METER_LENGTH);
		
		} else if (!ServerUtil.isEmpty(serviceSearch.getCityId())) {
			
			filters.add("s.institution.city.id = :cityId");
			parameters.put("cityId", serviceSearch.getCityId());
		}
		
		ServerUtil.setJoin(sql, joins);
		ServerUtil.setFilter(sql, filters);
		
		sql.append(" ORDER BY s.name");
		
		TypedQuery<Service> query = em.createQuery(sql.toString(), Service.class);
		
		if (serviceSearch.getLocation() == null) {
			query = query.setMaxResults(Constant.QUERY_MAX_RESULT);
		}
		
		for (String parameter : parameters.keySet()) {
			
			Object value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<Service> result = query.getResultList();

		for (Service service : result) {
			list.add(service.loadInstitution().loadServiceType().getBean());
		}

		em.close();

		return list;
	}
}
