package com.infoklinik.rsvp.client.rpc;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StatisticServiceAsync {
	
	public void getDataStatistic(AsyncCallback<Map<String, Long>> callback);
	
	public void getSearchTypeStatistic(AsyncCallback<Map<String, Long>> callback);
	
	public void getSearchMethodStatistic(AsyncCallback<Map<String, Long>> callback);
	
	public void getApptStatistic(AsyncCallback<Map<Long, Long>> callback);
}
