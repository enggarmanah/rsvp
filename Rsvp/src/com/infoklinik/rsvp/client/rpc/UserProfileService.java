package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.UserProfileBean;
import com.infoklinik.rsvp.shared.UserProfileSearchBean;

@RemoteServiceRelativePath("userProfileService")
public interface UserProfileService extends RemoteService {
	
	UserProfileBean addUserProfile(UserProfileBean userProfileBean);

	UserProfileBean updateUserProfile(UserProfileBean userProfileBean);
	
	UserProfileBean deleteUserProfile(UserProfileBean userProfileBean);
	
	List<UserProfileBean> getUserProfiles(UserProfileSearchBean userProfileSearch);	
}
