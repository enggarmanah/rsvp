package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class ScheduleBean extends BaseBean implements Comparable<ScheduleBean> {

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
	
	public void setBean(ScheduleBean schedule) {
		
		id = schedule.getId();
		institution = schedule.getInstitutionBean();
		doctor = schedule.getDoctor();
		day = schedule.getDay();
		opStart = schedule.getOpStart();
		opEnd = schedule.getOpEnd();
		setAuditBean(getAuditBean());
	}
	
	public int compareTo(ScheduleBean schedule) {
		
		int i = this.institution.getName().toLowerCase().compareTo(schedule.getInstitutionBean().getName().toLowerCase());
		
		if (i == 0) {
			i = this.day - schedule.getDay();
		}
		
		if (i == 0) {
			i = this.getOpStart() - schedule.getOpStart();
		}
		
		return i;
	}
}
