package com.infoklinik.rsvp.client.appt;

import com.google.gwt.user.client.ui.IsWidget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.appt.presenter.ReservationPatientInfoPresenter;
import com.infoklinik.rsvp.client.appt.presenter.ReservationPatientMobilePresenter;
import com.infoklinik.rsvp.client.appt.presenter.ReservationPresenter;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = ReservationPresenter.class, module = ReservationModule.class)
@Debug(logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface ReservationEventBus extends EventBus {

	@Event(forwardToParent = true)
	public void loadChildModuleView(IsWidget widget);
	
	@Event(handlers = ReservationPresenter.class)
	public void loadReservation(ScheduleBean schedule);
	
	@Event(handlers = ReservationPresenter.class)
	public void loadReservationLv2(ScheduleBean schedule);
	
	@Event(handlers = ReservationPresenter.class)
	public void selectAnotherDate(ReservationBean appointment);
	
	@Event(handlers = ReservationPatientMobilePresenter.class)
	public void verifyPatientMobile(ReservationBean appointment);
	
	@Event(handlers = ReservationPatientInfoPresenter.class)
	public void getPatientInfo(ReservationBean appointment);
}
