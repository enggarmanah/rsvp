package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gwt.user.client.ui.SuggestOracle;
import com.infoklinik.rsvp.client.rpc.SuggestService;
import com.infoklinik.rsvp.server.ReferenceUtil;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.dao.CityDAO;
import com.infoklinik.rsvp.server.dao.DoctorDAO;
import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.server.dao.RegionDAO;
import com.infoklinik.rsvp.server.dao.StreetDAO;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InstitutionSearchBean;
import com.infoklinik.rsvp.shared.RegionBean;
import com.infoklinik.rsvp.shared.RegionSearchBean;
import com.infoklinik.rsvp.shared.SearchSuggestion;
import com.infoklinik.rsvp.shared.SharedUtil;
import com.infoklinik.rsvp.shared.StreetBean;
import com.infoklinik.rsvp.shared.StreetSearchBean;
import com.infoklinik.rsvp.shared.SuggestParameter;

@SuppressWarnings("serial")
public class SuggestServiceImpl extends BaseServiceServlet implements SuggestService {

	private static final int MAX_SUGGEST_LIMIT = 20;
	
	public SuggestOracle.Response getPatients(SuggestOracle.Request req) {

		SuggestOracle.Response response = new SuggestOracle.Response();
		List<SearchSuggestion> searchSuggestions = new ArrayList<SearchSuggestion>();

		response.setSuggestions(searchSuggestions);
		
		return response;
	}
	
	public SuggestOracle.Response getDoctors(SuggestOracle.Request req) {
		
		String keyword = req.getQuery();
		
		SuggestOracle.Response response = new SuggestOracle.Response();
		List<SearchSuggestion> searchSuggestions = new ArrayList<SearchSuggestion>();
		
		DoctorDAO doctorDao = new DoctorDAO();
		List<DoctorBean> doctors = doctorDao.getDoctors(keyword);
		
		for (int i = 0; i < doctors.size() && i < MAX_SUGGEST_LIMIT; i++) {
			
			DoctorBean doctor = doctors.get(i);
			String speciality = doctor.getSpeciality().getDescription();
			
			SearchSuggestion sg = new SearchSuggestion(doctor.getId().toString(), 
					doctor.getName() + ", " + speciality, 
					getHighlight(doctor.getName(), keyword)  + ", " + speciality);
			
			searchSuggestions.add(sg);
		}
		
		response.setSuggestions(searchSuggestions);
		
		return response;
	}
	
	public SuggestOracle.Response getInstitutions(SuggestOracle.Request req) {

		SuggestOracle.Response response = new SuggestOracle.Response();
		InstitutionDAO institutionDao = new InstitutionDAO();
		
		SuggestParameter suggestParam = new SuggestParameter();
		suggestParam.setSuggestQuery(req.getQuery());
		
		InstitutionSearchBean instSearch = new InstitutionSearchBean();
		instSearch.setName(suggestParam.getName());
		instSearch.setCategory(suggestParam.getCategory());
		
		String keyword = suggestParam.getName();
		
		List<InstitutionBean> institutions = institutionDao.getInstitutions(instSearch);
		
		List<SearchSuggestion> searchSuggestions = new ArrayList<SearchSuggestion>();
		
		for (int i = 0; i < institutions.size() && i < MAX_SUGGEST_LIMIT; i++) {
			InstitutionBean instBean = institutions.get(i);
			SearchSuggestion sg = new SearchSuggestion(instBean.getId().toString(), 
					instBean.getName() + ", " + instBean.getAddress(), 
					getHighlight(instBean.getName(), 
					keyword) + ", " + instBean.getAddress());
			searchSuggestions.add(sg);
		}

		response.setSuggestions(searchSuggestions);
		
		return response;
	}
	
	public SuggestOracle.Response getCities(SuggestOracle.Request req) {

		SuggestOracle.Response response = new SuggestOracle.Response();
		CityDAO cityDao = new CityDAO();
		
		SuggestParameter suggestParam = new SuggestParameter();
		suggestParam.setSuggestQuery(req.getQuery());
		
		CitySearchBean citySearch = new CitySearchBean();
		citySearch.setName(suggestParam.getName());
		
		String keyword = suggestParam.getName();
		
		List<CityBean> cities = cityDao.getCities(citySearch);
		
		List<SearchSuggestion> searchSuggestions = new ArrayList<SearchSuggestion>();
		
		for (int i = 0; i < cities.size() && i < MAX_SUGGEST_LIMIT; i++) {
			CityBean cityBean = cities.get(i);
			SearchSuggestion sg = new SearchSuggestion(cityBean.getId().toString(), 
					cityBean.getName(), 
					getHighlight(cityBean.getName(), 
					keyword));
			searchSuggestions.add(sg);
		}

		response.setSuggestions(searchSuggestions);
		
		return response;
	}
	
