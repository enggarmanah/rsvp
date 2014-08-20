package com.infoklinik.rsvp.client;

import javax.inject.Singleton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.infoklinik.rsvp.client.rpc.SuggestService;
import com.infoklinik.rsvp.client.rpc.SuggestServiceAsync;
import com.infoklinik.rsvp.shared.SuggestParameter;

@Singleton
public class SuggestionOracle extends SuggestOracle {
	
	public static String SEARCH_NONE = "SEARCH_NONE";
	
	public static String SEARCH_PATIENT = "SEARCH_PATIENT";
	
	public static String SEARCH_DOCTOR = "SEARCH_DOCTOR";
	
	public static String SEARCH_INSTITUTION = "SEARCH_INSTITUTION";
	
	public static String SEARCH_MONTH = "SEARCH_MONTH";
	
	public static String SEARCH_YEAR = "SEARCH_YEAR";
	
	public static String SEARCH_CITY = "SEARCH_CITY";
	
	public static String SEARCH_REGION = "SEARCH_REGION";
	
	public static String SEARCH_STREET = "SEARCH_STREET";
	
	private SuggestParameter suggestParameter;
	
	public SuggestionOracle(SuggestParameter suggestParameter) {
		super();
		this.suggestParameter = suggestParameter;
	}
	
	public void requestSuggestions(SuggestOracle.Request req, SuggestOracle.Callback callback) {
		
		SuggestServiceAsync suggestService = (SuggestServiceAsync) GWT.create(SuggestService.class);
		
		if (SEARCH_INSTITUTION.equals(suggestParameter.getType())) {
			
			suggestParameter.setName(req.getQuery());
			req.setQuery(suggestParameter.getSuggestQuery());
			
			suggestService.getInstitutions(req, new ItemRequestCallback(req, callback));
			
		} else if (SEARCH_PATIENT.equals(suggestParameter.getType())) {
			
			suggestService.getPatients(req, new ItemRequestCallback(req, callback));
		
		} else if (SEARCH_DOCTOR.equals(suggestParameter.getType())) {
			
			suggestService.getDoctors(req, new ItemRequestCallback(req, callback));
			
		} else if (SEARCH_CITY.equals(suggestParameter.getType())) {
			
			suggestParameter.setName(req.getQuery());
			req.setQuery(suggestParameter.getSuggestQuery());
			
			suggestService.getCities(req, new ItemRequestCallback(req, callback));
		
		} else if (SEARCH_REGION.equals(suggestParameter.getType())) {
			
			suggestParameter.setName(req.getQuery());
			req.setQuery(suggestParameter.getSuggestQuery());
			
			suggestService.getRegions(req, new ItemRequestCallback(req, callback));
		
		} else if (SEARCH_STREET.equals(suggestParameter.getType())) {
			
			suggestParameter.setName(req.getQuery());
			req.setQuery(suggestParameter.getSuggestQuery());
			
			suggestService.getStreets(req, new ItemRequestCallback(req, callback));
		
		} else if (SEARCH_MONTH.equals(suggestParameter.getType())) {
			
			suggestService.getMonths(req, new ItemRequestCallback(req, callback));
		
		} else if (SEARCH_YEAR.equals(suggestParameter.getType())) {
			
			suggestService.getYears(req, new ItemRequestCallback(req, callback));
		}
	}

	public boolean isDisplayStringHTML() {
		return true;
	}

	public class ItemRequestCallback implements AsyncCallback<SuggestOracle.Response> {
		
		private SuggestOracle.Request req;
		private SuggestOracle.Callback callback;

		public void onFailure(Throwable caught) {
			callback.onSuggestionsReady(req, new SuggestOracle.Response());
			caught.printStackTrace();
		}

		public void onSuccess(SuggestOracle.Response result) {
			callback.onSuggestionsReady(req, (SuggestOracle.Response) result);
		}

		public ItemRequestCallback(SuggestOracle.Request _req, SuggestOracle.Callback _callback) {
			this.req = _req;
			this.callback = _callback;
		}
	}
}