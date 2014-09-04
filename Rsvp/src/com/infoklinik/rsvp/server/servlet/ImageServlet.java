package com.infoklinik.rsvp.server.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infoklinik.rsvp.server.dao.ImageDAO;
import com.infoklinik.rsvp.shared.ImageBean;

@SuppressWarnings("serial")
public class ImageServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AdminServlet.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		if (id == null || id.equals("") || id.equals("null")) {
			throw new ServletException("File Name can't be null or empty");
		}
		
		ImageDAO imageDao = new ImageDAO();
		ImageBean imageBean = imageDao.getImage(Long.valueOf(id));
		
		byte[] bytes = imageBean.getBytes();
		
		response.setContentLength(bytes != null ? bytes.length : 0);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + id + "\"");
		
		Calendar nextMonth = Calendar.getInstance();
		nextMonth.add(Calendar.MONTH, 1);
		
		response.setHeader("Cache-Control", "max-age="+ 30 * 24 * 3600 * 1000);
	    response.setDateHeader("Expires", nextMonth.getTimeInMillis());
		
		
		ServletOutputStream os = response.getOutputStream();
		
		os.write(bytes);
		os.flush();
		os.close();
		
		log.info("File " + id + " downloaded at client successfully");
	}
}
