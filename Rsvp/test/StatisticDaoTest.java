import java.util.Map;

import com.infoklinik.rsvp.server.dao.StatisticDAO;

public class StatisticDaoTest {
	
	public static void main(String[] args) {
								
		StatisticDAO statisticDao = new StatisticDAO();
		Map<String, Long> statistic = statisticDao.getDataStatistic();
		
		for (String key : statistic.keySet()) {
			System.out.println("Key : " + key + ", value : " + statistic.get(key));
		}
	}
}
