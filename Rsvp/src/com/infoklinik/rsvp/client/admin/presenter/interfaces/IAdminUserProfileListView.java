package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.UserProfileBean;
import com.infoklinik.rsvp.shared.UserProfileSearchBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminUserProfileListView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setList(List<GenericBean<UserProfileBean>> list);
	
	void refresh();
	
	UserProfileSearchBean getUserProfileSearch();
	
	void remove(GenericBean<UserProfileBean> genericBean);
	
	void setSearchBtnClickHandler(ClickHandler handler);	

	void setAddBtnClickHandler(ClickHandler handler);	
	
	void setOkBtnClickHandler(ClickHandler handler);
}
