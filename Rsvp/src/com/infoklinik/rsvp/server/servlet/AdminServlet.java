package com.infoklinik.rsvp.server.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AdminServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AdminServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException {

		try {

			UserService userService = UserServiceFactory.getUserService();
			
			String redirection = "";
			
			if (userService.isUserLoggedIn()) {
				redirection = "/";
			} else {
				redirection = userService.createLoginURL("/");
			}
			
			log.info("Redirection : " + redirection);
			res.sendRedirect(redirection);
			
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to perform redirection", e);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		doGet(req, res);
	}
}
