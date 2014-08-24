package com.infoklinik.rsvp.client.inst.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.InstitutionSearchBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.mvp4g.client.view.LazyView;

public interface ILabSearchView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	public void setCities(List<CityBean> citys);
	
	public void setCity(CityBean city);
		
	public void setInstitutionTypes(List<MasterCodeBean> masterCodes);
	
	public void setSearchBtnHandler(ClickHandler handler);
	
	public void setSearchOptionRbHandler(ClickHandler handler);
	
	public void setSearchSbHandler(ClickHandler handler);
	
	public void setCityLbHandler(ChangeHandler handler);
	
	public InstitutionSearchBean getInstitutionSearch();
	
	public String getSearchOptionValue();
	
	public void setSuggestType(String type);
	
	public void setSuggestCityId(String cityId);
	
	public HasText getSearchSb();
	
	public void setLocation(LocationBean location);
	
	public void fadeIn();
	
	public void fadeOut();
}
