package com.infoklinik.rsvp.client.appt;

import com.google.gwt.user.client.ui.IsWidget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.appt.presenter.AppointmentPresenter;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = AppointmentPresenter.class, module = AppointmentModule.class)
@Debug(logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface AppointmentEventBus extends EventBus {

	@Event(forwardToParent = true)
	public void loadChildModuleView(IsWidget widget);
	
	@Event(handlers = AppointmentPresenter.class)
	public void loadAppointment(ScheduleBean schedule);
}
