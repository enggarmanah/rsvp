package com.infoklinik.rsvp.client.promo.presenter.interfaces;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.view.LazyView;

public interface IPromoView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void addPromo(GenericBean<ServiceBean> serviceBean);
	
	void clear();
	
	void fadeIn();
	
	void fadeOut();
}
