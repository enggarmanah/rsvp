package com.infoklinik.rsvp.server.dao;

import javax.persistence.EntityManager;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Image;
import com.infoklinik.rsvp.shared.ImageBean;

public class ImageDAO {
	
	public ImageBean getImage(Long id) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Image image = em.find(Image.class, id);
		ImageBean imageBean = image.getBean();
		
		em.close();
		
		return imageBean;
	}
	
	public ImageBean addImage(ImageBean imageBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Image image = new Image();
		image.setBean(imageBean);
		
		em.persist(image);

		em.close();

		return image.getBean();
	}
	
	public ImageBean updateImage(ImageBean imageBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Image image = em.find(Image.class, imageBean.getId());
		image.setBean(imageBean);

		em.close();
		
		return imageBean;
	}
	
	public ImageBean deleteImage(ImageBean imageBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Image image = em.find(Image.class, imageBean.getId());
		em.remove(image);

		em.close();
		
		return imageBean;
	}
}
