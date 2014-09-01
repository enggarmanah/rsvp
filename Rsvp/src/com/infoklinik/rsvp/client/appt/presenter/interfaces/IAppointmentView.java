package com.infoklinik.rsvp.client.appt.presenter.interfaces;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.mvp4g.client.view.LazyView;

public interface IAppointmentView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setDoctor(DoctorBean doctor);
	
	void setInstitution(InstitutionBean institution);
	
	void setDate(Date date);
	
	void setSchedules(List<ScheduleBean> schedules);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
