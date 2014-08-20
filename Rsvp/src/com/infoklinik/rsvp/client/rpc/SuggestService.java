package com.infoklinik.rsvp.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.SuggestOracle;

@RemoteServiceRelativePath("suggestService")
public interface SuggestService extends RemoteService {
	
	public SuggestOracle.Response getPatients(SuggestOracle.Request req);

	public SuggestOracle.Response getDoctors(SuggestOracle.Request req);
	
	public SuggestOracle.Response getInstitutions(SuggestOracle.Request req);
	
	public SuggestOracle.Response getCities(SuggestOracle.Request req);
	
	public SuggestOracle.Response getRegions(SuggestOracle.Request req);
	
	public SuggestOracle.Response getStreets(SuggestOracle.Request req);
	
	public SuggestOracle.Response getMonths(SuggestOracle.Request req);
	
	public SuggestOracle.Response getYears(SuggestOracle.Request req);
}
