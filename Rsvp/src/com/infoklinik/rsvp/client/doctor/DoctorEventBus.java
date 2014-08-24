package com.infoklinik.rsvp.client.doctor;

import com.google.gwt.user.client.ui.IsWidget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.doctor.presenter.DoctorProfilePresenter;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = DoctorProfilePresenter.class, module = DoctorModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface DoctorEventBus extends EventBus {
	
	@Event(forwardToParent = true)
	public void loadChildModuleView(IsWidget widget);
	
	@Event(handlers = DoctorProfilePresenter.class)
	public void loadDoctorProfile(DoctorBean doctorBean);
}
