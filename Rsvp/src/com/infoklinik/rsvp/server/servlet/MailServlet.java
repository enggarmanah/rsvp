package com.infoklinik.rsvp.server.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infoklinik.rsvp.server.MailUtil;

@SuppressWarnings("serial")
public class MailServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AdminServlet.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MailUtil.sendEmail();
		
		String str = "Sending email to recipient";
		byte[] bytes = str.getBytes();
		
		ServletOutputStream os = response.getOutputStream();
		
		os.write(bytes);
		os.flush();
		os.close();
		
		log.info("Sending file to recipient successfully");
	}
}
