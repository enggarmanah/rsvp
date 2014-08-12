package com.infoklinik.rsvp.client.clinic.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class ClinicProfileDoctorView extends BaseView {
	
	@UiField
	FlowPanel doctorsPanel;
	
	interface ModuleUiBinder extends UiBinder<Widget, ClinicProfileDoctorView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institutionBean;
	
	private static String STYLE_DOCTOR = "profile-inst-doctor";
	private static String STYLE_DOCTOR_NAME = "name";
	private static String STYLE_DOCTOR_SPECIALITY = "speciality";
	private static String STYLE_INST_DOCTOR_WRAPPER = "profile-inst-doctor-wrapper-border";
	
	public ClinicProfileDoctorView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setDoctors(List<GenericBean<DoctorBean>> doctors) {
		
		doctorsPanel.clear();
		
		for (GenericBean<DoctorBean> genericDoctor : doctors) {
			
			DoctorBean doctor = genericDoctor.getBean();
			HandlerManager handlerMgr = genericDoctor.getHandlerMgr();
			
			Label doctorNameLb = new Label(doctor.getName());
			doctorNameLb.setStyleName(STYLE_DOCTOR_NAME);
			
			Label doctorSpecialityLb = new Label(doctor.getSpeciality().getDescription());
			doctorSpecialityLb.setStyleName(STYLE_DOCTOR_SPECIALITY);
			
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			horizontalPanel.setStyleName(STYLE_DOCTOR);
			horizontalPanel.add(doctorNameLb);
			horizontalPanel.add(doctorSpecialityLb);
			
			FocusPanel doctorPanel = new FocusPanel();
			doctorPanel.add(horizontalPanel);
			doctorPanel.addClickHandler(handlerMgr.getShowHandler());
			
			doctorsPanel.add(doctorPanel);
		}
		
		if (doctorsPanel.getOffsetHeight() > 340) {
			doctorsPanel.addStyleName(STYLE_INST_DOCTOR_WRAPPER);
		} else {
			doctorsPanel.removeStyleName(STYLE_INST_DOCTOR_WRAPPER);
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
