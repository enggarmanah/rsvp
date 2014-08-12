package com.infoklinik.rsvp.client.doctor.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class DoctorProfileInfoView extends BaseView {
	
	@UiField
	HTML profileLb;
	
	@UiField
	SimplePanel profilePanel;
	
	interface ModuleUiBinder extends UiBinder<Widget, DoctorProfileInfoView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	private static String STYLE_DOCTOR_WRAPPER_BORDER = "profile-doctor-wrapper-border";
	
	InstitutionBean institutionBean;
	
	public DoctorProfileInfoView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setInfo(String info) {
		
		profileLb.setHTML(new SafeHtmlBuilder().appendEscapedLines(info).toSafeHtml());
	}
	
	@Override
	public void loadStyle() {
		
		if (profilePanel.getOffsetHeight() >= 320) {
			profilePanel.addStyleName(STYLE_DOCTOR_WRAPPER_BORDER);
		} else {
			profilePanel.removeStyleName(STYLE_DOCTOR_WRAPPER_BORDER);
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}