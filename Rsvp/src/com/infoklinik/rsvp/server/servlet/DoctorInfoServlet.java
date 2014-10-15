package com.infoklinik.rsvp.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.dao.DoctorDAO;
import com.infoklinik.rsvp.server.dao.ScheduleDAO;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;

@SuppressWarnings("serial")
public class DoctorInfoServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(DoctorInfoServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletOutputStream os = response.getOutputStream();
		
		String doctorId = request.getParameter("id");
		
		DoctorDAO doctorDao = new DoctorDAO();
		DoctorBean doctor = doctorDao.getDoctor(Long.valueOf(doctorId)); 
		
		StringBuffer html = new StringBuffer();
		
		html.append("<!doctype html>\n");
		html.append("<html>\n");
		html.append("<head>\n");		
		html.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n");
		html.append("<meta name=\"gwt:property\" content=\"locale=id_ID\">\n");
		html.append("<title>Info Klinik - "+ doctor.getNameWithSpeciality() +"</title>\n");
		html.append("<script type=\"text/javascript\" language=\"javascript\" src=\"rsvp/rsvp.nocache.js\"></script>\n");
		html.append("<script type=\"text/javascript\" src=\"http://maps.googleapis.com/maps/api/js?sensor=false\"></script>\n");
		html.append("</head>\n");
		html.append("<body>\n");
		html.append("<div id=\"htmlContent\">\n");
		
		byte[] bytes = null;
		
		html.append(doctor.getNameWithTitle()  + ".<br/>\n");
		html.append("Spesialisasi: " + doctor.getSpeciality().getDescription() + ".<br/>\n");
		html.append("Profil Dokter: " + doctor.getProfile() + ".<br/>\n");
		html.append("Silahkan klik / pilih jadwal untuk melaksanakan reservasi kunjungan dokter. <br/>\n");
		
		ScheduleDAO scheduleDao = new ScheduleDAO();
		List<ScheduleBean> schedules = scheduleDao.getDoctorSchedules(doctor.getId());
		if (schedules != null && schedules.size() > 0) {
			
			html.append("Lokasi Praktek: <br/>\n");
			
			HashMap<Long, List<ScheduleBean>> instSchedules = new HashMap<Long, List<ScheduleBean>>();
			
			for (ScheduleBean schedule : schedules) {
				
				InstitutionBean inst = schedule.getInstitutionBean(); 
				List<ScheduleBean> scheduleList = instSchedules.get(inst.getId());
				
				if (scheduleList == null) {
					scheduleList = new ArrayList<ScheduleBean>();
				}
				
				scheduleList.add(schedule);
				instSchedules.put(inst.getId(), scheduleList);
			}
			
			for (Long instId : instSchedules.keySet()) {
				
				List<ScheduleBean> scheduleList = instSchedules.get(instId);
				
				String prevDay = Constant.EMPTY_STRING;
				
				for (ScheduleBean schedule : scheduleList) {
					
					if (schedule == scheduleList.get(0)) {
						html.append(schedule.getInstitutionBean().getName() + ".<br/>\n");
						html.append("Alamat: " + schedule.getInstitutionBean().getAddress() + ".<br/>\n");
						html.append("Telepon: " + schedule.getInstitutionBean().getTelephoneMasked() + ".<br/>\n");
						html.append("Jadwal Praktek: ");
					}
					
					String day = ServerUtil.dayToStr(schedule.getDay());
					String timeStr = ServerUtil.timeToStr(schedule.getOpStart()) + "-" +  ServerUtil.timeToStr(schedule.getOpEnd());
					
					if (!day.equalsIgnoreCase(prevDay)) {
						html.append(day + ": " + timeStr  + ". ");
					} else {
						html.append(timeStr  + ". ");
					}
					
					prevDay = day;
				}
				
				html.append("<br/>\n");
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
		
		log.info("Provide doctor info successfully");
	}
}
