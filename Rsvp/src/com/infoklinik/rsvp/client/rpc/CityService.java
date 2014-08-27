package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;

@RemoteServiceRelativePath("cityService")
public interface CityService extends RemoteService {
	
	CityBean addCity(CityBean cityBean);

	CityBean updateCity(CityBean cityBean);
	
	CityBean deleteCity(CityBean cityBean);
	
	List<CityBean> getCities(CitySearchBean citySearch);	
}
