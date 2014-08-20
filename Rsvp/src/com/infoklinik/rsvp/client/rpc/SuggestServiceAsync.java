package com.infoklinik.rsvp.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public interface SuggestServiceAsync {
	
	public void getPatients(SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback);
	
	public void getDoctors(SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback);
	
	public void getInstitutions(SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback);
	
	public void getCities(SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback);
	
	public void getRegions(SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback);
	
	public void getStreets(SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback);
	
	public void getYears(SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback);
	
	public void getMonths(SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback);
}
