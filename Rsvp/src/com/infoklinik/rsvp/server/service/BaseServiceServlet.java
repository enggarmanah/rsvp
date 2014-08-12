package com.infoklinik.rsvp.server.service;

import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.infoklinik.rsvp.server.ServerSession;
import com.infoklinik.rsvp.server.ServerUtil;

public class BaseServiceServlet extends RemoteServiceServlet {
	
	private static final long serialVersionUID = 5995012989934371359L;

	private static final Logger log = Logger.getLogger(BaseServiceServlet.class.getName());
	
	protected ServerSession getServerSession() {
		
		log.info("Session : " + getThreadLocalRequest().getSession());
		
		ServerSession serverSession = (ServerSession) getThreadLocalRequest().getSession().getAttribute("SERVER_SESSION");
		if (serverSession == null) {
			log.info("Create a new ServerSession");
			serverSession = new ServerSession();
			getThreadLocalRequest().getSession().setAttribute("SERVER_SESSION", serverSession);
		}
		
		return serverSession;
	}
	
	protected void setServerSession(ServerSession serverSession) {
		
		getThreadLocalRequest().getSession().setAttribute("SERVER_SESSION", serverSession);
	}
	
	protected void invalidateSession() {
		
		log.info("invalidate session");
		getThreadLocalRequest().getSession().invalidate();
	}
	
	protected boolean isDevelopment() {
		
		String remoteHost = getThreadLocalRequest().getRemoteHost();
		if (!ServerUtil.isEmpty(remoteHost) && 
		    (remoteHost.indexOf("localhost") != -1 || remoteHost.indexOf("127.0.0.1") != -1)) {
			return true;
		} else {
			return false;
		}
	}
}
