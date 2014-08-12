package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class LikeBean extends BaseBean {

	private InstitutionBean institution;
	private DoctorBean doctor;
	private ServiceBean service;
	private String fbId;
	private String fbName;

	public InstitutionBean getInstitution() {
		return institution;
	}

	public void setInstitution(InstitutionBean institution) {
		this.institution = institution;
	}

	public DoctorBean getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorBean doctor) {
		this.doctor = doctor;
	}

	public ServiceBean getService() {
		return service;
	}

	public void setService(ServiceBean service) {
		this.service = service;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public String getFbName() {
		return fbName;
	}

	public void setFbName(String fbName) {
		this.fbName = fbName;
	}
}
