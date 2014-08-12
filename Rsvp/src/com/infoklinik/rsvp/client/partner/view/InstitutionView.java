package com.infoklinik.rsvp.client.partner.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class InstitutionView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	SimplePanel logoPanel;
	
	@UiField
	Label nameLb;
	
	@UiField
	Label profileLb;
	
	@UiField
	Label addressLb;
	
	@UiField
	Label telephoneLb;
	
	@UiField
	Label emailLb;
	
	public InstitutionView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setInstitutionBean(GenericBean<InstitutionBean> genInstitution) {
		
		InstitutionBean institution = genInstitution.getBean();
		nameLb.addClickHandler(genInstitution.getHandlerMgr().getShowHandler());
		
		Image logoImg = new Image("/image?id=" + institution.getImageId());
		logoImg.setStyleName("image");
		
		logoPanel.add(logoImg);
		
		nameLb.setText(institution.getName());
		profileLb.setText(institution.getProfile());
		addressLb.setText(institution.getAddress());
		telephoneLb.setText(institution.getTelephone());
		emailLb.setText(institution.getEmail());
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
