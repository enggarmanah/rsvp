
package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.UserProfileBean;
import com.infoklinik.rsvp.shared.UserProfileSearchBean;

public interface UserProfileServiceAsync {
	
	public void addUserProfile(UserProfileBean userProfileBean, AsyncCallback<UserProfileBean> callback);

	public void updateUserProfile(UserProfileBean userProfileBean, AsyncCallback<UserProfileBean> callback);
	
	public void deleteUserProfile(UserProfileBean userProfileBean, AsyncCallback<UserProfileBean> callback);

	public void getUserProfiles(UserProfileSearchBean userProfileSearch, AsyncCallback<List<UserProfileBean>> callback);
}
