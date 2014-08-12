package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class ScheduleBean extends BaseBean {

	private InstitutionBean institution;
	private DoctorBean doctor;
	private Integer day;
	private Integer opStart;
	private Integer opEnd;

	public InstitutionBean getInstitutionBean() {
		return institution;
	}

	public void setInstitution(InstitutionBean institutionBean) {
		this.institution = institutionBean;
	}

	public DoctorBean getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorBean doctorBean) {
		this.doctor = doctorBean;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getOpStart() {
		return opStart;
	}

	public void setOpStart(Integer opStart) {
		this.opStart = opStart;
	}

	public Integer getOpEnd() {
		return opEnd;
	}

	public void setOpEnd(Integer opEnd) {
		this.opEnd = opEnd;
	}
	
	public void setBean(ScheduleBean scheduleBean) {
		
		id = scheduleBean.getId();
		institution = scheduleBean.getInstitutionBean();
		doctor = scheduleBean.getDoctor();
		day = scheduleBean.getDay();
		opStart = scheduleBean.getOpStart();
		opEnd = scheduleBean.getOpEnd();
		setAuditBean(getAuditBean());
	}
}
