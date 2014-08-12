package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.LikeBean;

public interface LikeServiceAsync {

	public void getInstLikes(Long instId, AsyncCallback<List<LikeBean>> callback);
	
	public void getDoctorLikes(Long doctorId, AsyncCallback<List<LikeBean>> callback);
	
	public void getServiceLikes(Long serviceId, AsyncCallback<List<LikeBean>> callback);
	
	public void addLike(LikeBean like, AsyncCallback<LikeBean> callback);
	
	public void deleteLike(LikeBean like, AsyncCallback<LikeBean> callback);
}
