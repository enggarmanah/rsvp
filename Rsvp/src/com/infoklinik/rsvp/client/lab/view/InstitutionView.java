package com.infoklinik.rsvp.client.lab.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class InstitutionView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Label nameLb;
	
	@UiField
	Label addressLb;
	
	@UiField
	Label telephoneLb;
	
	@UiField
	Label emailLb;
	
	public InstitutionView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setInstitutionBean(InstitutionBean instBean) {
		
		nameLb.setText(instBean.getName());
		addressLb.setText(instBean.getAddress());
		telephoneLb.setText(instBean.getTelephone());
		emailLb.setText(instBean.getEmail());
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
