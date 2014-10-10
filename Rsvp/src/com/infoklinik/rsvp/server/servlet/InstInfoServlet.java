package com.infoklinik.rsvp.server.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infoklinik.rsvp.server.dao.DoctorDAO;
import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;

@SuppressWarnings("serial")
public class InstInfoServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AdminServlet.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletOutputStream os = response.getOutputStream();
		
		String instId = request.getParameter("id");
		
		InstitutionDAO instDao = new InstitutionDAO();
		InstitutionBean inst = instDao.getInstitutionWithServices(Long.valueOf(instId));
		
		StringBuffer html = new StringBuffer();
		
		html.append("<!doctype html>\n");
		html.append("<html>\n");
		html.append("<head>\n");		
		html.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n");
		html.append("<meta name=\"gwt:property\" content=\"locale=id_ID\">\n");
		html.append("<title>Info Klinik - "+ inst.getName() + "</title>\n");
		html.append("<script type=\"text/javascript\" language=\"javascript\" src=\"rsvp/rsvp.nocache.js\"></script>\n");
		html.append("<script type=\"text/javascript\" src=\"http://maps.googleapis.com/maps/api/js?sensor=false\"></script>\n");
		html.append("</head>\n");
		html.append("<body>\n");
		html.append("<div id=\"htmlContent\">\n");
				
		byte[] bytes = null;
		
		html.append(inst.getName()  + ".<br/>\n");
		html.append("Alamat: " + inst.getAddress() + ".<br/>\n");
		html.append("No. Telepon: " + inst.getTelephone() + ".<br/>\n");
		html.append("Email: " + inst.getEmail() + ".<br/>\n");
		html.append("Profil: " + inst.getProfile() + "<br/>\n");
		
		DoctorDAO doctorDao = new DoctorDAO();
		List<DoctorBean> doctors =  doctorDao.getDoctors(inst.getId());
		if (doctors != null && doctors.size() > 0) {
			html.append("Daftar Dokter: <br/>");
			
			for (DoctorBean doctor : doctors) {
				html.append("<a href=\"/doctorInfo?id="+ doctor.getId() +"\">" + doctor.getNameWithTitle() + ".</a><br/>\n");
			}
		}
		
		List<ServiceBean> services = inst.getServices();
		if (services != null && services.size() > 0) {
			html.append("Daftar Layanan: <br/>");
			
			for (ServiceBean service : services) {
				html.append("<a href=\"/serviceInfo?id="+ service.getId() +"\">" + service.getName() + ".</a><br/>\n");
			}
		}		
				
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
