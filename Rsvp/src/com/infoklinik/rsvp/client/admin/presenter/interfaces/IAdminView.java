package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IAdminView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	public void setInstitutionLinkClickHandler(ClickHandler handler);
	
	void setServiceReferenceLinkClickHandler(ClickHandler handler);
	
	void setLogoutLinkClickHandler(ClickHandler handler);
}
