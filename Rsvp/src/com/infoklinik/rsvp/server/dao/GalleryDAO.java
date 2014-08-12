package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Gallery;
import com.infoklinik.rsvp.shared.GalleryBean;

public class GalleryDAO {
	
	public GalleryBean getGallery(Long id) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Gallery gallery = em.find(Gallery.class, id);
		GalleryBean galleryBean = gallery.getBean();
		
		em.close();
		
		return galleryBean;
	}
	
	public GalleryBean addGallery(GalleryBean galleryBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Gallery gallery = new Gallery();
		gallery.setBean(galleryBean, em);
		
		em.persist(gallery);
		
		galleryBean = gallery.getBean();
		
		em.close();

		return galleryBean;
	}
	
	public GalleryBean updateGallery(GalleryBean galleryBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Gallery gallery = em.find(Gallery.class, galleryBean.getId());
		gallery.setBean(galleryBean, em);

		em.close();
		
		return galleryBean;
	}
	
	public GalleryBean deleteGallery(GalleryBean galleryBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Gallery gallery = em.find(Gallery.class, galleryBean.getId());
		em.remove(gallery);

		em.close();
		
		return galleryBean;
	}
	
	public List<GalleryBean> getGalleries(Long instId) {
		
		List<GalleryBean> list = new ArrayList<GalleryBean>();
		
		String sql = "SELECT g FROM Gallery g JOIN g.institution i WHERE i.id = :instId";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Gallery> query = em.createQuery(sql, Gallery.class);
		
		query.setParameter("instId", instId);
		
		List<Gallery> result = query.getResultList();

		for (Gallery gallery : result) {
			list.add(gallery.getBean());
		}

		em.close();

		return list;
	}
}
