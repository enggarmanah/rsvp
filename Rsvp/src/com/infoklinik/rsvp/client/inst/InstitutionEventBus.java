package com.infoklinik.rsvp.client.inst;

import com.google.gwt.user.client.ui.IsWidget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.inst.presenter.InstitutionProfilePresenter;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = InstitutionProfilePresenter.class, module = InstitutionModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface InstitutionEventBus extends EventBus {

	@Event(forwardToParent = true)
	public void loadChildModuleView(IsWidget widget);
	
	@Event(forwardToParent = true)
	public void loadDoctorProfile(DoctorBean doctor);
	
	@Event(handlers = InstitutionProfilePresenter.class)
	public void loadInstitutionProfile(InstitutionBean institution);
}
