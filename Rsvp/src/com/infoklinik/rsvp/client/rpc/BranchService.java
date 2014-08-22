package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

@RemoteServiceRelativePath("branchService")
public interface BranchService extends RemoteService {
	
	List<BranchBean> getBranches(Long instId);
	
	Long getGroupId(Long instId);
	
	List<BranchBean> updateBranches(InstitutionBean institution, List<BranchBean> branches);
}