	public SuggestOracle.Response getRegions(SuggestOracle.Request req) {

		SuggestOracle.Response response = new SuggestOracle.Response();
		RegionDAO regionDao = new RegionDAO();
		
		SuggestParameter suggestParam = new SuggestParameter();
		suggestParam.setSuggestQuery(req.getQuery());
		
		RegionSearchBean regionSearch = new RegionSearchBean();
		regionSearch.setName(suggestParam.getName());
		regionSearch.setCityId(ServerUtil.strToLong(suggestParam.getCityId()));
		
		String keyword = suggestParam.getName();
		
		List<RegionBean> regions = regionDao.getRegions(regionSearch);
		
		List<SearchSuggestion> searchSuggestions = new ArrayList<SearchSuggestion>();
		
		for (int i = 0; i < regions.size() && i < MAX_SUGGEST_LIMIT; i++) {
			RegionBean regionBean = regions.get(i);
			SearchSuggestion sg = new SearchSuggestion(regionBean.getId().toString(), 
					regionBean.getName(), 
					getHighlight(regionBean.getName(), 
					keyword));
			searchSuggestions.add(sg);
		}

		response.setSuggestions(searchSuggestions);
		
		return response;
	}
	
	public SuggestOracle.Response getStreets(SuggestOracle.Request req) {

		SuggestOracle.Response response = new SuggestOracle.Response();
		StreetDAO streetDao = new StreetDAO();
		
		SuggestParameter suggestParam = new SuggestParameter();
		suggestParam.setSuggestQuery(req.getQuery());
		
		StreetSearchBean streetSearch = new StreetSearchBean();
		streetSearch.setName(suggestParam.getName());
		streetSearch.setCityId(ServerUtil.strToLong(suggestParam.getCityId()));
		
		String keyword = suggestParam.getName();
		
		List<StreetBean> streets = streetDao.getStreets(streetSearch);
		
		List<SearchSuggestion> searchSuggestions = new ArrayList<SearchSuggestion>();
		
		for (int i = 0; i < streets.size() && i < MAX_SUGGEST_LIMIT; i++) {
			StreetBean streetBean = streets.get(i);
			SearchSuggestion sg = new SearchSuggestion(streetBean.getId().toString(), 
					streetBean.getName(), 
					getHighlight(streetBean.getName(), 
					keyword));
			searchSuggestions.add(sg);
		}

		response.setSuggestions(searchSuggestions);
		
		return response;
	}
	
	public SuggestOracle.Response getMonths(SuggestOracle.Request req) {

		SuggestOracle.Response response = new SuggestOracle.Response();
		ArrayList<String> months = ReferenceUtil.getMonths();
		
		List<SearchSuggestion> searchSuggestions = new ArrayList<SearchSuggestion>();

		for (int i = 0; i < months.size(); i++) {
			if (months.get(i).toUpperCase().indexOf(req.getQuery().toUpperCase()) == 0) {
				SearchSuggestion sg = new SearchSuggestion(String.valueOf(i+1), months.get(i));
				searchSuggestions.add(sg);
			}
		}

		response.setSuggestions(searchSuggestions);
		
		return response;
	}
	
	public SuggestOracle.Response getYears(SuggestOracle.Request req) {

		SuggestOracle.Response response = new SuggestOracle.Response();
		List<SearchSuggestion> searchSuggestions = new ArrayList<SearchSuggestion>();
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		for (int i = 2013; i <= year; i++) {
			if (String.valueOf(i).indexOf(req.getQuery()) == 0) {
				SearchSuggestion sg = new SearchSuggestion(String.valueOf(i));
				searchSuggestions.add(sg);
			}
		}

		response.setSuggestions(searchSuggestions);
		
		return response;
	}
	
	private String getHighlight(String text, String keyword) {
		
		if (SharedUtil.isEmpty(text)) {
			return "";
		}
		
		int i = text.toLowerCase().indexOf(keyword.toLowerCase());
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(text.substring(0, i));
		//sb.append("<span style='font-weight:bold'>");
		sb.append("<span style='color: #111;background: #A5C6D9;'>");
		sb.append(text.substring(i, i + keyword.length()));
		sb.append("</span>");
		sb.append(text.substring(i + keyword.length()));
		
		return sb.toString();
	}
}
