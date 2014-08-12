package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.GalleryBean;

@RemoteServiceRelativePath("galleryService")
public interface GalleryService extends RemoteService {
	
	List<GalleryBean> getGalleries(Long instId);	
}
