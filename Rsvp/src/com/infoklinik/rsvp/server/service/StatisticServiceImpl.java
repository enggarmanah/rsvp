package com.infoklinik.rsvp.server.service;

import java.util.Map;

import com.infoklinik.rsvp.client.rpc.StatisticService;
import com.infoklinik.rsvp.server.dao.StatisticDAO;

@SuppressWarnings("serial")
public class StatisticServiceImpl extends BaseServiceServlet implements StatisticService {
	
	public Map<String, Long> getDataStatistic() {
		
		StatisticDAO statisticDao = new StatisticDAO();
		Map<String, Long> statistic = statisticDao.getDataStatistic();
		
		return statistic;
	}
}
