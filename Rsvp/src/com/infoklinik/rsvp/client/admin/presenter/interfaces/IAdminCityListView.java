package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminCityListView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setList(List<GenericBean<CityBean>> list);
	
	void refresh();
	
	CitySearchBean getCitySearch();
	
	void remove(GenericBean<CityBean> genericBean);
	
	void setSearchBtnClickHandler(ClickHandler handler);	

	void setAddBtnClickHandler(ClickHandler handler);	
	
	void setOkBtnClickHandler(ClickHandler handler);
}
