import java.util.Date;
import java.util.List;

import com.infoklinik.rsvp.server.dao.AppointmentDAO;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.AppointmentSearchBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class AppointmentDaoTest {
	
	public static void main(String[] args) {
		
		DoctorBean doctor = new DoctorBean();
		doctor.setId(Long.valueOf(1));
		
		InstitutionBean institution = new InstitutionBean();
		institution.setId(Long.valueOf(1));
		
		AppointmentBean appointment = new AppointmentBean();
		appointment.setDoctor(doctor);
		appointment.setInstitution(institution);
		appointment.setApptDate(new Date());
		appointment.setApptCreateDate(new Date());
		appointment.setPatientName("John Doe");
		appointment.setPatientBirthYear("1891");
		appointment.setPatientMobile("6598550250");
		appointment.setPatientEmail("john.doe@gmail.com");
		
		AppointmentDAO apptDao = new AppointmentDAO();
		//apptDao.addAppointment(appointment);
		
		AppointmentSearchBean apptSearch = new AppointmentSearchBean();
		apptSearch.setDoctorId(Long.valueOf(1));
		apptSearch.setInstId(Long.valueOf(1));
		//apptSearch.setApptDate(new Date());
		List<AppointmentBean> appts = apptDao.getAppointments(apptSearch);
		System.out.println("completed");
	}
}
