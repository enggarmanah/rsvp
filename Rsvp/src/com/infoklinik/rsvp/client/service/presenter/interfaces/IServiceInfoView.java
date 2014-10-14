package com.infoklinik.rsvp.client.service.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.view.LazyView;

public interface IServiceInfoView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setInstitution(InstitutionBean institution);
	
	void setService(ServiceBean service);
	
	void setWebsiteClickHandler(ClickHandler handler);
	
	void setFacebookClickHandler(ClickHandler handler);
	
	void setTwitterClickHandler(ClickHandler handler);
	
	void setBookBtnClickHandler(ClickHandler handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
}
