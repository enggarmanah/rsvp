package com.infoklinik.rsvp.client.service.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.ServiceSearchBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.mvp4g.client.view.LazyView;

public interface IServiceSearchView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	public void setCities(List<CityBean> citys);
	
	public void setServiceTypes(List<ServiceTypeBean> specialities);
	
	public void setSearchOptionRbHandler(ClickHandler handler);
	
	public void setSearchSbHandler(ClickHandler handler);
	
	public void setCityLbHandler(ChangeHandler handler);
	
	public void setSearchBtnHandler(ClickHandler handler);
	
	public ServiceSearchBean getServiceSearch();
	
	public String getSearchOptionValue();
	
	public void setLocation(LocationBean location);
	
	public void setSuggestType(String type);
	
	public void setSuggestCityId(String cityId);
	
	public HasText getSearchSb();
	
	public void fadeIn();
	
	public void fadeOut();
}
