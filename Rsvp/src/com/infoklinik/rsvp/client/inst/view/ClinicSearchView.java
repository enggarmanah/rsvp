package com.infoklinik.rsvp.client.inst.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.SuggestionOracle;
import com.infoklinik.rsvp.client.inst.presenter.interfaces.IClinicSearchView;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InstitutionSearchBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.SpecialityBean;
import com.infoklinik.rsvp.shared.SuggestParameter;

public class ClinicSearchView extends BaseView implements IClinicSearchView {
	
	@UiField
	HTMLPanel contentPanel;
	
	@UiField
	ListBox cityLb;
	
	@UiField
	ListBox insuranceLb;
	
	@UiField
	ListBox specialityLb;

	@UiField
	ListBox institutionTypeLb;
	
	@UiField
	ListBox open24HrsLb;
	
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
	
	private LocationBean location;
	
	SuggestParameter suggestParameter = new SuggestParameter();
	
	@UiField(provided = true)
	SuggestBox searchSb = new SuggestBox(new SuggestionOracle(suggestParameter));
	
	interface ModuleUiBinder extends UiBinder<Widget, ClinicSearchView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	public void createView() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		suggestParameter.setCityId(Constant.ZERO_STRING);
		
		open24HrsLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		open24HrsLb.addItem(Constant.YES_DESC, Constant.YES);
		open24HrsLb.addItem(Constant.NO_DESC, Constant.NO);
		
		searchNameRb.setValue(true);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return contentPanel;
	}
	
	public void setSearchOptionRbHandler(ClickHandler handler) {
		
		searchNameRb.addClickHandler(handler);
		searchStreetRb.addClickHandler(handler);
		searchRegionRb.addClickHandler(handler);
		searchDistanceRb.addClickHandler(handler);
	}
	
	public void setSearchSbHandler(ClickHandler handler) {
		
		searchSb.getValueBox().addClickHandler(handler);
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
	
	public void setCity(CityBean city) {
		
		ClientUtil.setSelectedIndex(cityLb, city.getName());
		suggestParameter.setCityId(city.getId().toString());
	}
	
	public void setInsurances(List<InsuranceBean> insuranceBeans) {
		
		insuranceLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (InsuranceBean insuranceBean : insuranceBeans) {
			insuranceLb.addItem(insuranceBean.getName(), String.valueOf(insuranceBean.getId()));
		}
	}
	
	public void setSpecialities(List<SpecialityBean> specialityBeans) {
		
		specialityLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (SpecialityBean specialityBean : specialityBeans) {
			specialityLb.addItem(specialityBean.getDescription(), String.valueOf(specialityBean.getId()));
		}
	}
	
	public void setInstitutionTypes(List<MasterCodeBean> masterCodeBeans) {
		
		institutionTypeLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (MasterCodeBean masterCodeBean : masterCodeBeans) {
			institutionTypeLb.addItem(masterCodeBean.getValue(), String.valueOf(masterCodeBean.getCode()));
		}
	}
	
	public InstitutionSearchBean getInstitutionSearch() {
		
		InstitutionSearchBean instSearchBean = new InstitutionSearchBean();
		
		instSearchBean.setCategory(InstitutionBean.CATEGORY_CLINIC);
		
		if (searchNameRb.getValue()) {
			instSearchBean.setName(searchSb.getText());
			
		} else if (searchStreetRb.getValue()) {
			instSearchBean.setStreetName(searchSb.getText());
			
		} else if (searchRegionRb.getValue()) {
			instSearchBean.setRegionName(searchSb.getText());
			
		} else if (searchDistanceRb.getValue()) {
			instSearchBean.setLocation(location);
		}
		
		instSearchBean.setCityId(Long.valueOf(cityLb.getValue(cityLb.getSelectedIndex())));
		
		if (institutionTypeLb.getSelectedIndex() != 0) {
			instSearchBean.setType(institutionTypeLb.getValue(institutionTypeLb.getSelectedIndex()));
		}
		
		if (insuranceLb.getSelectedIndex() != 0) {
			instSearchBean.setInsuranceId(Long.valueOf(insuranceLb.getValue(insuranceLb.getSelectedIndex())));
		}
		
		if (specialityLb.getSelectedIndex() != 0) {
			Window.alert("SpecialityLb selectedIndex : " + specialityLb.getSelectedIndex());
			instSearchBean.setSpecialityId(Long.valueOf(specialityLb.getValue(specialityLb.getSelectedIndex())));
		}
		
		if (open24HrsLb.getSelectedIndex() != 0) {
			if (open24HrsLb.getSelectedIndex() == 1) {
				instSearchBean.setOpen24Hours(true);
			} else {
				instSearchBean.setOpen24Hours(false);
			}
		}
		
		return instSearchBean;
	}
	
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
	
	public void setLocation(LocationBean location) {
		
		this.location = location;
		searchSb.setValue("Posisi: " + location.getLat() + "," + location.getLng() + " Jarak: " + location.getDistance() + " M");
	}
}
