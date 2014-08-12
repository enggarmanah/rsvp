import java.util.List;

import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InstitutionSearchBean;

public class InstitutionDaoTest {
	
	public static void main(String[] args) {
								
		InstitutionDAO instDao = new InstitutionDAO();
		
		InstitutionSearchBean instSearch = new InstitutionSearchBean();
		instSearch.setCategory("CLI");
		instSearch.setCityId(Long.valueOf(3));
		instSearch.setSpecialityId(Long.valueOf(1));
		
		List<InstitutionBean> list = instDao.getInstitutions(instSearch);
		
		for (InstitutionBean inst : list) {
			
			System.out.println("institution : " + inst.getName());
		}
		
		InstitutionBean inst = instDao.getInstitution(Long.valueOf(1));
		inst.setCommentCount(Long.valueOf(5));
		instDao.updateInstitution(inst);
	}
}
