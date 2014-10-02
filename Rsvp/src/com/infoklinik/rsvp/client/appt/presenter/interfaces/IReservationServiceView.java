package com.infoklinik.rsvp.client.appt.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.mvp4g.client.view.LazyView;

public interface IReservationServiceView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void showLv2();
	
	void hide();
	
	ReservationBean getReservation();
	
	void setReservation(ReservationBean reservation);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
