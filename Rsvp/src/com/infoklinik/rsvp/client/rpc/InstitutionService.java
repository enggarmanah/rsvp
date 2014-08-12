package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InstitutionSearchBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;

@RemoteServiceRelativePath("institutionService")
public interface InstitutionService extends RemoteService {
	
	InstitutionBean getInstitution(Long id);
	
	InstitutionBean addInstitution(InstitutionBean institution);
	
	InstitutionBean updateInstitution(InstitutionBean institution);
	
	List<InstitutionBean> getPartners();
	
	List<MasterCodeBean> getInstitutionTypes(String category);
	
	List<InstitutionBean> getInstitutions(InstitutionSearchBean searchBean);
	
	void increaseViewCount(Long instId);
}
