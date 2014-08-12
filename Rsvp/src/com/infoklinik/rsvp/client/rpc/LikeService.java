package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.LikeBean;

@RemoteServiceRelativePath("likeService")
public interface LikeService extends RemoteService {
	
	List<LikeBean> getInstLikes(Long instId);
	
	List<LikeBean> getDoctorLikes(Long doctorId);
	
	List<LikeBean> getServiceLikes(Long serviceId);
	
	LikeBean addLike(LikeBean like);
	
	LikeBean deleteLike(LikeBean like);
}
