package com.infoklinik.rsvp.client.appt.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.ScheduleAppointmentBean;
import com.mvp4g.client.view.LazyView;

public interface IAppointmentView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setSchedulesAndAppointments(ScheduleAppointmentBean scheduleAppointment);
	
	AppointmentBean getAppointment();
	
	void setAppointment(AppointmentBean appointment);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
