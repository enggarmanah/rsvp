package com.infoklinik.rsvp.client.search;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.LatLng;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.search.presenter.SearchLocationPresenter;
import com.infoklinik.rsvp.client.search.presenter.SearchPresenter;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = SearchPresenter.class, module = SearchModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface SearchEventBus extends EventBus {
	
	@Event(forwardToParent = true)
	public void setLeftPanel(Widget widget);
	
	@Event(forwardToParent = true)
	public void updateInstitution(InstitutionBean institution);
	
	@Event(forwardToParent = true)
	public void loadInstitutionProfile(InstitutionBean institution);
	
	@Event(forwardToParent = true)
	public void loadDoctorProfile(DoctorBean doctor);
	
	@Event(forwardToParent = true)
	public void loadServiceInfo(ServiceBean service);
	
	@Event(forwardToParent = true)
	public void loadInstitutionComment(InstitutionBean institution);
	
	@Event(forwardToParent = true)
	public void loadDoctorComment(DoctorBean doctor);
	
	@Event(forwardToParent = true)
	public void loadServiceComment(ServiceBean service);
	
	@Event(forwardToParent = true)
	public void loadInstitutionLike(InstitutionBean institution);
	
	@Event(forwardToParent = true)
	public void loadDoctorLike(DoctorBean doctor);
	
	@Event(forwardToParent = true)
	public void loadServiceLike(ServiceBean service);
	
	@Event(handlers = SearchPresenter.class)
	public void loadInstitutionSearchResult(List<InstitutionBean> institutions);
	
	@Event(handlers = SearchPresenter.class)
	public void loadDoctorSearchResult(List<InstitutionBean> institutions);
	
	@Event(handlers = SearchPresenter.class)
	public void loadServiceSearchResult(List<ServiceBean> services);
	
	@Event(handlers = SearchLocationPresenter.class)
	public void setSearchLocation(LatLng searchLocation);
	
	@Event(handlers = SearchLocationPresenter.class)
	public void loadSearchLocations(List<InstitutionBean> institutions);
}
