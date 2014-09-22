package com.infoklinik.rsvp.client.main;

import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.main.presenter.MenuPresenter;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = MenuPresenter.class, module = MenuModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface MenuEventBus extends EventBus {

	@Event(forwardToParent = true)
	public void setMenuPanel(Widget widget);
	
	@Event(forwardToParent = true)
	public void setMenuImage(String selectedMenu);
	
	@Event(forwardToParent = true)
	public void loadClinicSearch();
	
	@Event(forwardToParent = true)
	public void removeClinicSearch();
	
	@Event(forwardToParent = true)
	public void loadHospitalSearch();
	
	@Event(forwardToParent = true)
	public void loadLabSearch();
	
	@Event(forwardToParent = true)
	public void removeHospitalSearch();
	
	@Event(forwardToParent = true)
	public void loadDoctorSearch();
	
	@Event(forwardToParent = true)
	public void removeDoctorSearch();
	
	@Event(forwardToParent = true)
	public void loadServiceSearch();
	
	@Event(forwardToParent = true)
	public void removeServiceSearch();
	
	@Event(handlers = MenuPresenter.class)
	public void loadMenu();
}
