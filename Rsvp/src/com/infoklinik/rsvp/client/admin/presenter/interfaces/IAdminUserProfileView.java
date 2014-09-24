package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.UserProfileBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminUserProfileView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	UserProfileBean getUserProfile();
	
	void setUserProfile(UserProfileBean userProfileBean);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
