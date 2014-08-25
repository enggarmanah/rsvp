package com.infoklinik.rsvp.client.partner;

import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.partner.presenter.PartnerPresenter;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = PartnerPresenter.class, module = PartnerModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface PartnerEventBus extends EventBus {
	
	@Event(forwardToParent = true)
	public void setLeftPanel(Widget widget);
	
	@Event(forwardToParent = true) 
	public void loadInstitutionProfile(InstitutionBean institution);
	
	@Event(handlers = PartnerPresenter.class)
	public void loadPartner();
}
