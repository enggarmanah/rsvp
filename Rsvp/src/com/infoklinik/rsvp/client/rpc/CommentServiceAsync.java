package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.CommentBean;

public interface CommentServiceAsync {

	public void getInstComments(Long instId, AsyncCallback<List<CommentBean>> callback);
	
	public void getDoctorComments(Long commentId, AsyncCallback<List<CommentBean>> callback);
	
	public void getServiceComments(Long serviceId, AsyncCallback<List<CommentBean>> callback);
	
	public void addComment(CommentBean comment, AsyncCallback<CommentBean> callback);
	
	public void deleteComment(CommentBean comment, AsyncCallback<CommentBean> callback);
}
