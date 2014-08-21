package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.BranchService;
import com.infoklinik.rsvp.server.dao.BranchDAO;
import com.infoklinik.rsvp.shared.BranchBean;

@SuppressWarnings("serial")
public class BranchServiceImpl extends BaseServiceServlet implements BranchService {
	
	public List<BranchBean> getBranches(Long instId) {
		
		BranchDAO branchDao = new BranchDAO();
		List<BranchBean> branches = branchDao.getBranches(instId);
		
		return branches;
	}
	
	public Long getGroupId(Long instId) {
		
		BranchDAO branchDao = new BranchDAO();
		return branchDao.getGroupId(instId);
	}
	
	public List<BranchBean> updateBranches(List<BranchBean> branches) {
		
		BranchDAO branchDao = new BranchDAO();
		branches = branchDao.updateBranches(branches);
		
		return branches;
	}
}
