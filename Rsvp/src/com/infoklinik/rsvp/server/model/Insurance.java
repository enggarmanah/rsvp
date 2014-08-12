package com.infoklinik.rsvp.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;

@Entity
public class Insurance extends Base {
	
	private String name;
	
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "insurances")
	private List<Institution> institutions;    
	
	@Transient
	private boolean isLoadInstitution = false; 
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}

	public void setBean(InsuranceBean insuranceBean) {
		
		id = insuranceBean.getId();
		name = insuranceBean.getName();
		
		setAuditBean(insuranceBean.getAuditBean());
	}
	
	public InsuranceBean getBean() {
		
		InsuranceBean insuranceBean = new InsuranceBean();
		
		insuranceBean.setId(id);
		insuranceBean.setName(name);
		
		if (isLoadInstitution) {
			
			List<InstitutionBean> list = new ArrayList<InstitutionBean>();
			for (Institution institution : institutions) {
				list.add(institution.getBean());
			}
			
			insuranceBean.setInstitutionBeans(list);
		}
		
		insuranceBean.setAuditBean(getAuditBean());
		
		return insuranceBean;
	}
	
	public Insurance loadInstitution() {
		
		isLoadInstitution = true;
		
		return this;
	}
}
