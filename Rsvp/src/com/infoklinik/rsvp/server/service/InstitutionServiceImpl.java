package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.InstitutionService;
import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.server.dao.MasterCodeDAO;
import com.infoklinik.rsvp.server.dao.SearchDAO;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstDistanceComparator;
import com.infoklinik.rsvp.shared.GisLatLng;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InstitutionSearchBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.SearchBean;
import com.infoklinik.rsvp.shared.SharedUtil;

@SuppressWarnings("serial")
public class InstitutionServiceImpl extends BaseServiceServlet implements InstitutionService {
	
	public InstitutionBean getInstitution(Long id) {
		
		InstitutionDAO institutionDao = new InstitutionDAO();
		InstitutionBean institutionBean = institutionDao.getInstitution(id);
		
		return institutionBean;
	}
	
	public InstitutionBean addInstitution(InstitutionBean institution) {
		
		InstitutionDAO institutionDAO = new InstitutionDAO();
		institution = institutionDAO.addInstitution(institution);
		
		return institution;
	}
	
	public InstitutionBean updateInstitution(InstitutionBean institution) {
		
		InstitutionDAO institutionDAO = new InstitutionDAO();
		institution = institutionDAO.updateInstitution(institution);
		
		return institution;
	}
	
	public List<InstitutionBean> getInstitutions(InstitutionSearchBean instSearch) {
		
		SearchBean search = new SearchBean();
		
		InstitutionDAO institutionDao = new InstitutionDAO();
		List<InstitutionBean> institutions = institutionDao.getInstitutions(instSearch);
		
		String codeType = MasterCodeBean.CLINIC_TYPE;
		
		if (Constant.CATEGORY_CLINIC.equals(instSearch.getCategory())) {
			codeType = MasterCodeBean.CLINIC_TYPE;
			search.setType(Constant.SEARCH_CLINIC);
			
		} else if (Constant.CATEGORY_HOSPITAL.equals(instSearch.getCategory())) {
			codeType = MasterCodeBean.HOSPITAL_TYPE;
			search.setType(Constant.SEARCH_HOSPITAL);
			
		} else if (Constant.CATEGORY_LABORATORY.equals(instSearch.getCategory())) {
			codeType = MasterCodeBean.LAB_TYPE;
			search.setType(Constant.SEARCH_LAB);
		}
		
		search.setName(instSearch.getName());
		search.setCityId(instSearch.getCityId());
		search.setStreetName(instSearch.getStreetName());
		search.setRegionName(instSearch.getRegionName());
		
		if (instSearch.getLocation() != null) {
			search.setDistance(Long.valueOf(instSearch.getLocation().getDistance()));
			search.setLat(instSearch.getLocation().getLat());		
			search.setLng(instSearch.getLocation().getLng());
		}
		
		search.setReqTime(new Date());
		
		SearchDAO searchDao = new SearchDAO();
		searchDao.addSearch(search);
		
		MasterCodeDAO masterCodeDao = new MasterCodeDAO();
	 	List<MasterCodeBean> masterCodes = masterCodeDao.getMasterCodes(codeType);
	 	
	 	HashMap<String, String> institutionTypes = new HashMap<String, String>();
	 	
	 	for (MasterCodeBean masterCode : masterCodes) {
	 		institutionTypes.put(masterCode.getCode(), masterCode.getValue());
	 	}
	 	
	 	for (InstitutionBean institution : institutions) {
	 		institution.setTypeDescription(institutionTypes.get(institution.getType()));
	 	}
	 	
	 	if (instSearch.getLocation() != null) {
	 		
	 		List<InstitutionBean> instDistOutOfRange = new ArrayList<InstitutionBean>();
	 		
 			LocationBean location = instSearch.getLocation();
 			
 			for (InstitutionBean institution : institutions) {
 				
 				GisLatLng searchLatLng = new GisLatLng(location.getLat(), location.getLng());
 				GisLatLng instLatLng = new GisLatLng(institution.getLocationLat(), institution.getLocationLng());
	 			
	 			long distance = SharedUtil.getDistance(searchLatLng, instLatLng);
	 			
	 			institution.setDistance(distance);
	 			
	 			if (distance > location.getDistance()) {
	 				instDistOutOfRange.add(institution);
	 			}
 			}
 			
 			for (InstitutionBean institution : instDistOutOfRange) {
 				institutions.remove(institution);
 		 	}
 			
 			Collections.sort(institutions, new InstDistanceComparator());
 			
 			institutions = institutions.size() > Constant.QUERY_MAX_RESULT ? new ArrayList<InstitutionBean>(institutions.subList(0, Constant.QUERY_MAX_RESULT)) : institutions;
 		}
		
		return institutions;
	}
	
	public List<MasterCodeBean> getInstitutionTypes(String category) {
		
		InstitutionDAO institutionDao = new InstitutionDAO();
		List<MasterCodeBean> instTypes = institutionDao.getInstitutionType(category);
		
		return instTypes;
	}
	
	public List<InstitutionBean> getPartners() {
		
		InstitutionDAO institutionDao = new InstitutionDAO();
		List<InstitutionBean> partners = institutionDao.getPartners();
		
		List<Long> ids = new ArrayList<Long>();
		for (InstitutionBean instBean : partners) {
			ids.add(instBean.getId());
		}
		
		institutionDao.updateDisplayDate(ids);
		
		return partners;
	}
	
	
	public void increaseViewCount(Long instId) {
		
		InstitutionDAO institutionDao = new InstitutionDAO();
		InstitutionBean institution = institutionDao.getInstitution(instId);
		institution.setViewCount(institution.getViewCount() + 1);
		institutionDao.updateInstitution(institution);
	}
}
