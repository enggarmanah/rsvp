package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.SuggestionOracle;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.SuggestParameter;

public class InstitutionMainView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionMainView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	TextBox nameTb;
	
	@UiField
	ListBox categoryLb;
	
	@UiField
	ListBox typeLb;
	
	@UiField
	TextBox telephoneTb;
	
	@UiField
	TextBox emailTb;
	
	@UiField(provided = true)
	SuggestBox citySb = new SuggestBox(new SuggestionOracle(new SuggestParameter(SuggestionOracle.SEARCH_CITY)));
	
	SuggestParameter regionSugParameter = new SuggestParameter();
	
	@UiField(provided = true)
	SuggestBox regionSb = new SuggestBox(new SuggestionOracle(regionSugParameter));
	
	SuggestParameter streetSugParameter = new SuggestParameter();
	
	@UiField(provided = true)
	SuggestBox streetSb = new SuggestBox(new SuggestionOracle(streetSugParameter));
	
	@UiField
	TextArea addressTa;
	
	@UiField
	TextBox locationTb;
	
	@UiField
	ListBox partnerTypeLb;
	
	@UiField
	TextBox websiteTb;
	
	@UiField
	TextBox facebookTb;
	
	@UiField
	TextBox twitterTb;
	
	InstitutionBean institution;
	
	public InstitutionMainView() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		regionSugParameter.setType(SuggestionOracle.SEARCH_REGION);
		streetSugParameter.setType(SuggestionOracle.SEARCH_STREET);
		
		locationTb.setReadOnly(true);
	}
	
	public InstitutionBean getInstitution() {
		
		institution.setName(nameTb.getValue());
		institution.setCategory(categoryLb.getValue(categoryLb.getSelectedIndex()));
		institution.setType(typeLb.getValue(typeLb.getSelectedIndex()));
		institution.setTypeDescription(typeLb.getItemText(typeLb.getSelectedIndex()));
		institution.setTelephone(telephoneTb.getValue());
		institution.setEmail(emailTb.getValue());
		
		if (institution.getCity() != null && 
			!institution.getCity().getName().equals(citySb.getValue())) {
			institution.setCity(null);
		} 
		
		institution.setAddress(addressTa.getValue());
		institution.setLocation(locationTb.getValue());
		institution.setPartnerType(partnerTypeLb.getValue(partnerTypeLb.getSelectedIndex()));
		institution.setWebsite(websiteTb.getValue());
		institution.setFacebook(facebookTb.getValue());
		institution.setTwitter(twitterTb.getValue());
		
		return institution;
	}
	
	public void setInstitution(InstitutionBean institution) {
		
		if (typeLb.getItemCount() == 0) {
			typeLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		}
		
		this.institution = institution;
		
		String cityId = Constant.ZERO_STRING;
		
		if (institution.getCity() != null) {
			cityId = institution.getCity().getId().toString();
		}
		
		regionSugParameter.setCityId(cityId);
		streetSugParameter.setCityId(cityId);
		
		nameTb.setValue(institution.getName());
		ClientUtil.setSelectedIndex(categoryLb, institution.getCategory());
		telephoneTb.setValue(institution.getTelephone());
		emailTb.setValue(institution.getEmail());
		
		citySb.setValue(Constant.EMPTY_STRING);
		if (institution.getCity() != null) {
			citySb.setValue(institution.getCity().getName());
		}
		
		regionSb.setValue(Constant.EMPTY_STRING);
		if (institution.getRegion() != null) {
			regionSb.setValue(institution.getRegion().getName());
		}
		
		streetSb.setValue(Constant.EMPTY_STRING);
		if (institution.getStreet() != null) {
			streetSb.setValue(institution.getStreet().getName());
		}
		
		addressTa.setValue(institution.getAddress());
		locationTb.setValue(institution.getLocation());
		ClientUtil.setSelectedIndex(partnerTypeLb, institution.getPartnerType());
		ClientUtil.setSelectedIndex(typeLb, institution.getType());
	}
	
	public void setCategories(List<MasterCodeBean> masterCodeBeans) {
		
		categoryLb.clear();
		
		categoryLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (MasterCodeBean masterCodeBean : masterCodeBeans) {
			categoryLb.addItem(masterCodeBean.getValue(), String.valueOf(masterCodeBean.getCode()));
		}
	}
	
	public void setTypes(List<MasterCodeBean> masterCodeBeans) {
		
		typeLb.clear();
		
		typeLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		if (masterCodeBeans.size() == 0) {
			typeLb.setEnabled(false);
		} else {
			typeLb.setEnabled(true);
		}
				
		for (MasterCodeBean masterCodeBean : masterCodeBeans) {
			typeLb.addItem(masterCodeBean.getValue(), String.valueOf(masterCodeBean.getCode()));
		}
		
		if (institution != null) {
			ClientUtil.setSelectedIndex(typeLb, institution.getType());
		}
	}
	
	public void setPartnerTypes(List<MasterCodeBean> masterCodeBeans) {
		
		partnerTypeLb.clear();
		
		partnerTypeLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		if (masterCodeBeans.size() == 0) {
			partnerTypeLb.setEnabled(false);
		} else {
			partnerTypeLb.setEnabled(true);
		}
				
		for (MasterCodeBean masterCodeBean : masterCodeBeans) {
			partnerTypeLb.addItem(masterCodeBean.getValue(), String.valueOf(masterCodeBean.getCode()));
		}
	}
	
	public void setCategoryChangeHandler(ChangeHandler handler) {
		
		categoryLb.addChangeHandler(handler);
	}
	
	public void setCitySelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler) {
		
		citySb.addSelectionHandler(handler);
	}
	
	public void setRegionSelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler) {
		
		regionSb.addSelectionHandler(handler);
	}
	
	public void setStreetSelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler) {
		
		streetSb.addSelectionHandler(handler);
	}
	
	public void setLocationClickHandler(ClickHandler handler) {
		
		locationTb.addClickHandler(handler);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
