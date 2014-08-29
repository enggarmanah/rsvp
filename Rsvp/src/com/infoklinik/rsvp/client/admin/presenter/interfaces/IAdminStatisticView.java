package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IAdminStatisticView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void setDataCount(Map<String, Long> map);
}
