package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.GalleryService;
import com.infoklinik.rsvp.server.dao.GalleryDAO;
import com.infoklinik.rsvp.shared.GalleryBean;

@SuppressWarnings("serial")
public class GalleryServiceImpl extends BaseServiceServlet implements GalleryService {
	
	public List<GalleryBean> getGalleries(Long instId) {
		
		GalleryDAO galleryDao = new GalleryDAO();
		List<GalleryBean> gallerys = galleryDao.getGalleries(instId);
		
		return gallerys;
	}
}
