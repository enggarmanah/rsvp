package com.infoklinik.rsvp.server.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.shared.InstitutionBean;

@SuppressWarnings("serial")
public class InstDirectoryServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AdminServlet.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletOutputStream os = response.getOutputStream();
		
		StringBuffer html = new StringBuffer();
		
		html.append("<html>");
		
		html.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		html.append("<meta name=\"gwt:property\" content=\"locale=id_ID\">");
		html.append("<title>Info Klinik</title>");
		html.append("<script type=\"text/javascript\" language=\"javascript\" src=\"rsvp/rsvp.nocache.js\"></script>");
		html.append("<script type=\"text/javascript\" src=\"http://maps.googleapis.com/maps/api/js?sensor=false\"></script>");
		html.append("</head>");
		
		html.append("<body>");
		
		InstitutionDAO instDao = new InstitutionDAO();
		List<InstitutionBean> institutions = instDao.getAllInstitutions();
		
		for (InstitutionBean inst : institutions) {
			
			html.append("<a href='/institutionInfo?id='"+ inst.getId() +"'><br/>");
			html.append(inst.getName());
			html.append("</a>");
			
			byte[] bytes = html.toString().getBytes();
			html.setLength(0);
			os.write(bytes);
		}
		
		html.append("</body>");
		
		os.flush();
		os.close();
		
		log.info("Sending file to recipient successfully");
	}
}
