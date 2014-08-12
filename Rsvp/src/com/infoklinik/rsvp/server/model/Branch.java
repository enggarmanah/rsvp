package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.infoklinik.rsvp.shared.BranchBean;

@Entity
public class Branch extends Base {
	
	private Long group_id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inst_id")
	private Institution institution;

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long groupId) {
		this.group_id = groupId;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	
	public void setBean(BranchBean branchBean, EntityManager em) {
		
		id = branchBean.getId();
		group_id = branchBean.getGroupId();
		
		if (branchBean.getInstitution() != null) {
			institution = em.find(Institution.class, branchBean.getInstitution().getId());
		}
		
		setAuditBean(branchBean.getAuditBean());
	}
	
	public BranchBean getBean() {
		
		BranchBean branchBean = new BranchBean();
		
		branchBean.setId(id);
		branchBean.setGroupId(group_id);
		
		if (institution != null) {
			branchBean.setInstitution(institution.getBean());
		}
		
		branchBean.setAuditBean(getAuditBean());
		
		return branchBean;
	}
}
