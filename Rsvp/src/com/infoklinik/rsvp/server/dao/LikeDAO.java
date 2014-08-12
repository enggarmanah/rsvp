package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Like;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.LikeBean;

public class LikeDAO {
	
	public LikeBean getLike(Long id) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Like like = em.find(Like.class, id);
		LikeBean likeBean = like.getBean();
		
		em.close();
		
		return likeBean;
	}
	
	public LikeBean addLike(LikeBean likeBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Like like = new Like();
		like.setBean(likeBean, em);
		
		em.persist(like);
		em.refresh(like);
		
		likeBean = like.getBean();
		
		em.close();

		return likeBean;
	}
	
	public LikeBean updateLike(LikeBean likeBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Like like = em.find(Like.class, likeBean.getId());
		like.setBean(likeBean, em);

		em.close();
		
		return likeBean;
	}
	
	public void deleteLike(LikeBean like) {

		String sql = Constant.EMPTY_STRING;
		
		if (like.getDoctor() != null) {
			sql = "DELETE FROM Like l WHERE l.doctor_id = :doctorId AND l.fb_id = :fbId";
		} else if (like.getInstitution() != null) {
			sql = "DELETE FROM Like l WHERE l.inst_id = :instId AND l.fb_id = :fbId";
		} else if (like.getService() != null) {
			sql = "DELETE FROM Like l WHERE l.service_id = :serviceId AND l.fb_id = :fbId";
		}
		
		EntityManager em = PersistenceManager.getEntityManager();
		Query query = em.createQuery(sql);
		
		if (like.getDoctor() != null) {
			query.setParameter("doctorId", like.getDoctor().getId());
		} else if (like.getInstitution() != null) {
			query.setParameter("instId", like.getInstitution().getId());
		} else if (like.getService() != null) {
			query.setParameter("serviceId", like.getService().getId());
		}
		
		query.setParameter("fbId", like.getFbId());
		
		query.executeUpdate();
	}
	
	public List<LikeBean> getInstLikes(Long instId) {
		
		List<LikeBean> list = new ArrayList<LikeBean>();
		
		String sql = "SELECT c FROM Like c WHERE c.inst_id = :instId ORDER BY c.create_date DESC";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Like> query = em.createQuery(sql, Like.class);
		
		query.setParameter("instId", instId);
		
		List<Like> result = query.getResultList();

		for (Like like : result) {
			list.add(like.getBean());
		}

		em.close();

		return list;
	}
	
	public Long getInstLikesCount(Long instId) {
		
		String sql = "SELECT COUNT(c.id) FROM Like c WHERE c.inst_id = :instId";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Query query = em.createQuery(sql);
		
		query.setParameter("instId", instId);
		
		Long count = (Long) query.getSingleResult();

		em.close();

		return count;
	}
	
	public List<LikeBean> getDoctorLikes(Long instId) {
		
		List<LikeBean> list = new ArrayList<LikeBean>();
		
		String sql = "SELECT c FROM Like c WHERE c.doctor_id = :doctorId ORDER BY c.create_date DESC";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Like> query = em.createQuery(sql, Like.class);
		
		query.setParameter("doctorId", instId);
		
		List<Like> result = query.getResultList();

		for (Like like : result) {
			list.add(like.getBean());
		}

		em.close();

		return list;
	}
	
	public Long getDoctorLikesCount(Long instId) {
		
		String sql = "SELECT COUNT(c.id) FROM Like c WHERE c.doctor_id = :doctorId";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Query query = em.createQuery(sql);
		
		query.setParameter("doctorId", instId);
		
		Long count = (Long) query.getSingleResult();

		em.close();

		return count;
	}
	
	public List<LikeBean> getServiceLikes(Long serviceId) {
		
		List<LikeBean> list = new ArrayList<LikeBean>();
		
		String sql = "SELECT c FROM Like c WHERE c.service_id = :serviceId ORDER BY c.create_date DESC";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Like> query = em.createQuery(sql, Like.class);
		
		query.setParameter("serviceId", serviceId);
		
		List<Like> result = query.getResultList();

		for (Like like : result) {
			list.add(like.getBean());
		}

		em.close();

		return list;
	}
	
	public Long getServiceLikesCount(Long serviceId) {
		
		String sql = "SELECT COUNT(c.id) FROM Like c WHERE c.service_id = :serviceId";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Query query = em.createQuery(sql);
		
		query.setParameter("serviceId", serviceId);
		
		Long count = (Long) query.getSingleResult();

		em.close();

		return count;
	}
}
