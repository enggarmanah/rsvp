package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.ServiceService;
import com.infoklinik.rsvp.server.dao.MasterCodeDAO;
import com.infoklinik.rsvp.server.dao.ServiceDAO;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.GisLatLng;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceDistanceComparator;
import com.infoklinik.rsvp.shared.ServiceSearchBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.SharedUtil;

@SuppressWarnings("serial")
public class ServiceServiceImpl extends BaseServiceServlet implements ServiceService {
	
	public ServiceBean getService(Long serviceId) {
		
		ServiceDAO serviceDao = new ServiceDAO();
		ServiceBean service = serviceDao.getService(serviceId);
		
		return service;
	}
	
	public List<ServiceBean> getPromotions() {
		
		ServiceDAO serviceDao = new ServiceDAO();
		List<ServiceBean> promotions = serviceDao.getPromotions();
		
		return promotions;
	}
	
	public List<ServiceBean> getServices(Long serviceId) {
		
		ServiceDAO serviceDao = new ServiceDAO();
		List<ServiceBean> services = serviceDao.getServices(serviceId);
		
		return services;
	}
	
	public List<ServiceBean> getPromotions(Long serviceId) {
		
		ServiceDAO serviceDao = new ServiceDAO();
		List<ServiceBean> promotions = serviceDao.getPromotions(serviceId);
		
		return promotions;
	}
	
	public List<ServiceBean> getServices(ServiceSearchBean instSearch) {
		
		ServiceDAO serviceDao = new ServiceDAO();
		List<ServiceBean> services = serviceDao.getServices(instSearch);
		
		MasterCodeDAO masterCodeDao = new MasterCodeDAO();
	 	List<MasterCodeBean> masterCodes = masterCodeDao.getMasterCodes(MasterCodeBean.CLINIC_TYPE);
	 	
	 	HashMap<String, String> clinicInstTypes = new HashMap<String, String>();
	 	
	 	for (MasterCodeBean masterCode : masterCodes) {
	 		clinicInstTypes.put(masterCode.getCode(), masterCode.getValue());
	 	}
	 	
	 	masterCodes = masterCodeDao.getMasterCodes(MasterCodeBean.HOSPITAL_TYPE);
	 	
	 	HashMap<String, String> hospitalInstTypes = new HashMap<String, String>();
	 	
	 	for (MasterCodeBean masterCode : masterCodes) {
	 		hospitalInstTypes.put(masterCode.getCode(), masterCode.getValue());
	 	}
	 	
	 	for (ServiceBean service : services) {
	 		
	 		InstitutionBean institution = service.getInstitution();
	 		
	 		if (InstitutionBean.CATEGORY_CLINIC.equals(institution.getCategory())) {
	 			institution.setTypeDescription(clinicInstTypes.get(institution.getType()));
	 		} else if (InstitutionBean.CATEGORY_HOSPITAL.equals(institution.getCategory())) {
	 			institution.setTypeDescription(hospitalInstTypes.get(institution.getType()));
	 		} 
	 	}
		
		if (instSearch.getLocation() != null) {
	 		
	 		List<ServiceBean> instDistOutOfRange = new ArrayList<ServiceBean>();
	 		
 			LocationBean location = instSearch.getLocation();
 			
 			for (ServiceBean service : services) {
 				
 				GisLatLng searchLatLng = new GisLatLng(location.getLat(), location.getLng());
 				GisLatLng instLatLng = new GisLatLng(service.getInstitution().getLocationLat(), service.getInstitution().getLocationLng());
	 			
	 			long distance = SharedUtil.getDistance(searchLatLng, instLatLng);
	 			
	 			service.setDistance(distance);
	 			
	 			if (distance > location.getDistance()) {
	 				instDistOutOfRange.add(service);
	 			}
 			}
 			
 			for (ServiceBean service : instDistOutOfRange) {
 				services.remove(service);
 		 	}
 			
 			Collections.sort(services, new ServiceDistanceComparator());
 			
 			services = services.size() > Constant.QUERY_MAX_RESULT ? new ArrayList<ServiceBean>(services.subList(0, Constant.QUERY_MAX_RESULT)) : services;
 		}
		
		return services;
	}
	
	public void increaseViewCount(Long serviceId) {
		
		ServiceDAO serviceDao = new ServiceDAO();
		ServiceBean service = serviceDao.getService(serviceId);
		service.setViewCount(service.getViewCount() + 1);
		serviceDao.updateService(service);
	}
}
