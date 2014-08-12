package com.infoklinik.rsvp.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.UserBean;

@RemoteServiceRelativePath("commonService")
public interface CommonService extends RemoteService {

	UserBean getUserInfo();
	
	UserBean logout();
}
