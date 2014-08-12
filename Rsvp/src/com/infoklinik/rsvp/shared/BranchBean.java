package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class BranchBean extends BaseBean {

	private Long groupId;
	private InstitutionBean institution;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public InstitutionBean getInstitution() {
		return institution;
	}
	public void setInstitution(InstitutionBean institution) {
		this.institution = institution;
	}
}
