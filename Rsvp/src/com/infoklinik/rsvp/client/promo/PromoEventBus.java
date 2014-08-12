package com.infoklinik.rsvp.client.promo;

import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.promo.presenter.PromoPresenter;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = PromoPresenter.class, module = PromoModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface PromoEventBus extends EventBus {
	
	@Event(forwardToParent = true)
	public void setRightPanel(Widget widget);
	
	@Event(forwardToParent = true)
	public void loadServiceInfo(ServiceBean service);
	
	@Event(handlers = PromoPresenter.class)
	public void loadPromotion();
}
