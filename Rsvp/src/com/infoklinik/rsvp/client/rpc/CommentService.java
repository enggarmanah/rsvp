package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.CommentBean;

@RemoteServiceRelativePath("commentService")
public interface CommentService extends RemoteService {
	
	List<CommentBean> getInstComments(Long instId);
	
	List<CommentBean> getDoctorComments(Long doctorId);
	
	List<CommentBean> getServiceComments(Long serviceId);
	
	CommentBean addComment(CommentBean comment);
	
	CommentBean deleteComment(CommentBean comment);
}
