
package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;

public interface CityServiceAsync {
	
	public void addCity(CityBean cityBean, AsyncCallback<CityBean> callback);

	public void updateCity(CityBean cityBean, AsyncCallback<CityBean> callback);
	
	public void deleteCity(CityBean cityBean, AsyncCallback<CityBean> callback);

	public void getCities(CitySearchBean citySearch, AsyncCallback<List<CityBean>> callback);
}
