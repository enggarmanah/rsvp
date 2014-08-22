package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.model.Branch;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class BranchDAO {
		
	private static final Logger log = Logger.getLogger(BranchDAO.class.getName());
	
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
	
	public List<BranchBean> updateBranches(InstitutionBean institutionBean, List<BranchBean> branchBeans) {

		Map<Long, Branch> branchMap = new HashMap<Long, Branch>();
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Long groupId = null;
		
		for (BranchBean branchBean : branchBeans) {
			
			if (branchBean.getGroupId() != null) {
				groupId = branchBean.getGroupId();
				break;
			}
		}
		
		if (groupId == null) {
			
			String sql = "SELECT DISTINCT group_id FROM Branch b JOIN b.institution i WHERE i.id = :instId";
				
			Query query = em.createQuery(sql);
				
			query.setParameter("instId", institutionBean.getId());
				
			try {
				groupId =  (Long) query.getSingleResult();
			} catch (Exception e) {
				log.warning(e.getMessage());
			}
		}
		
		if (groupId != null) {
			
			String sql = "SELECT b FROM Branch b WHERE b.group_id = :groupId";
			
			TypedQuery<Branch> query = em.createQuery(sql, Branch.class);
			
			query.setParameter("groupId", groupId);
			
			List<Branch> result = query.getResultList();

			for (Branch branch : result) {
				branchMap.put(branch.getInstitution().getId(), branch);
			}
			
		} else {
			
			String sql = "SELECT MAX(group_id) FROM Branch b";
			
			Long maxId = (Long) em.createQuery(sql).getSingleResult();
			
			if (maxId == null) {
				maxId = Long.valueOf(0);
			}
			
			groupId = maxId + 1;
		} 
		
		for (BranchBean branchBean : branchBeans) {
			
			Branch branch = branchMap.remove(branchBean.getInstitution().getId());
			
			if (branch != null) {
				branch.setBean(branchBean, em);
			} else {
				branch = new Branch();
				branch.setBean(branchBean, em);
				branch.setGroup_id(groupId);
				
				em.persist(branch);
				
				branchBean.setId(branch.getId());
			}
		}
		
		Branch mainBranch = branchMap.remove(institutionBean.getId());
		if (mainBranch == null) {
			
			BranchBean branchBean = new BranchBean();
			branchBean.setInstitution(institutionBean);
			branchBean.setGroupId(groupId);
			branchBean.setUpdateBy(institutionBean.getUpdateBy());
			
			Branch branch = new Branch();
			branch.setBean(branchBean, em);
			em.persist(branch);
		}
		
		for (Long key : branchMap.keySet()) {
			Branch branch = branchMap.get(key);
			em.remove(branch);
		}
		
		em.close();

		return branchBeans;
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
	
	public Long getGroupId(Long instId) {
		
		Long groupId = null;
		
		String sql = "SELECT b FROM Branch b JOIN b.institution i WHERE i.id = :instId";
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		TypedQuery<Branch> query = em.createQuery(sql, Branch.class);
		
		query.setParameter("instId", instId);
		
		Branch branch = null;
		
		try {
			branch = query.getSingleResult();
		} catch (Exception e) {
			log.warning(e.getMessage());
		}
		
		em.close();
		
		if (branch != null) {
			groupId = branch.getGroup_id();
		}

		return groupId;
	}
}
