package com.infoklinik.rsvp.client.doctor.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.mvp4g.client.view.LazyView;

public interface IDoctorProfileView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void showSchedule();
	
	void showLocation();
	
	void showInfo();
	
	void setDoctor(DoctorBean doctor);
	
	void setSchedules(List<GenericBean<ScheduleBean>> schedules);
	
	void setInstitutions(List<InstitutionBean> institutions);
	
	void setInfo(String info);
	
	void setScheduleMenuClickHandler(ClickHandler handler);

	void setLocationMenuClickHandler(ClickHandler handler);
	
	void setInfoMenuClickHandler(ClickHandler handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
}
