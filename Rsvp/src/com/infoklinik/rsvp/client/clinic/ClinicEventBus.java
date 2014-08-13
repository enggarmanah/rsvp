package com.infoklinik.rsvp.client.clinic;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.maps.gwt.client.LatLng;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.clinic.presenter.ClinicSearchPresenter;
import com.infoklinik.rsvp.client.clinic.presenter.ClinicProfilePresenter;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = ClinicSearchPresenter.class, module = ClinicModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface ClinicEventBus extends EventBus {

	@Event(forwardToParent = true)
	public void loadChildModuleView(IsWidget widget);
	
	@Event(forwardToParent = true)
	public void setSearchPanel(IsWidget widget);
	
	@Event(forwardToParent = true)
	public void setSearchLocation(LatLng searchLocation);
	
	@Event(forwardToParent = true)
	public void loadPartner();
	
	@Event(forwardToParent = true)
	public void loadInstitutionSearchResult(List<InstitutionBean> institutions);
	
	@Event(forwardToParent = true)
	public void loadDoctorProfile(DoctorBean doctor);
	
	@Event(handlers = ClinicSearchPresenter.class)
	public void loadClinicSearch();
	
	@Event(handlers = ClinicSearchPresenter.class)
	public void removeClinicSearch();
	
	@Event(handlers = ClinicProfilePresenter.class)
	public void loadClinicProfile(InstitutionBean institution);
}