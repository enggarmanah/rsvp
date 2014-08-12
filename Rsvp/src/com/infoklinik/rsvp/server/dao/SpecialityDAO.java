package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Speciality;
import com.infoklinik.rsvp.shared.SpecialityBean;

public class SpecialityDAO {

	public List<SpecialityBean> getSpecialities() {

		List<SpecialityBean> list = new ArrayList<SpecialityBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		List<Speciality> result = em.createQuery("SELECT s FROM Speciality s", Speciality.class).getResultList();

		for (Speciality speciality : result) {
			list.add(speciality.getBean());
		}

		em.close();

		return list;
	}
}
