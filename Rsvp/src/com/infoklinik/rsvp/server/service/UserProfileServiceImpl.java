package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.UserProfileService;
import com.infoklinik.rsvp.server.dao.UserProfileDAO;
import com.infoklinik.rsvp.shared.UserProfileBean;
import com.infoklinik.rsvp.shared.UserProfileSearchBean;

@SuppressWarnings("serial")
public class UserProfileServiceImpl extends BaseServiceServlet implements UserProfileService {
	
	public UserProfileBean addUserProfile(UserProfileBean userProfileBean) {
		
		UserProfileDAO userProfileDAO = new UserProfileDAO();
		UserProfileBean userProfile = userProfileDAO.addUserProfile(userProfileBean);
		
		return userProfile;
	}
	
	public UserProfileBean updateUserProfile(UserProfileBean userProfileBean) {
		
		UserProfileDAO userProfileDAO = new UserProfileDAO();
		UserProfileBean userProfile = userProfileDAO.updateUserProfile(userProfileBean);
		
		return userProfile;
	}
	
	public UserProfileBean deleteUserProfile(UserProfileBean userProfileBean) {
		
		UserProfileDAO userProfileDAO = new UserProfileDAO();
		UserProfileBean userProfile = userProfileDAO.deleteUserProfile(userProfileBean);
		
		return userProfile;
	}
	
	public List<UserProfileBean> getUserProfiles(UserProfileSearchBean userProfileSearchBean) {
		
		UserProfileDAO userProfileDAO = new UserProfileDAO();
		List<UserProfileBean> userProfiles = userProfileDAO.getUserProfiles(userProfileSearchBean);
		
		return userProfiles;
	}
}
