package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminServiceTypeView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setCategories(List<String> list);
	
	ServiceTypeBean getServiceTypeBean();
	
	void setServiceTypeBean(ServiceTypeBean serviceTypeBean);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
