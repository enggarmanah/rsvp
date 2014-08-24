package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminInstitutionLocationView  extends LazyView, IsWidget {
	
	InstitutionBean getInstitution();

	void setInstitution(InstitutionBean institution);
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setOkBtnClickHandler(ClickHandler handler);
}
