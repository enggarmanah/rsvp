package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.LikeService;
import com.infoklinik.rsvp.server.dao.DoctorDAO;
import com.infoklinik.rsvp.server.dao.LikeDAO;
import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.server.dao.ServiceDAO;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.LikeBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;

@SuppressWarnings("serial")
public class LikeServiceImpl extends BaseServiceServlet implements LikeService {
	
	public List<LikeBean> getInstLikes(Long instId) {
		
		LikeDAO likeDao = new LikeDAO();
		List<LikeBean> likes = likeDao.getInstLikes(instId);
		
		return likes;
	}
	
	public List<LikeBean> getDoctorLikes(Long doctorId) {
		
		LikeDAO likeDao = new LikeDAO();
		List<LikeBean> likes = likeDao.getDoctorLikes(doctorId);
		
		return likes;
	}
	
	public List<LikeBean> getServiceLikes(Long serviceId) {
		
		LikeDAO likeDao = new LikeDAO();
		List<LikeBean> likes = likeDao.getServiceLikes(serviceId);
		
		return likes;
	}
	
	public LikeBean addLike(LikeBean like) {
		
		LikeDAO likeDao = new LikeDAO();
		like = likeDao.addLike(like);
		
		if (like.getInstitution() != null) {
			updateInstLikeCount(like.getInstitution().getId());
		} else if (like.getDoctor() != null) {
			updateDoctorLikeCount(like.getDoctor().getId());
		} else if (like.getService() != null) {
			updateServiceLikeCount(like.getService().getId());
		}
		
		return like;
	}
	
	public LikeBean deleteLike(LikeBean like) {
		
		LikeDAO likeDao = new LikeDAO();
		likeDao.deleteLike(like);
		
		if (like.getInstitution() != null) {
			updateInstLikeCount(like.getInstitution().getId());
		} else if (like.getDoctor() != null) {
			updateDoctorLikeCount(like.getDoctor().getId());
		} else if (like.getService() != null) {
			updateServiceLikeCount(like.getService().getId());
		}
		
		return like;
	}
	
	private void updateInstLikeCount(Long instId) {
		
		LikeDAO likeDao = new LikeDAO();
		Long likeCount = likeDao.getInstLikesCount(instId);
		
		InstitutionDAO instDao = new InstitutionDAO();
		InstitutionBean institution = instDao.getInstitution(instId);
		institution.setLikeCount(likeCount);
		instDao.updateInstitution(institution);
	}
	
	private void updateDoctorLikeCount(Long doctorId) {
		
		LikeDAO likeDao = new LikeDAO();
		Long likeCount = likeDao.getDoctorLikesCount(doctorId);
		
		DoctorDAO doctorDao = new DoctorDAO();
		DoctorBean doctor = doctorDao.getDoctor(doctorId);
		doctor.setLikeCount(likeCount);
		doctorDao.updateDoctor(doctor);
	}
	
	private void updateServiceLikeCount(Long serviceId) {
		
		LikeDAO likeDao = new LikeDAO();
		Long likeCount = likeDao.getServiceLikesCount(serviceId);
		
		ServiceDAO serviceDao = new ServiceDAO();
		ServiceBean service = serviceDao.getService(serviceId);
		service.setLikeCount(likeCount);
		serviceDao.updateService(service);
	}
}
