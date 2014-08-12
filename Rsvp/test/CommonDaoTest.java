

import java.util.List;
import java.util.Map;

import com.infoklinik.rsvp.server.dao.DoctorDAO;
import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.server.dao.InsuranceDAO;
import com.infoklinik.rsvp.server.dao.ScheduleDAO;
import com.infoklinik.rsvp.server.dao.ServiceDAO;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ServiceBean;

public class CommonDaoTest {
	
	public static void main(String[] args) {
		
		DoctorDAO doctorDAO = new DoctorDAO();
		List<DoctorBean> list = doctorDAO.getDoctors();
		
		for (DoctorBean docBean : list) {
			System.out.println(docBean.getId() + " " + docBean.getName() + " " + docBean.getCreateDate());
		}
		
		Map<String, String> map = doctorDAO.getDoctorStatistic();
		
		for (String key : map.keySet()) {
			System.out.println(key + " : " + map.get(key));
		}
		
		InstitutionDAO instDAO = new InstitutionDAO();
		List<InstitutionBean> instBeans = instDAO.getInstInsurances();
		
		for (InstitutionBean instBean : instBeans) {
			System.out.println("Institution " + instBean.getName() + " " + instBean.getAddress());
			
			for (InsuranceBean insuranceBean : instBean.getInsurances()) {
				System.out.println("Insurance " + insuranceBean.getName());
			}
		}
		
		InsuranceDAO insuranceDAO = new InsuranceDAO();
		List<InsuranceBean> insuranceBeans = insuranceDAO.getInsurances();
		
		for (InsuranceBean insuranceBean : insuranceBeans) {
			System.out.println("Insurance : " + insuranceBean.getName());
		}
		
		ServiceDAO serviceDAO = new ServiceDAO();
		List<ServiceBean> serviceBeans = serviceDAO.getServices();
		
		for (ServiceBean serviceBean : serviceBeans) {
			System.out.println("Service : " + serviceBean.getName() + " is_promo : " + serviceBean.isPromo());
			
			InstitutionBean instBean = serviceBean.getInstitution();
			System.out.println("Institution :  " + instBean.getName());
		}
		
		DoctorBean doctorBean = new DoctorBean();
		doctorBean.setId(new Long(2));
		
		ScheduleDAO scheduleDAO = new ScheduleDAO();
		List<ScheduleBean> scheduleBeans = scheduleDAO.getDoctorSchedules(doctorBean.getId());
		
		for (ScheduleBean scheduleBean : scheduleBeans) {
			System.out.println("Schedule Day : " + scheduleBean.getDay() + " Start : " + scheduleBean.getOpStart());
			
			InstitutionBean instBean = scheduleBean.getInstitutionBean();
			System.out.println("Institution :  " + instBean.getName());
		}
	}
}
