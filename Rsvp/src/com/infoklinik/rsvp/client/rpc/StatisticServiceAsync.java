package com.infoklinik.rsvp.client.rpc;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StatisticServiceAsync {
	
	public void getDataStatistic(AsyncCallback<Map<String, Long>> callback);
}
