import java.util.List;

import com.infoklinik.rsvp.server.dao.DoctorDAO;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.DoctorSearchBean;

public class DoctorDaoTest {
	
	public static void main(String[] args) {
								
		DoctorDAO doctorDAO = new DoctorDAO();
		
		List<DoctorBean> list = doctorDAO.getDoctors(Long.valueOf(1));
		
		for (DoctorBean docBean : list) {
			System.out.println(docBean.getId() + " " + docBean.getName() + " " + docBean.getCreateDate());
		}
		
		
		DoctorSearchBean doctorSearch = new DoctorSearchBean();
		doctorSearch.setRegionName("Gambir");
		list = doctorDAO.getDoctors(doctorSearch);
		
		for (DoctorBean docBean : list) {
			System.out.println(docBean.getId() + " " + docBean.getName() + " " + docBean.getCreateDate());
		}
	}
}
