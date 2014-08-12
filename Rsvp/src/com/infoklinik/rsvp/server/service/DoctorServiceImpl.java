package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.DoctorService;
import com.infoklinik.rsvp.server.dao.DoctorDAO;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.DoctorSearchBean;
import com.infoklinik.rsvp.shared.GisLatLng;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.SharedUtil;

@SuppressWarnings("serial")
public class DoctorServiceImpl extends BaseServiceServlet implements DoctorService {
	
	public DoctorBean getDoctor(Long doctorId) {
		
		DoctorDAO doctorDao = new DoctorDAO();
		DoctorBean doctor = doctorDao.getDoctor(doctorId);
		
		return doctor;
	}
	
	public List<DoctorBean> getDoctors(Long instId) {
		
		DoctorDAO doctorDao = new DoctorDAO();
		List<DoctorBean> doctors = doctorDao.getDoctors(instId);
		
		return doctors;
	}
	
	public List<DoctorBean> getDoctors(DoctorSearchBean doctorSearch) {
		
		DoctorDAO doctorDao = new DoctorDAO();
		List<DoctorBean> doctors = doctorDao.getDoctors(doctorSearch);
		
		if (doctorSearch.getLocation() != null) {
			
			LocationBean location = doctorSearch.getLocation();
			List<DoctorBean> doctorWoInstitution = new ArrayList<DoctorBean>();
			
			for (DoctorBean doctor : doctors) {
			
		 		List<ScheduleBean> instDistOutOfRange = new ArrayList<ScheduleBean>();
		 		
	 			for (ScheduleBean schedule : doctor.getSchedules()) {
	 				
	 				InstitutionBean institution = schedule.getInstitutionBean(); 
	 				
	 				GisLatLng searchLatLng = new GisLatLng(location.getLat(), location.getLng());
	 				GisLatLng instLatLng = new GisLatLng(institution.getLocationLat(), institution.getLocationLng());
		 			
		 			long distance = SharedUtil.getDistance(searchLatLng, instLatLng);
		 			
		 			institution.setDistance(distance);
		 			
		 			if (distance > location.getDistance()) {
		 				instDistOutOfRange.add(schedule);
		 			}
	 			}
	 			
	 			for (ScheduleBean schedule : instDistOutOfRange) {
	 				doctor.getSchedules().remove(schedule);
	 		 	}
	 			
	 			if (doctor.getSchedules().size() == 0) {
	 				doctorWoInstitution.add(doctor);
	 			}
			}
			
			for (DoctorBean doctor : doctorWoInstitution) {
				
				doctors.remove(doctor);
			}
 		}
		
		doctors = doctors.size() > Constant.QUERY_MAX_RESULT ? new ArrayList<DoctorBean>(doctors.subList(0, Constant.QUERY_MAX_RESULT)) : doctors;
		
		return doctors;
	}
	
	public void increaseViewCount(Long doctorId) {
		
		DoctorDAO doctorDao = new DoctorDAO();
		DoctorBean doctor = doctorDao.getDoctor(doctorId);
		doctor.setViewCount(doctor.getViewCount() + 1);
		doctorDao.updateDoctor(doctor);
	}
}
