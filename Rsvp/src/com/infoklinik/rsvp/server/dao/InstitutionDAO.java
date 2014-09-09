package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.Institution;
import com.infoklinik.rsvp.server.model.MasterCode;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.InstitutionSearchBean;

public class InstitutionDAO {
	
	public InstitutionBean getInstitution(Long id) {

		InstitutionBean institutionBean = new InstitutionBean();

		EntityManager em = PersistenceManager.getEntityManager();
		
		Institution institution = em.find(Institution.class, id);
		institutionBean = institution.getBean();
		
		em.close();

		return institutionBean;
	}
	
	public InstitutionBean addInstitution(InstitutionBean institutionBean) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Institution institution = new Institution();
		institution.setBean(institutionBean, em);
		
		institution.setStatus(Constant.STATUS_ACTIVE);
		institution.setView_count(Long.valueOf(0));
		institution.setLike_count(Long.valueOf(0));
		institution.setComment_count(Long.valueOf(0));
		
		em.persist(institution);
				
		institutionBean = institution.loadServices().getBean();
		
		em.close();
		
		return institutionBean;
	}
	
	public InstitutionBean updateInstitution(InstitutionBean institutionBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.getTransaction().begin();
		
		Institution institution = em.find(Institution.class, institutionBean.getId());
		institution.setBean(institutionBean, em);
		
		em.getTransaction().commit();
		
		institutionBean = institution.loadServices().getBean();

		em.close();
		
		return institutionBean;
	}
	
	public List<InstitutionBean> getInstInsurances() {

		List<InstitutionBean> list = new ArrayList<InstitutionBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		List<Institution> result = em.createQuery("SELECT i FROM Institution i", Institution.class).getResultList();

		for (Institution inst : result) {
			list.add(inst.loadInsurances().getBean());
		}

		em.close();

		return list;
	}
	
	public List<InstitutionBean> getPartners() {

		List<InstitutionBean> list = new ArrayList<InstitutionBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		
		List<Institution> result = em.createQuery("SELECT i FROM Institution i", Institution.class).getResultList();

		for (Institution inst : result) {
			list.add(inst.getBean());
		}
		
		em.getTransaction().commit();
		em.close();

		return list;
	}
	
	public List<MasterCodeBean> getInstitutionType(String category) {

		List<MasterCodeBean> list = new ArrayList<MasterCodeBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		List<MasterCode> result = em.createQuery("SELECT i FROM InstitutionType i", MasterCode.class).getResultList();

		for (MasterCode inst : result) {
			list.add(inst.getBean());
		}
		
		em.close();

		return list;
	}
	
	public void updateDisplayDate(List<Long> instIds) {
		
		if (instIds != null && instIds.size() > 0) {
			
			EntityManager em = PersistenceManager.getEntityManager();
			em.getTransaction().begin();
			
			String sql = "UPDATE Institution i SET i.display_date = :displayDate WHERE i.id IN (" + ServerUtil.createSqlFilterIdIn(instIds) + ")";
			
			Query query = em.createQuery(sql, Institution.class);
			
			query.setParameter("displayDate", new Date());
			ServerUtil.setSqlParamIdIn(query, instIds);
			
			query.executeUpdate();
	
			em.getTransaction().commit();
			em.close();
		}
	}
	
	public List<InstitutionBean> getInstitutions(InstitutionSearchBean instSearch) {
		
		List<InstitutionBean> list = new ArrayList<InstitutionBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		ArrayList<String> joins = new ArrayList<String>();
		ArrayList<String> filters = new ArrayList<String>();
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("SELECT DISTINCT i FROM Institution i");
		
		if (!ServerUtil.isEmpty(instSearch.getCategory())) {
			
			filters.add("UPPER(i.category) LIKE :category");
			parameters.put("category", instSearch.getCategory());
		}
		
		if (!ServerUtil.isEmpty(instSearch.getName())) {
			
			filters.add("UPPER(i.name) LIKE :name");
			parameters.put("name", "%" + instSearch.getName().toUpperCase() + "%");
		}
		
		if (!ServerUtil.isEmpty(instSearch.getStreetName())) {
			
			filters.add("UPPER(i.street.name) LIKE :streetName");
			parameters.put("streetName", "%" + instSearch.getStreetName().toUpperCase() + "%");
		}
		
		if (!ServerUtil.isEmpty(instSearch.getRegionName())) {
			
			filters.add("UPPER(i.region.name) LIKE :regionName");
			parameters.put("regionName", "%" + instSearch.getRegionName().toUpperCase() + "%");
		}
		
		if (instSearch.getLocation() != null) {
			
			LocationBean location = instSearch.getLocation();
			
			filters.add("i.location_lat >= :locationLatMin");
			parameters.put("locationLatMin", location.getLat() - location.getDistance() * Constant.MAP_METER_LENGTH);
			
			filters.add("i.location_lat <= :locationLatMax");
			parameters.put("locationLatMax", location.getLat() + location.getDistance() * Constant.MAP_METER_LENGTH);
			
			filters.add("i.location_lng >= :locationLngMin");
			parameters.put("locationLngMin", location.getLng() - location.getDistance() * Constant.MAP_METER_LENGTH);
			
			filters.add("i.location_lng <= :locationLngMax");
			parameters.put("locationLngMax", location.getLng() + location.getDistance() * Constant.MAP_METER_LENGTH);
		
		} else if (!ServerUtil.isEmpty(instSearch.getCityId())) {
			
			filters.add("i.city.id = :cityId");
			parameters.put("cityId", instSearch.getCityId());
		}
		
		if (!ServerUtil.isEmpty(instSearch.getType())) {
			
			filters.add("i.type = :type");
			parameters.put("type", instSearch.getType());
		}
		
		if (!ServerUtil.isEmpty(instSearch.getInsuranceId())) {
			
			joins.add("JOIN i.insurances ins");
			filters.add("ins.id = :insuranceId");
			
			parameters.put("insuranceId", instSearch.getInsuranceId());
		}
		
		if (!ServerUtil.isEmpty(instSearch.getSpecialityId())) {
			
			//joins.add("i.schedules s");
			//filters.add("EXISTS (SELECT sc FROM Schedule sc JOIN sc.doctor d WHERE s = sc AND d.speciality.id = :specialityId)");
			
			filters.add("EXISTS (SELECT DISTINCT it FROM Institution it JOIN it.schedules s WHERE i = it AND "
					+ "EXISTS (SELECT sc FROM Schedule sc JOIN sc.doctor d WHERE s = sc AND d.speciality.id = :specialityId))");
						
			parameters.put("specialityId", instSearch.getSpecialityId());
		}
		
		if (!ServerUtil.isEmpty(instSearch.getOpen24Hours())) {
			
			filters.add("i.op_24hours = :op24Hours");
			parameters.put("op24Hours", instSearch.getOpen24Hours());
		}
		
		if (!ServerUtil.isEmpty(instSearch.getResidentialService())) {
			
			filters.add("i.residential_service = :residentialService");
			parameters.put("residentialService", instSearch.getResidentialService());
		}
		
		ServerUtil.setJoin(sql, joins);
		ServerUtil.setFilter(sql, filters);
		
		sql.append(" ORDER BY i.name");
		
		TypedQuery<Institution> query = em.createQuery(sql.toString(), Institution.class);
		
		if (instSearch.getLocation() == null) {
			query = query.setMaxResults(Constant.QUERY_MAX_RESULT);
		}
		
		for (String parameter : parameters.keySet()) {
			
			Object value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<Institution> result = query.getResultList();

		for (Institution institution : result) {
			list.add(institution.getBean());
		}

		em.close();

		return list;
	}
}
