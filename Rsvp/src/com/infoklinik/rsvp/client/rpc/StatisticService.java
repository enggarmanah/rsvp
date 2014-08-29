package com.infoklinik.rsvp.client.rpc;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("statisticService")
public interface StatisticService extends RemoteService {
	
	public Map<String, Long> getDataStatistic();
}
