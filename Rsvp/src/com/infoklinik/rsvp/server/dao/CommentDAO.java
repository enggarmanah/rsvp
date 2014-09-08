package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Comment;
import com.infoklinik.rsvp.shared.CommentBean;

public class CommentDAO {
	
	public CommentBean getComment(Long id) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Comment comment = em.find(Comment.class, id);
		CommentBean commentBean = comment.getBean();
		
		em.close();
		
		return commentBean;
	}
	
	public CommentBean addComment(CommentBean commentBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Comment comment = new Comment();
		comment.setBean(commentBean, em);
		
		em.persist(comment);
		em.refresh(comment);
		
		commentBean = comment.getBean();
		
		em.close();

		return commentBean;
	}
	
	public CommentBean updateComment(CommentBean commentBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Comment comment = em.find(Comment.class, commentBean.getId());
		comment.setBean(commentBean, em);

		em.close();
		
		return commentBean;
	}
	
	public CommentBean deleteComment(CommentBean commentBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Comment comment = em.find(Comment.class, commentBean.getId());
		em.remove(comment);

		em.close();
		
		return commentBean;
	}
	
	public List<CommentBean> getInstComments(Long instId) {
		
		List<CommentBean> list = new ArrayList<CommentBean>();
		
		String sql = "SELECT c FROM Comment c JOIN c.institution i WHERE i.id = :instId ORDER BY c.create_date DESC";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Comment> query = em.createQuery(sql, Comment.class);
		
		query.setParameter("instId", instId);
		
		List<Comment> result = query.getResultList();

		for (Comment comment : result) {
			list.add(comment.getBean());
		}

		em.close();

		return list;
	}
	
	public Long getInstCommentsCount(Long instId) {
		
		String sql = "SELECT COUNT(c.id) FROM Comment c JOIN c.institution i WHERE i.id = :instId";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Query query = em.createQuery(sql);
		
		query.setParameter("instId", instId);
		
		Long count = (Long) query.getSingleResult();

		em.close();

		return count;
	}
	
	public List<CommentBean> getDoctorComments(Long doctorId) {
		
		List<CommentBean> list = new ArrayList<CommentBean>();
		
		String sql = "SELECT c FROM Comment c JOIN c.doctor d WHERE d.id = :doctorId ORDER BY c.create_date DESC";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Comment> query = em.createQuery(sql, Comment.class);
		
		query.setParameter("doctorId", doctorId);
		
		List<Comment> result = query.getResultList();

		for (Comment comment : result) {
			list.add(comment.getBean());
		}

		em.close();

		return list;
	}
	
	public Long getDoctorCommentsCount(Long doctorId) {
		
		String sql = "SELECT COUNT(c.id) FROM Comment c WHERE c.doctor_id = :doctorId";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Query query = em.createQuery(sql);
		
		query.setParameter("doctorId", doctorId);
		
		Long count = (Long) query.getSingleResult();

		em.close();

		return count;
	}
	
	public List<CommentBean> getServiceComments(Long serviceId) {
		
		List<CommentBean> list = new ArrayList<CommentBean>();
		
		String sql = "SELECT c FROM Comment c JOIN c.service s WHERE s.id = :serviceId ORDER BY c.create_date DESC";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Comment> query = em.createQuery(sql, Comment.class);
		
		query.setParameter("serviceId", serviceId);
		
		List<Comment> result = query.getResultList();

		for (Comment comment : result) {
			list.add(comment.getBean());
		}

		em.close();

		return list;
	}
	
	public Long getServiceCommentsCount(Long serviceId) {
		
		String sql = "SELECT COUNT(c.id) FROM Comment c JOIN c.service s WHERE s.id = :serviceId";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Query query = em.createQuery(sql);
		
		query.setParameter("serviceId", serviceId);
		
		Long count = (Long) query.getSingleResult();

		em.close();

		return count;
	}
}