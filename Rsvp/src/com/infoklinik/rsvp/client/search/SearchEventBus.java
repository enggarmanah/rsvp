package com.infoklinik.rsvp.client.search;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.maps.gwt.client.LatLng;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.search.presenter.ClinicSearchPresenter;
import com.infoklinik.rsvp.client.search.presenter.DoctorSearchPresenter;
import com.infoklinik.rsvp.client.search.presenter.HospitalSearchPresenter;
import com.infoklinik.rsvp.client.search.presenter.LabSearchPresenter;
import com.infoklinik.rsvp.client.search.presenter.ServiceSearchPresenter;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = ClinicSearchPresenter.class, module = SearchModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface SearchEventBus extends EventBus {

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
	public void loadDoctorSearchResult(List<DoctorBean> doctors);
	
	@Event(forwardToParent = true)
	public void loadServiceSearchResult(List<ServiceBean> services);
	
	@Event(handlers = ClinicSearchPresenter.class)
	public void loadClinicSearch();
	
	@Event(handlers = ClinicSearchPresenter.class)
	public void removeClinicSearch();
	
	@Event(handlers = HospitalSearchPresenter.class)
	public void loadHospitalSearch();
	
	@Event(handlers = HospitalSearchPresenter.class)
	public void removeHospitalSearch();
	
	@Event(handlers = LabSearchPresenter.class)
	public void loadLabSearch();
	
	@Event(handlers = LabSearchPresenter.class)
	public void removeLabSearch();
	
	@Event(handlers = DoctorSearchPresenter.class)
	public void loadDoctorSearch();
	
	@Event(handlers = DoctorSearchPresenter.class)
	public void removeDoctorSearch();
	
	@Event(handlers = ServiceSearchPresenter.class)
	public void loadServiceSearch();
	
	@Event(handlers = ServiceSearchPresenter.class)
	public void removeServiceSearch();
}
