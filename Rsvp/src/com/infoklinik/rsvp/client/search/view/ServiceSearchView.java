package com.infoklinik.rsvp.client.search.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.SuggestionOracle;
import com.infoklinik.rsvp.client.search.presenter.interfaces.IServiceSearchView;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ServiceSearchBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.infoklinik.rsvp.shared.SuggestParameter;

public class ServiceSearchView extends BaseView implements IServiceSearchView {
	
	@UiField
	ListBox cityLb;
	
	@UiField
	ListBox serviceTypeLb;
	
	@UiField
	RadioButton searchNameRb;
	
	@UiField
	RadioButton searchStreetRb;
	
	@UiField
	RadioButton searchRegionRb;
	
	@UiField
	RadioButton searchDistanceRb;
	
	@UiField
	Button searchBtn;
	
	SuggestParameter suggestParameter = new SuggestParameter();
	
	@UiField(provided = true)
	SuggestBox searchSb = new SuggestBox(new SuggestionOracle(suggestParameter));
	
	interface ModuleUiBinder extends UiBinder<Widget, ServiceSearchView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	private LocationBean location;
	
	public void createView() {
		
		initWidget(uiBinder.createAndBindUi(this));
			
		suggestParameter.setCityId(Constant.ZERO_STRING);
		
		searchNameRb.setValue(true);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	public void setSearchOptionRbHandler(ClickHandler handler) {
		
		searchNameRb.addClickHandler(handler);
		searchStreetRb.addClickHandler(handler);
		searchRegionRb.addClickHandler(handler);
		searchDistanceRb.addClickHandler(handler);
	}
	
	public void setCityLbHandler(ChangeHandler handler) {
		
		cityLb.addChangeHandler(handler);
	}
	
	public void setSearchBtnHandler(ClickHandler handler) {
		searchBtn.addClickHandler(handler);
	}
	
	public void setCities(List<CityBean> cityBeans) {
		
		for (CityBean cityBean : cityBeans) {
			cityLb.addItem(cityBean.getName(), String.valueOf(cityBean.getId()));
		}
	}
	
	public void setServiceTypes(List<ServiceTypeBean> serviceTypes) {
		
		serviceTypeLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (ServiceTypeBean serviceType : serviceTypes) {
			serviceTypeLb.addItem(serviceType.getName(), String.valueOf(serviceType.getId()));
		}
	}
	
	public ServiceSearchBean getServiceSearch() {
		
		ServiceSearchBean docSearchBean = new ServiceSearchBean();
		
		if (searchNameRb.getValue()) {
			docSearchBean.setName(searchSb.getText());
		}
		
		if (searchStreetRb.getValue()) {
			docSearchBean.setStreetName(searchSb.getText());
		}
		
		if (searchRegionRb.getValue()) {
			docSearchBean.setRegionName(searchSb.getText());
		}
		
		if (searchDistanceRb.getValue()) {
			docSearchBean.setLocation(location);
		}
		
		docSearchBean.setCityId(ClientUtil.strToLong(cityLb.getValue(cityLb.getSelectedIndex())));
		
		if (serviceTypeLb.getSelectedIndex() != 0) {
			docSearchBean.setServiceTypeId(ClientUtil.strToLong(serviceTypeLb.getValue(serviceTypeLb.getSelectedIndex())));
		}
		
		return docSearchBean;
	}

	@Override
	public void setSearchSbHandler(ClickHandler handler) {
		
		searchSb.getValueBox().addClickHandler(handler);
	}

	@Override
	public String getSearchOptionValue() {
		
		String searchOptionValue = Constant.SEARCH_BY_NAME;
		
		if (searchStreetRb.getValue()) {
			searchOptionValue = Constant.SEARCH_BY_STREET;
			
		} else if (searchRegionRb.getValue()) {
			searchOptionValue = Constant.SEARCH_BY_REGION;
			
		} else if (searchDistanceRb.getValue()) {
			searchOptionValue = Constant.SEARCH_BY_DISTANCE;
		}
		
		return searchOptionValue;
	}
	
	public void setSuggestType(String type) {
		
		suggestParameter.setType(type);
	}
	
	public void setSuggestCityId(String cityId) {
		
		suggestParameter.setCityId(cityId);
	}
	
	public HasText getSearchSb() {
		
		return searchSb;
	}

	@Override
	public void setLocation(LocationBean location) {
		
		this.location = location;
		searchSb.setValue("Posisi: " + location.getLat() + "," + location.getLng() + " Jarak: " + location.getDistance() + " M");
	}
}
