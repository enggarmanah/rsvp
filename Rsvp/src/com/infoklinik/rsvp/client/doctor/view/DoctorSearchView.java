package com.infoklinik.rsvp.client.doctor.view;

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
import com.infoklinik.rsvp.client.SuggestionOracle;
import com.infoklinik.rsvp.client.doctor.presenter.interfaces.IDoctorSearchView;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorSearchBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.SpecialityBean;
import com.infoklinik.rsvp.shared.SuggestParameter;

public class DoctorSearchView extends BaseView implements IDoctorSearchView {
	
	@UiField
	ListBox cityLb;
	
	@UiField
	ListBox specialityLb;
	
	@UiField
	ListBox dayLb;
	
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
	
	interface ModuleUiBinder extends UiBinder<Widget, DoctorSearchView> {}
	
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
	
	public void setSearchBtnHandler(ClickHandler handler) {
		searchBtn.addClickHandler(handler);
	}
	
	public void setCities(List<CityBean> cityBeans) {
		
		for (CityBean cityBean : cityBeans) {
			cityLb.addItem(cityBean.getName(), String.valueOf(cityBean.getId()));
		}
	}
	
	public void setSpecialities(List<SpecialityBean> specialityBeans) {
		
		specialityLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (SpecialityBean specialityBean : specialityBeans) {
			specialityLb.addItem(specialityBean.getDescription(), String.valueOf(specialityBean.getId()));
		}
	}
	
	public void setDays(List<MasterCodeBean> masterCodeBeans) {
		
		dayLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (MasterCodeBean masterCodeBean : masterCodeBeans) {
			dayLb.addItem(masterCodeBean.getValue(), String.valueOf(masterCodeBean.getCode()));
		}
	}
	
	public DoctorSearchBean getDoctorSearch() {
		
		DoctorSearchBean docSearchBean = new DoctorSearchBean();
		
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
		
		docSearchBean.setCityId(Long.valueOf(cityLb.getValue(cityLb.getSelectedIndex())));
		
		if (dayLb.getSelectedIndex() != 0) {
			docSearchBean.setDay(Integer.valueOf(dayLb.getValue(dayLb.getSelectedIndex())));
		}
		
		if (specialityLb.getSelectedIndex() != 0) {
			docSearchBean.setSpecialityId(Long.valueOf(specialityLb.getValue(specialityLb.getSelectedIndex())));
		}
		
		return docSearchBean;
	}
	
	public void setSearchOptionRbHandler(ClickHandler handler) {
		
		searchNameRb.addClickHandler(handler);
		searchStreetRb.addClickHandler(handler);
		searchRegionRb.addClickHandler(handler);
		searchDistanceRb.addClickHandler(handler);
	}
	
	@Override
	public void setSearchSbHandler(ClickHandler handler) {
		
		searchSb.getValueBox().addClickHandler(handler);
	}
	
	public void setCityLbHandler(ChangeHandler handler) {
		
		cityLb.addChangeHandler(handler);
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
