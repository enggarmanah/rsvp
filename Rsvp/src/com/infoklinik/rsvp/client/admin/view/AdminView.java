package com.infoklinik.rsvp.client.admin.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminView;

public class AdminView extends BaseView implements IAdminView {
	
	@UiField
	Label institutionLb;
	
	@UiField
	Label doctorLb;
	
	@UiField
	Label serviceReferenceLb;
	
	@UiField
	Label logoutLb;
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminView> {}
	
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
	
	public void setInstitutionLinkClickHandler(ClickHandler handler) {
		
		institutionLb.addClickHandler(handler);
	}
	
	public void setDoctorLinkClickHandler(ClickHandler handler) {
		
		doctorLb.addClickHandler(handler);
	}
	
	public void setServiceReferenceLinkClickHandler(ClickHandler handler) {
		
		serviceReferenceLb.addClickHandler(handler);
	}
	
	public void setLogoutLinkClickHandler(ClickHandler handler) {
		
		logoutLb.addClickHandler(handler);
	}
}
