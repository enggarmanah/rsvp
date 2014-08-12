package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.CommentService;
import com.infoklinik.rsvp.server.dao.CommentDAO;
import com.infoklinik.rsvp.server.dao.DoctorDAO;
import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.server.dao.ServiceDAO;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;

@SuppressWarnings("serial")
public class CommentServiceImpl extends BaseServiceServlet implements CommentService {
	
	public List<CommentBean> getInstComments(Long instId) {
		
		CommentDAO commentDao = new CommentDAO();
		List<CommentBean> comments = commentDao.getInstComments(instId);
		
		return comments;
	}
	
	public List<CommentBean> getDoctorComments(Long doctorId) {
		
		CommentDAO commentDao = new CommentDAO();
		List<CommentBean> comments = commentDao.getDoctorComments(doctorId);
		
		return comments;
	}
	
	public List<CommentBean> getServiceComments(Long serviceId) {
		
		CommentDAO commentDao = new CommentDAO();
		List<CommentBean> comments = commentDao.getServiceComments(serviceId);
		
		return comments;
	}
	
	public CommentBean addComment(CommentBean comment) {
		
		CommentDAO commentDao = new CommentDAO();
		comment = commentDao.addComment(comment);
		
		if (comment.getInstitution() != null) {
			updateInstCommentCount(comment.getInstitution().getId());
		} else if (comment.getDoctor() != null) {
			updateDoctorCommentCount(comment.getDoctor().getId());
		} else if (comment.getService() != null) {
			updateServiceCommentCount(comment.getService().getId());
		}
		
		return comment;
	}
	
	public CommentBean deleteComment(CommentBean comment) {
		
		CommentDAO commentDao = new CommentDAO();
		comment = commentDao.deleteComment(comment);
		
		if (comment.getInstitution() != null) {
			updateInstCommentCount(comment.getInstitution().getId());
		} else if (comment.getDoctor() != null) {
			updateDoctorCommentCount(comment.getDoctor().getId());
		} else if (comment.getService() != null) {
			updateServiceCommentCount(comment.getService().getId());
		}
		
		return comment;
	}
	
	private void updateInstCommentCount(Long instId) {
		
		CommentDAO commentDao = new CommentDAO();
		Long commentCount = commentDao.getInstCommentsCount(instId);
		
		InstitutionDAO instDao = new InstitutionDAO();
		InstitutionBean institution = instDao.getInstitution(instId);
		institution.setCommentCount(commentCount);
		instDao.updateInstitution(institution);
	}
	
	private void updateDoctorCommentCount(Long doctorId) {
		
		CommentDAO commentDao = new CommentDAO();
		Long commentCount = commentDao.getDoctorCommentsCount(doctorId);
		
		DoctorDAO doctorDao = new DoctorDAO();
		DoctorBean doctor = doctorDao.getDoctor(doctorId);
		doctor.setCommentCount(commentCount);
		doctorDao.updateDoctor(doctor);
	}
	
	private void updateServiceCommentCount(Long serviceId) {
		
		CommentDAO commentDao = new CommentDAO();
		Long commentCount = commentDao.getServiceCommentsCount(serviceId);
		
		ServiceDAO serviceDao = new ServiceDAO();
		ServiceBean service = serviceDao.getService(serviceId);
		service.setCommentCount(commentCount);
		serviceDao.updateService(service);
	}
}
