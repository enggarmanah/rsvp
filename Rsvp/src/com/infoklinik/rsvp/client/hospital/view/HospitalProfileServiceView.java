package com.infoklinik.rsvp.client.hospital.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ServiceBean;

public class HospitalProfileServiceView extends BaseView {
	
	@UiField
	FlowPanel servicePanel;
	
	@UiField
	FlowPanel promotionPanel;
	
	@UiField
	FlowPanel insurancePanel;
	
	interface ModuleUiBinder extends UiBinder<Widget, HospitalProfileServiceView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institutionBean;
	
	private static String SERVICE_STYLE = "content";
	private static String SERVICE_DIVIDER = "\u2022 ";
	
	public HospitalProfileServiceView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setServices(List<ServiceBean> services) {
		
		servicePanel.clear();
		
		int i = 1;
		
		for (ServiceBean service : services) {
			
			String content = service.getName();
			content = i > 1 ? SERVICE_DIVIDER + content : content;
			
			Label serviceLb = new Label(content);
			serviceLb.setStyleName(SERVICE_STYLE);
			servicePanel.add(serviceLb);
			
			i++;
		}
	}
	
	public void setPromotions(List<ServiceBean> services) {
		
		promotionPanel.clear();
		
		for (ServiceBean service : services) {
			
			String content = service.getName() + " - Promo: " + service.getPromoPrice() + ", Normal: " + service.getPrice();
			
			Label serviceLb = new Label(content);
			serviceLb.setStyleName(SERVICE_STYLE);
			promotionPanel.add(serviceLb);
		}
	}
	
	public void setInsurances(List<InsuranceBean> insurances) {
		
		insurancePanel.clear();
		
		int i = 1;
		
		for (InsuranceBean insurance : insurances) {
			
			String content = insurance.getName();
			content = i > 1 ? SERVICE_DIVIDER + content : content;
			
			Label insuranceLb = new Label(content);
			insuranceLb.setStyleName(SERVICE_STYLE);
			insurancePanel.add(insuranceLb);
			
			i++;
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
