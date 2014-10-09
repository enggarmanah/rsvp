package com.infoklinik.rsvp.server.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.shared.InstitutionBean;

@SuppressWarnings("serial")
public class InstInfoServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AdminServlet.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletOutputStream os = response.getOutputStream();
		
		StringBuffer html = new StringBuffer();
		
		html.append("<!doctype html>\n");
		html.append("<html>\n");
		html.append("<head>\n");		
		html.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n");
		html.append("<meta name=\"gwt:property\" content=\"locale=id_ID\">\n");
		html.append("<title>Info Klinik</title>\n");
		html.append("<script type=\"text/javascript\" language=\"javascript\" src=\"rsvp/rsvp.nocache.js\"></script>\n");
		html.append("<script type=\"text/javascript\" src=\"http://maps.googleapis.com/maps/api/js?sensor=false\"></script>\n");
		html.append("</head>\n");
		html.append("<body>\n");
		html.append("<div id=\"htmlContent\">\n");
		
		String instId = request.getParameter("id");
		
		InstitutionDAO instDao = new InstitutionDAO();
		InstitutionBean inst = instDao.getInstitution(Long.valueOf(instId)); 
				
		byte[] bytes = null;
		
		html.append(ServerUtil.getInstTypeDescription(inst.getCategory()) + " " + inst.getName()  + "<br/>\n");
		html.append("Alamat : " + inst.getAddress() + "<br/>\n");
		html.append("No. Telepon : " + inst.getTelephone() + "<br/>\n");
		html.append("Email : " + inst.getEmail() + "<br/>\n");
				
		html.append("</div>\n");
		html.append("</body>\n");
		html.append("</html>\n");
		
		bytes = html.toString().getBytes();
		html.setLength(0);
		os.write(bytes);
		
		os.flush();
		os.close();
		
		log.info("Sending file to recipient successfully");
	}
}
