import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import com.infoklinik.rsvp.server.dao.StatisticDAO;

public class StatisticDaoTest {
	
	public static void main(String[] args) {
								
		StatisticDAO statisticDao = new StatisticDAO();
		Map<String, Long> statistic = statisticDao.getDataStatistic();
		
		for (String key : statistic.keySet()) {
			System.out.println("Key : " + key + ", value : " + statistic.get(key));
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
		
		Map<Long, Long> map = statisticDao.getApptStatistic();
		Object[] keys = map.keySet().toArray();
		Arrays.sort(keys, Collections.reverseOrder());
		for (Object key : keys) {
			System.out.println(sdf.format(key) + " value : " + map.get(key));
		}
	}
}
