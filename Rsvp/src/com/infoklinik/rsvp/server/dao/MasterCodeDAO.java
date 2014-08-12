package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.MasterCode;
import com.infoklinik.rsvp.shared.MasterCodeBean;

public class MasterCodeDAO {
	
	public List<MasterCodeBean> getMasterCodes(String type) {

		List<MasterCodeBean> list = new ArrayList<MasterCodeBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		String sql = "SELECT m FROM MasterCode m WHERE m.type = :type AND m.status='A' ORDER BY m.display_order";
		
		TypedQuery<MasterCode> query = em.createQuery(sql, MasterCode.class);
		
		query.setParameter("type", type);
		
		List<MasterCode> result = query.getResultList();

		for (MasterCode inst : result) {
			list.add(inst.getBean());
		}
		
		em.close();

		return list;
	}
}
