package com.infoklinik.rsvp.server.service;

import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.infoklinik.rsvp.client.rpc.CommonService;
import com.infoklinik.rsvp.server.ServerSession;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.shared.UserBean;

@SuppressWarnings("serial")
public class CommonServiceImpl extends BaseServiceServlet implements CommonService {
	
	private static final Logger log = Logger.getLogger(CommonServiceImpl.class.getName());
	
	public UserBean getUserInfo() {
		
		log.info("getUserInfo - Begin");
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		ServerSession serverSession = getServerSession();
		UserBean userInfo = serverSession.getUserInfo();
		
		if (userInfo == null) {
			
			userInfo = new UserBean();
			serverSession.setUserInfo(userInfo);
		}
		
		if (isDevelopment()) {
			
			userInfo.setLoginUrl(userService.createLoginURL("/"));
		
		} else {
			
			String scheme = getThreadLocalRequest().getScheme();
			String serverName = getThreadLocalRequest().getServerName();
			
			ServerUtil.redirectUri = scheme + "://" + serverName;
			log.info("Redirect URI : " + ServerUtil.redirectUri);
			
			userInfo.setLoginUrl(userService.createLoginURL(ServerUtil.redirectUri));
		}
		
		userInfo.setLogoutUrl(userService.createLogoutURL(userInfo.getLoginUrl()));
		
		if (userService.isUserLoggedIn()) {
			
			log.info("Google user : " + user.getNickname() + " / " + user.getEmail());
			
			userInfo.setLogin(true);
			userInfo.setUserId(user.getUserId());
			userInfo.setName(user.getNickname());
			
		} else {
			
			//invalidateSession();
		}
		

		log.info("ServerSession : " + serverSession);
		log.info("IsLogin : " + userInfo.isLogin());
		
		log.info("getUserInfo - End");
		
		setServerSession(serverSession);
		
		return userInfo;
	}
	
	public UserBean logout() {
		
		UserBean userInfo = getServerSession().getUserInfo();
		
		if (userInfo != null) {
			userInfo.setLogin(false);
		}
		
		invalidateSession();
		
		return userInfo;
	}
}
