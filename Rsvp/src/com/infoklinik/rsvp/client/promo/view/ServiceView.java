package com.infoklinik.rsvp.client.promo.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.ServiceBean;

public class ServiceView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, ServiceView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Label nameLb;
	
	@UiField
	Label descriptionLb;
	
	@UiField
	Label priceLb;
	
	@UiField
	Label institutionLb;
	
	public ServiceView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setService(GenericBean<ServiceBean> genService) {
		
		ServiceBean service = genService.getBean();
		
		nameLb.setText(service.getServiceType().getName() + " - " + service.getName());
		nameLb.addClickHandler(genService.getHandlerMgr().getShowHandler());
		
		descriptionLb.setText(service.getDescription());
		priceLb.setText("Harga Promo : " + service.getPrice() + ". Harga Normal : " + service.getPromoPrice());
		institutionLb.setText(service.getInstitution().getName());
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
