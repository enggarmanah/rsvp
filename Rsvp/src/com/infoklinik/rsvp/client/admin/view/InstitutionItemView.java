package com.infoklinik.rsvp.client.admin.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ServiceBean;

public class InstitutionItemView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionItemView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Label nameLb;
	
	@UiField
	Image deleteImg;
	
	public InstitutionItemView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setService(GenericBean<ServiceBean> genService) {
		
		ServiceBean service = genService.getBean();
		HandlerManager handlerMgr = genService.getHandlerMgr();
		
		nameLb.setText(service.getName());
		nameLb.addClickHandler(handlerMgr.getUpdateHandler());
		deleteImg.addClickHandler(handlerMgr.getDeleteHandler());
	}
	
	public void setInsurance(GenericBean<InsuranceBean> genInsurance) {
		
		InsuranceBean insurance = genInsurance.getBean();
		HandlerManager handlerMgr = genInsurance.getHandlerMgr();
		
		nameLb.setText(insurance.getName());
		nameLb.addClickHandler(handlerMgr.getUpdateHandler());
		deleteImg.addClickHandler(handlerMgr.getDeleteHandler());
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
