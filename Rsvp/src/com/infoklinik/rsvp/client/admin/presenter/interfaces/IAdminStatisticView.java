package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IAdminStatisticView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void setDataStatistic(Map<String, Long> map);
	
	void setSearchTypeStatistic(Map<String, Long> map);
	
	void setSearchMethodStatistic(Map<String, Long> map);
}
