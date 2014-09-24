package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.DoctorService;
import com.infoklinik.rsvp.server.dao.DoctorDAO;
import com.infoklinik.rsvp.server.dao.SearchDAO;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.DoctorSearchBean;
import com.infoklinik.rsvp.shared.GisLatLng;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.SearchBean;
import com.infoklinik.rsvp.shared.SharedUtil;

@SuppressWarnings("serial")
public class DoctorServiceImpl extends BaseServiceServlet implements DoctorService {
	
	public DoctorBean getDoctor(Long doctorId) {
		
		DoctorDAO doctorDao = new DoctorDAO();
		DoctorBean doctor = doctorDao.getDoctor(doctorId);
		
		return doctor;
	}
	
	public DoctorBean addDoctor(DoctorBean doctor) {
		
		DoctorDAO doctorDao = new DoctorDAO();
		doctor = doctorDao.addDoctor(doctor);
		
		return doctor;
	}
	
	public DoctorBean updateDoctor(DoctorBean doctor) {
		
		DoctorDAO doctorDao = new DoctorDAO();
		doctor = doctorDao.updateDoctor(doctor);
		
		return doctor;
	}
	
	public List<DoctorBean> getDoctors(Long instId) {
		
		DoctorDAO doctorDao = new DoctorDAO();
		List<DoctorBean> doctors = doctorDao.getDoctors(instId);
		
		return doctors;
	}
	
	private void trackSearch(DoctorSearchBean doctorSearch) {
		
		SearchBean search = new SearchBean();
		search.setType(Constant.SEARCH_DOCTOR);
		
		search.setName(doctorSearch.getName());
		search.setCityId(doctorSearch.getCityId());
		search.setStreetName(doctorSearch.getStreetName());
		search.setRegionName(doctorSearch.getRegionName());
		
		if (doctorSearch.getLocation() != null) {
			search.setDistance(Long.valueOf(doctorSearch.getLocation().getDistance()));
			search.setLat(doctorSearch.getLocation().getLat());		
			search.setLng(doctorSearch.getLocation().getLng());
		}
		
		search.setReqTime(new Date());
		
		SearchDAO searchDao = new SearchDAO();
		searchDao.addSearch(search);
	}

	public List<DoctorBean> getDoctors(DoctorSearchBean doctorSearch) {
		
		if (!isAdminUser()) {
			trackSearch(doctorSearch);
		}
		
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
