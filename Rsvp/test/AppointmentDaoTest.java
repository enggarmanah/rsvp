import java.util.Date;

//import com.infoklinik.rsvp.server.dao.ReservationDAO;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ReservationSearchBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class AppointmentDaoTest {
	
	public static void main(String[] args) {
		
		DoctorBean doctor = new DoctorBean();
		doctor.setId(Long.valueOf(1));
		
		InstitutionBean institution = new InstitutionBean();
		institution.setId(Long.valueOf(1));
		
		ReservationBean appointment = new ReservationBean();
		appointment.setDoctor(doctor);
		appointment.setInstitution(institution);
		appointment.setApptDate(new Date());
		appointment.setApptCreateDate(new Date());
		appointment.setPatientName("John Doe");
		appointment.setPatientBirthYear("1891");
		appointment.setPatientMobile("6598550250");
		appointment.setPatientEmail("john.doe@gmail.com");
		
		//ReservationDAO apptDao = new ReservationDAO();
		//apptDao.addAppointment(appointment);
		
		ReservationSearchBean apptSearch = new ReservationSearchBean();
		apptSearch.setDoctorId(Long.valueOf(1));
		apptSearch.setInstId(Long.valueOf(1));
		apptSearch.setApptDate(new Date());
		//List<AppointmentBean> appts = apptDao.getAppointments(apptSearch);
		System.out.println("completed");
	}
}
