package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.BranchBean;

@RemoteServiceRelativePath("branchService")
public interface BranchService extends RemoteService {
	
	List<BranchBean> getBranches(Long instId);	
}
