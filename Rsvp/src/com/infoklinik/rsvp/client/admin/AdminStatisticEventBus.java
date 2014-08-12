package com.infoklinik.rsvp.client.admin;

import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.admin.presenter.AdminStatisticPresenter;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = AdminStatisticPresenter.class, module = AdminStatisticModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface AdminStatisticEventBus extends EventBus {
	
	@Event(forwardToParent = true)
	public void setLeftPanel(Widget widget);
	
	@Event(handlers = AdminStatisticPresenter.class)
	public void loadStatistic();
}
