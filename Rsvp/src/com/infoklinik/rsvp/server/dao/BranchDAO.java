package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Branch;
import com.infoklinik.rsvp.shared.BranchBean;

public class BranchDAO {
	
	public BranchBean getBranch(Long id) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Branch branch = em.find(Branch.class, id);
		BranchBean branchBean = branch.getBean();
		
		em.close();
		
		return branchBean;
	}
	
	public BranchBean addBranch(BranchBean branchBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Branch branch = new Branch();
		branch.setBean(branchBean, em);
		
		em.persist(branch);
		
		branchBean = branch.getBean();
		
		em.close();

		return branchBean;
	}
	
	public BranchBean updateBranch(BranchBean branchBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Branch branch = em.find(Branch.class, branchBean.getId());
		branch.setBean(branchBean, em);

		em.close();
		
		return branchBean;
	}
	
	public BranchBean deleteBranch(BranchBean branchBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Branch branch = em.find(Branch.class, branchBean.getId());
		em.remove(branch);

		em.close();
		
		return branchBean;
	}
	
	public List<BranchBean> getBranches(Long instId) {
		
		List<BranchBean> list = new ArrayList<BranchBean>();
		
		String sql = "SELECT b FROM Branch b JOIN b.institution i WHERE i.id <> :instId AND b.group_id IN (SELECT bc.group_id FROM Branch bc JOIN bc.institution i WHERE i.id = :instId)";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Branch> query = em.createQuery(sql, Branch.class);
		
		query.setParameter("instId", instId);
		
		List<Branch> result = query.getResultList();

		for (Branch branch : result) {
			list.add(branch.getBean());
		}

		em.close();

		return list;
	}
}
