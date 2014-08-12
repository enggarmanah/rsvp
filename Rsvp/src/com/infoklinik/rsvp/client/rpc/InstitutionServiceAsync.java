package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InstitutionSearchBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;

public interface InstitutionServiceAsync {
	
	public void getInstitution(Long id, AsyncCallback<InstitutionBean> callback);

	public void addInstitution(InstitutionBean institution, AsyncCallback<InstitutionBean> callback);
	
	public void updateInstitution(InstitutionBean institution, AsyncCallback<InstitutionBean> callback);
	
	public void getPartners(AsyncCallback<List<InstitutionBean>> callback);
	
	public void getInstitutionTypes(String category, AsyncCallback<List<MasterCodeBean>> callback);
	
	public void getInstitutions(InstitutionSearchBean searchBean, AsyncCallback<List<InstitutionBean>> callback);
	
	public void increaseViewCount(Long instId, AsyncCallback<Void> callback);
}
