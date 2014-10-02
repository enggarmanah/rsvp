package com.infoklinik.rsvp.client.appt.presenter.interfaces;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ScheduleReservationBean;
import com.mvp4g.client.view.LazyView;

public interface IReservationDoctorView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void showLv2();
	
	void hide();
	
	void setSchedulesAndReservations(ScheduleReservationBean scheduleReservation);
	
	ReservationBean getReservation();
	
	void setReservation(ReservationBean reservation);
	
	void setResvDateDbValueChangeHandler(ValueChangeHandler<Date> handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
