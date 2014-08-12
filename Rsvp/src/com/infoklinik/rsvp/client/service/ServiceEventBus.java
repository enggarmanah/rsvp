package com.infoklinik.rsvp.client.service;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.maps.gwt.client.LatLng;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.service.presenter.ServiceInfoPresenter;
import com.infoklinik.rsvp.client.service.presenter.ServiceSearchPresenter;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = ServiceInfoPresenter.class, module = ServiceModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface ServiceEventBus extends EventBus {
	
	@Event(forwardToParent = true)
	public void loadChildModuleView(IsWidget widget);
	
	@Event(forwardToParent = true)
	public void setSearchPanel(IsWidget widget);
	
	@Event(forwardToParent = true)
	public void loadPartner();
	
	@Event(forwardToParent = true)
	public void setSearchLocation(LatLng searchLocation);
	
	@Event(forwardToParent = true)
	public void loadServiceSearchResult(List<ServiceBean> services);
	
	@Event(handlers = ServiceInfoPresenter.class)
	public void loadServiceInfo(ServiceBean service);
	
	@Event(handlers = ServiceSearchPresenter.class)
	public void loadServiceSearch();
	
	@Event(handlers = ServiceSearchPresenter.class)
	public void removeServiceSearch();
}
