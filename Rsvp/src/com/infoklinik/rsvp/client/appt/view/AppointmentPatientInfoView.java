package com.infoklinik.rsvp.client.appt.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IAppointmentPatientInfoView;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class AppointmentPatientInfoView extends BaseView implements IAppointmentPatientInfoView {
	
	private DialogBox dialogBox;
	
	@UiField
	TextBox verificationCodeTb;
	
	@UiField
	TextBox patientNameTb;
	
	@UiField
	RadioButton patientSexMaleRb;
	
	@UiField
	RadioButton patientSexFemaleRb;
	
	@UiField
	TextBox patientBirthYearTb;
	
	@UiField
	TextBox patientEmailTb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	AppointmentBean appointment;
	
	interface ModuleUiBinder extends UiBinder<Widget, AppointmentPatientInfoView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	DoctorBean doctor;
	InstitutionBean institution;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
		dialogBox.setText("Verifikasi No. Handphone");
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return dialogBox;
	}
	
	public AppointmentBean getAppointment() {
		
		appointment.setVerificationCode(verificationCodeTb.getValue());
		appointment.setPatientName(patientNameTb.getValue());
		appointment.setPatientSex(patientSexMaleRb.getValue() ? "L" : "P");
		appointment.setPatientBirthYear(patientBirthYearTb.getValue());
		appointment.setPatientEmail(patientEmailTb.getValue());
		
		return appointment;
	}

	public void setAppointment(AppointmentBean appointment) {
		this.appointment = appointment;
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
	
	public void show() {
		
		goToTop();
		
		fadeOut();
		
		patientSexMaleRb.setValue(true);
		
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft(), 70);
		dialogBox.show();
		
		fadeIn();
	}
	
	public void hide() {
		
		fadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				dialogBox.hide();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}
}