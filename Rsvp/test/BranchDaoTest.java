import com.infoklinik.rsvp.server.dao.BranchDAO;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class BranchDaoTest {
	
	public static void main(String[] args) {
		
		InstitutionBean institution = new InstitutionBean();
		institution.setId(Long.valueOf(1));
		
		BranchBean branchBean = new BranchBean();
		branchBean.setGroupId(Long.valueOf(1));
		branchBean.setInstitution(institution);
		
		BranchDAO branchDao = new BranchDAO();
		branchDao.addBranch(branchBean);
	}
}
