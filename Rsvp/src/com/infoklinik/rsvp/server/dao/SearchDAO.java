package com.infoklinik.rsvp.server.dao;

import javax.persistence.EntityManager;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Search;
import com.infoklinik.rsvp.shared.SearchBean;

public class SearchDAO {
	
	public SearchBean addSearch(SearchBean searchBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Search search = new Search();
		search.setBean(searchBean);
		
		em.persist(search);

		em.close();

		return search.getBean();
	}
}