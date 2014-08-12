package com.infoklinik.rsvp.client.main.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.main.presenter.interfaces.IMenuView;
import com.infoklinik.rsvp.shared.Constant;

public class MenuView extends BaseView implements IMenuView {
	
	@UiField
	Label clinicLb;
	
	@UiField
	Label hospitalLb;
	
	@UiField
	Label doctorLb;
	
	@UiField
	Label serviceLb;
	
	@UiField
	Label laboratLb;
	
	@UiField
	Label pharmacyLb;
	
	Label currentMenu;
	
	interface ModuleUiBinder extends UiBinder<Widget, MenuView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	public void createView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	public void setClinicSelected() {
		
		setMenu(clinicLb);
	}
	
	public void setHospitalSelected() {
		
		setMenu(hospitalLb);
	}
	
	public void setDoctorSelected() {
		
		setMenu(doctorLb);
	}
	
	public void setServiceSelected() {
		
		setMenu(serviceLb);
	}
	
	public void setLabSelected() {
		
		setMenu(laboratLb);
	}
	
	public void setPharmacySelected() {
		
		setMenu(pharmacyLb);
	}
	
	public void setClinicLbHandler(ClickHandler handler) {
		
		clinicLb.addClickHandler(handler);
	}
	
	public void setHospitalLbHandler(ClickHandler handler) {
		
		hospitalLb.addClickHandler(handler);
	}
	
	public void setDoctorLbHandler(ClickHandler handler) {
		
		doctorLb.addClickHandler(handler);
	}
	
	public void setServiceLbHandler(ClickHandler handler) {
		
		serviceLb.addClickHandler(handler);
	}
	
	public void setLabLbHandler(ClickHandler handler) {
		
		laboratLb.addClickHandler(handler);
	}
	
	public void setPharmacyLbHandler(ClickHandler handler) {
		
		pharmacyLb.addClickHandler(handler);
	}
	
	private void setMenu(Label menuLb) {
		
		if (currentMenu != null) {
			
			currentMenu.removeStyleName(Constant.STYLE_MENU_SELECTED);
			currentMenu.setStyleName(Constant.STYLE_MENU);
		}
		
		menuLb.removeStyleName(Constant.STYLE_MENU);
		menuLb.setStyleName(Constant.STYLE_MENU_SELECTED);
		
		currentMenu = menuLb;
	}
}
