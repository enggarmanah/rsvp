package com.infoklinik.rsvp.client.doctor;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.LatLng;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.doctor.presenter.DoctorProfilePresenter;
import com.infoklinik.rsvp.client.doctor.presenter.DoctorSearchPresenter;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = DoctorSearchPresenter.class, module = DoctorModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface DoctorEventBus extends EventBus {
	
	@Event(forwardToParent = true)
	public void loadChildModuleView(IsWidget widget);
	
	@Event(forwardToParent = true)
	public void setSearchPanel(Widget widget);
	
	@Event(forwardToParent = true)
	public void loadPartner();
	
	@Event(forwardToParent = true)
	public void setSearchLocation(LatLng searchLocation);
	
	@Event(forwardToParent = true)
	public void loadDoctorSearchResult(List<DoctorBean> doctors);
	
	@Event(handlers = DoctorSearchPresenter.class)
	public void loadDoctorSearch();
	
	@Event(handlers = DoctorSearchPresenter.class)
	public void removeDoctorSearch();
	
	@Event(handlers = DoctorProfilePresenter.class)
	public void loadDoctorProfile(DoctorBean doctorBean);
}
