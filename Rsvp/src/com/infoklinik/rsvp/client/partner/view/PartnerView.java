package com.infoklinik.rsvp.client.partner.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.partner.presenter.interfaces.IPartnerView;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class PartnerView extends BaseView implements IPartnerView {
	
	interface ModuleUiBinder extends UiBinder<Widget, PartnerView> {}
	
	@UiField
	VerticalPanel partnerPanel;
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	public void createView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return partnerPanel;
	}
	
	public void addPartner(GenericBean<InstitutionBean> genInstitution) {
		
		InstitutionView instView = new InstitutionView();
		instView.setInstitutionBean(genInstitution);
		
		partnerPanel.add(instView);
	}
	
	public void clear() {
		
		partnerPanel.clear();
	}
}
