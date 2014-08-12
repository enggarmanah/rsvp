package com.infoklinik.rsvp.client.partner.presenter.interfaces;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.mvp4g.client.view.LazyView;

public interface IPartnerView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void addPartner(GenericBean<InstitutionBean> genInstitution);
	
	void clear();
	
	void fadeIn();
	
	void fadeOut();
}
