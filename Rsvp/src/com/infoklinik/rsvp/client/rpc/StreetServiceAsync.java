
package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.StreetBean;
import com.infoklinik.rsvp.shared.StreetSearchBean;

public interface StreetServiceAsync {
	
	public void addStreet(StreetBean streetBean, AsyncCallback<StreetBean> callback);

	public void updateStreet(StreetBean streetBean, AsyncCallback<StreetBean> callback);
	
	public void deleteStreet(StreetBean streetBean, AsyncCallback<StreetBean> callback);

	public void getStreets(StreetSearchBean streetSearch, AsyncCallback<List<StreetBean>> callback);
}
