package com.infoklinik.rsvp.client.admin.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class InstitutionBranchItemView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionBranchItemView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Label nameLb;
	
	@UiField
	Label addressLb;
	
	@UiField
	Image deleteImg;
	
	public InstitutionBranchItemView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setInstitution(InstitutionBean instBean) {
		
		nameLb.setText(instBean.getName());
		addressLb.setText(instBean.getAddress());
	}
	
	public void setDeleteClickHandler(ClickHandler handler) {
		
		deleteImg.addClickHandler(handler);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
