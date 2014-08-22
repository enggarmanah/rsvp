import java.util.ArrayList;
import java.util.List;

import com.infoklinik.rsvp.server.dao.BranchDAO;
import com.infoklinik.rsvp.server.dao.InstitutionDAO;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class BranchDaoTest {
	
	public static void main(String[] args) {
		
		InstitutionDAO instDao = new InstitutionDAO();
		BranchDAO branchDao = new BranchDAO();
		
		InstitutionBean inst1 = instDao.getInstitution(Long.valueOf(2));
		BranchBean b1 = new BranchBean();
		b1.setUpdateBy("System");
		b1.setInstitution(inst1);
		
		InstitutionBean inst2 = instDao.getInstitution(Long.valueOf(23));
		BranchBean b2 = new BranchBean();
		b2.setUpdateBy("System");
		b2.setInstitution(inst2);
		
		InstitutionBean inst3 = instDao.getInstitution(Long.valueOf(24));
		BranchBean b3 = new BranchBean();
		b3.setUpdateBy("System");
		b3.setInstitution(inst3);
		
		List<BranchBean> list = new ArrayList<BranchBean>();
		list.add(b2);
		list.add(b3);
		
		branchDao.updateBranches(inst1, list);
	}
}
