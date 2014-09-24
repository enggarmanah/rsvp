package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.UserProfile;
import com.infoklinik.rsvp.shared.UserProfileBean;
import com.infoklinik.rsvp.shared.UserProfileSearchBean;

public class UserProfileDAO {
	
	public UserProfileBean addUserProfile(UserProfileBean userProfileBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		UserProfile userProfile = new UserProfile();
		userProfile.setBean(userProfileBean);
		
		em.persist(userProfile);
		userProfileBean = userProfile.getBean();

		em.close();

		return userProfileBean;
	}
	
	public UserProfileBean updateUserProfile(UserProfileBean userProfileBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		UserProfile userProfile = em.find(UserProfile.class, userProfileBean.getId());
		userProfile.setBean(userProfileBean);

		em.close();
		
		return userProfileBean;
	}
	
	public UserProfileBean deleteUserProfile(UserProfileBean userProfileBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		UserProfile userProfile = em.find(UserProfile.class, userProfileBean.getId());
		em.remove(userProfile);

		em.close();
		
		return userProfileBean;
	}
	
	public List<UserProfileBean> getUserProfiles(UserProfileSearchBean userProfileSearch) {

		List<UserProfileBean> list = new ArrayList<UserProfileBean>();

		EntityManager em = PersistenceManager.getEntityManager();
		
		ArrayList<String> filters = new ArrayList<String>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("SELECT u FROM UserProfile u");
		
		if (!ServerUtil.isEmpty(userProfileSearch.getName())) {
			
			filters.add("UPPER(u.name) LIKE :name");
			parameters.put("name", "%" + userProfileSearch.getName().toUpperCase() + "%");
		}
		
		if (!ServerUtil.isEmpty(userProfileSearch.getStatus())) {
			
			filters.add("u.status = :status");
			parameters.put("status", userProfileSearch.getStatus());
		}
		
		ServerUtil.setFilter(sql, filters);
		
		sql.append(" ORDER BY u.name");
		
		TypedQuery<UserProfile> query = em.createQuery(sql.toString(), UserProfile.class);
		
		for (String parameter : parameters.keySet()) {
			
			Object value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<UserProfile> result = query.getResultList();

		for (UserProfile userProfile : result) {
			list.add(userProfile.getBean());
		}

		em.close();

		return list;
	}
}