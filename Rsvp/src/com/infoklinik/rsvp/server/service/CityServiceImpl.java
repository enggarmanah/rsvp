package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.CityService;
import com.infoklinik.rsvp.server.dao.CityDAO;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;

@SuppressWarnings("serial")
public class CityServiceImpl extends BaseServiceServlet implements CityService {
	
	public CityBean addCity(CityBean cityBean) {
		
		CityDAO cityDAO = new CityDAO();
		CityBean city = cityDAO.addCity(cityBean);
		
		return city;
	}
	
	public CityBean updateCity(CityBean cityBean) {
		
		CityDAO cityDAO = new CityDAO();
		CityBean city = cityDAO.updateCity(cityBean);
		
		return city;
	}
	
	public CityBean deleteCity(CityBean cityBean) {
		
		CityDAO cityDAO = new CityDAO();
		CityBean city = cityDAO.deleteCity(cityBean);
		
		return city;
	}
	
	public List<CityBean> getCities(CitySearchBean citySearch) {
		
		CityDAO cityDao = new CityDAO();
		List<CityBean> cities = cityDao.getCities(citySearch);
		
		List<Long> ids = new ArrayList<Long>();
		for (CityBean instBean : cities) {
			ids.add(instBean.getId());
		}
		
		return cities;
	}
}
