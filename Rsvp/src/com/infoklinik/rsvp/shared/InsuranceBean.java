package com.infoklinik.rsvp.shared;

import java.util.List;

@SuppressWarnings("serial")
public class InsuranceBean extends BaseBean {

	private String name;
	private List<InstitutionBean> institutionBeans;	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<InstitutionBean> getInstitutionBeans() {
		return institutionBeans;
	}

	public void setInstitutionBeans(List<InstitutionBean> institutionBeans) {
		this.institutionBeans = institutionBeans;
	}
}
