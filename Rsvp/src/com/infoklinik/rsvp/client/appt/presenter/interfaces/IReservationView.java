package com.infoklinik.rsvp.client.appt.presenter.interfaces;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ScheduleAppointmentBean;
import com.mvp4g.client.view.LazyView;

public interface IReservationView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void showLv2();
	
	void hide();
	
	void setSchedulesAndAppointments(ScheduleAppointmentBean scheduleAppointment);
	
	ReservationBean getAppointment();
	
	void setAppointment(ReservationBean appointment);
	
	void setApptDateDbValueChangeHandler(ValueChangeHandler<Date> handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
