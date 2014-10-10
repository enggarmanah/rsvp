package com.infoklinik.rsvp.server.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infoklinik.rsvp.server.dao.ServiceDAO;
import com.infoklinik.rsvp.shared.ServiceBean;

@SuppressWarnings("serial")
public class ServiceInfoServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AdminServlet.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletOutputStream os = response.getOutputStream();
		
		StringBuffer html = new StringBuffer();
		
		String serviceId = request.getParameter("id");
		
		ServiceDAO serviceDao = new ServiceDAO();
		ServiceBean service = serviceDao.getService(Long.valueOf(serviceId)); 
		
		html.append("<!doctype html>\n");
		html.append("<html>\n");
		html.append("<head>\n");		
		html.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n");
		html.append("<meta name=\"gwt:property\" content=\"locale=id_ID\">\n");
		html.append("<title>Info Klinik - " + service.getName() + "</title>\n");
		html.append("<script type=\"text/javascript\" language=\"javascript\" src=\"rsvp/rsvp.nocache.js\"></script>\n");
		html.append("<script type=\"text/javascript\" src=\"http://maps.googleapis.com/maps/api/js?sensor=false\"></script>\n");
		html.append("</head>\n");
		html.append("<body>\n");
		html.append("<div id=\"htmlContent\">\n");
		
		byte[] bytes = null;
		
		html.append(service.getName()  + ".<br/>\n");
		html.append("Deskripsi: " + service.getDescription() + "<br/>\n");
		html.append("Harga: " + service.getPrice() + ".<br/>\n");
		html.append("Lokasi: " + service.getInstitution().getName() + ".<br/>\n");
		html.append("Alamat: " + service.getInstitution().getAddress() + ".<br/>\n");
		html.append("Telepon: " + service.getInstitution().getTelephoneMasked() + ".<br/>\n");
				
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
