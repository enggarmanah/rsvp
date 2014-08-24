package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.Doctor;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.DoctorSearchBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.SharedUtil;

public class DoctorDAO {
	
	public DoctorBean getDoctor(Long doctorId) {
		
		DoctorBean doctorBean = null;
		
		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		
		Doctor doctor = em.find(Doctor.class, doctorId); 
		doctorBean = doctor != null ? doctor.getBean() : null;
		
		em.getTransaction().commit();
		em.close();
		
		return doctorBean;
	}
	
	public DoctorBean addDoctor(DoctorBean doctorBean) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Doctor doctor = new Doctor();
		doctor.setBean(doctorBean, em);
		
		doctor.setStatus(Constant.STATUS_ACTIVE);
		doctor.setView_count(Long.valueOf(0));
		doctor.setLike_count(Long.valueOf(0));
		doctor.setComment_count(Long.valueOf(0));
		
		em.persist(doctor);
				
		doctorBean = doctor.getBean();
		
		em.close();
		
		return doctorBean;
	}
	
	public DoctorBean updateDoctor(DoctorBean doctorBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Doctor doctor = em.find(Doctor.class, doctorBean.getId());
		doctor.setBean(doctorBean, em);

		em.close();
		
		return doctorBean;
	}
	
	public List<DoctorBean> getDoctors() {

		List<DoctorBean> list = new ArrayList<DoctorBean>();
		
		String sql = "SELECT d FROM Doctor d";
		
		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		List<Doctor> result = em.createQuery(sql, Doctor.class)
				.getResultList();

		for (Doctor doc : result) {
			list.add(doc.getBean());
		}

		em.getTransaction().commit();
		em.close();

		return list;
	}
	
	public List<DoctorBean> getDoctors(String name) {
		
		name = SharedUtil.isEmpty(name) ? Constant.EMPTY_STRING : name;
		
		List<DoctorBean> list = new ArrayList<DoctorBean>();
		
		String sql = "SELECT DISTINCT d FROM Doctor d WHERE UPPER(d.name) LIKE :name";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Doctor> query = em.createQuery(sql, Doctor.class);
		
		query.setParameter("name", "%" + name.toUpperCase() + "%");
		
		List<Doctor> result = query.getResultList();

		for (Doctor doc : result) {
			list.add(doc.getBean());
		}

		em.close();

		return list;
	}
	
	public List<DoctorBean> getDoctors(Long instId) {

		List<DoctorBean> list = new ArrayList<DoctorBean>();
		
		String sql = "SELECT DISTINCT d FROM Doctor d JOIN d.schedules s WHERE EXISTS (SELECT sc FROM Schedule sc JOIN sc.institution i WHERE s = sc AND i.id = :instId)";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Doctor> query = em.createQuery(sql, Doctor.class);
		
		query.setParameter("instId", instId);
		
		List<Doctor> result = query.getResultList();

		for (Doctor doc : result) {
			list.add(doc.loadSchedules(instId).getBean());
		}

		em.close();

		return list;
	}

	public Map<String, String> getDoctorStatistic() {

		Map<String, String> map = new HashMap<String, String>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<Object[]> result = em.createQuery("SELECT d.speciality.title title, COUNT(d) AS total FROM Doctor d JOIN d.speciality s GROUP BY d.speciality.title ORDER BY d.speciality.title ASC").getResultList();

		for (Object[] objects : result) {
			map.put(objects[0].toString(), objects[1].toString());
		}

		em.close();

		return map;
	}
	
	public List<DoctorBean> getDoctors(DoctorSearchBean doctorSearch) {
		
		List<DoctorBean> list = new ArrayList<DoctorBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		ArrayList<String> filters = new ArrayList<String>();
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer();
		
		if (doctorSearch.getDay() != null) {
			
			filters.add("sc.day = :day");
			parameters.put("day", doctorSearch.getDay());
		}
		
		if (!ServerUtil.isEmpty(doctorSearch.getStreetName())) {
			
			filters.add("UPPER(i.street.name) LIKE :streetName");
			parameters.put("streetName", "%" + doctorSearch.getStreetName().toUpperCase() + "%");
		}
		
		if (!ServerUtil.isEmpty(doctorSearch.getRegionName())) {
			
			filters.add("UPPER(i.region.name) LIKE :regionName");
			parameters.put("regionName", "%" + doctorSearch.getRegionName().toUpperCase() + "%");
		}
		
		if (doctorSearch.getLocation() != null) {
			
			LocationBean location = doctorSearch.getLocation();
			
			filters.add("i.location_lat >= :locationLatMin");
			parameters.put("locationLatMin", location.getLat() - location.getDistance() * Constant.MAP_METER_LENGTH);
			
			filters.add("i.location_lat <= :locationLatMax");
			parameters.put("locationLatMax", location.getLat() + location.getDistance() * Constant.MAP_METER_LENGTH);
			
			filters.add("i.location_lng >= :locationLngMin");
			parameters.put("locationLngMin", location.getLng() - location.getDistance() * Constant.MAP_METER_LENGTH);
			
			filters.add("i.location_lng <= :locationLngMax");
			parameters.put("locationLngMax", location.getLng() + location.getDistance() * Constant.MAP_METER_LENGTH);
		
		} else if (!ServerUtil.isEmpty(doctorSearch.getCityId())) {
			
			filters.add("i.city.id = :cityId");
			parameters.put("cityId", doctorSearch.getCityId());
		}
		
		ServerUtil.setFilterAnd(sql, filters);
		
		filters = new ArrayList<String>();
		
		StringBuffer mainSql = new StringBuffer("SELECT DISTINCT d FROM Doctor d JOIN d.schedules s WHERE "
				+ "(EXISTS (SELECT sc FROM Schedule sc JOIN sc.institution i WHERE s = sc " + sql.toString() + "))");
		
		if (!ServerUtil.isEmpty(doctorSearch.getName())) {
			
			filters.add("UPPER(d.name) LIKE :name");
			parameters.put("name", "%" + doctorSearch.getName().toUpperCase() + "%");
		}
		
		if (!ServerUtil.isEmpty(doctorSearch.getSpecialityId())) {
			
			filters.add("d.speciality.id = :specialityId");
			parameters.put("specialityId", doctorSearch.getSpecialityId());
		}
		
		ServerUtil.setFilterAnd(mainSql, filters);
		
		mainSql.append(" ORDER BY d.name");
		
		System.out.println("SQL : " + mainSql.toString());
		
		TypedQuery<Doctor> query = em.createQuery(mainSql.toString(), Doctor.class);
		
		if (doctorSearch.getLocation() == null) {
			query = query.setMaxResults(Constant.QUERY_MAX_RESULT);
		}
		
		for (String parameter : parameters.keySet()) {
			
			Object value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<Doctor> result = query.getResultList();

		for (Doctor doctor : result) {
			
			DoctorBean doctorBean = doctor.loadSchedules().getBean(); 
			List<ScheduleBean> schedules = new ArrayList<ScheduleBean>();
			
			for (ScheduleBean schedule : doctorBean.getSchedules()) {
				
				InstitutionBean institution = schedule.getInstitutionBean();
				
				boolean isMatch = true;
				
				if (doctorSearch.getDay() != null && !doctorSearch.getDay().equals(schedule.getDay())) {
					isMatch = false;
				}
				
				if (doctorSearch.getLocation() == null && doctorSearch.getCityId() != null && !doctorSearch.getCityId().equals(institution.getCity().getId())) {
					isMatch = false;
				}
				
				if (doctorSearch.getRegionName() != null && !doctorSearch.getRegionName().equals(institution.getRegion().getName())) {
					isMatch = false;
				}
				
				if (doctorSearch.getStreetName() != null && !doctorSearch.getStreetName().equals(institution.getStreet().getName())) {
					isMatch = false;
				}
				
				if (isMatch) {
					schedules.add(schedule);
				}
			}
			
			doctorBean.setSchedules(schedules);
			list.add(doctorBean);
		}

		em.close();

		return list;
	}
}