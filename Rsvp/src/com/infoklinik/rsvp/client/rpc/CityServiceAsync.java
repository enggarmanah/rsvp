
package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;

public interface CityServiceAsync {

	public void getCities(CitySearchBean citySearch, AsyncCallback<List<CityBean>> callback);
}
