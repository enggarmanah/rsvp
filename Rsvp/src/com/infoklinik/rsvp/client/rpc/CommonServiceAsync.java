package com.infoklinik.rsvp.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.UserBean;

public interface CommonServiceAsync {
	
	public void isProductionEnvironment(AsyncCallback<Boolean> callback);
	
	public void getUserInfo(AsyncCallback<UserBean> callback);
	
	public void logout(AsyncCallback<UserBean> callback);
}
