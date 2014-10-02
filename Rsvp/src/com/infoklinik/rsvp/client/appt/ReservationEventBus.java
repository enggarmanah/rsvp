package com.infoklinik.rsvp.client.appt;

import com.google.gwt.user.client.ui.IsWidget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.appt.presenter.ReservationPatientInfoPresenter;
import com.infoklinik.rsvp.client.appt.presenter.ReservationPatientMobilePresenter;
import com.infoklinik.rsvp.client.appt.presenter.ReservationDoctorPresenter;
import com.infoklinik.rsvp.client.appt.presenter.ReservationServicePresenter;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = ReservationDoctorPresenter.class, module = ReservationModule.class)
@Debug(logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface ReservationEventBus extends EventBus {

	@Event(forwardToParent = true)
	public void loadChildModuleView(IsWidget widget);
	
	@Event(handlers = ReservationDoctorPresenter.class)
	public void loadReservationDoctor(ScheduleBean schedule);
	
	@Event(handlers = ReservationDoctorPresenter.class)
	public void loadReservationDoctorLv2(ScheduleBean schedule);
	
	@Event(handlers = ReservationServicePresenter.class)
	public void loadReservationService(ServiceBean service);
	
	@Event(handlers = ReservationServicePresenter.class)
	public void loadReservationServiceLv2(ServiceBean schedule);
	
	@Event(handlers = ReservationDoctorPresenter.class)
	public void selectAnotherDate(ReservationBean reservation);
	
	@Event(handlers = ReservationPatientMobilePresenter.class)
	public void verifyPatientMobile(ReservationBean reservation);
	
	@Event(handlers = ReservationPatientInfoPresenter.class)
	public void getPatientInfo(ReservationBean reservation);
}
