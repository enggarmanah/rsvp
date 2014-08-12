import java.util.List;

import com.infoklinik.rsvp.server.dao.ScheduleDAO;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class CommentDaoTest {
	
	public static void main(String[] args) {
								
		ScheduleDAO scheduleDao = new ScheduleDAO();
		
		List<ScheduleBean> list = scheduleDao.getDoctorSchedules(Long.valueOf(1)); 
		
		for (ScheduleBean scheduleBean : list) {
			System.out.println(scheduleBean.getId() + " " + scheduleBean.getInstitutionBean().getName() + " " + scheduleBean.getDay() + " " + scheduleBean.getOpStart() + " " + scheduleBean.getOpEnd());
		}
	}
}
