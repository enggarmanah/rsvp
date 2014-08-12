package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminInstitutionDoctorView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	DoctorBean getDoctor();
	
	void setDoctor(DoctorBean doctor);
	
	void setDoctorSelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
