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
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IReservationPatientInfoView;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class ReservationPatientInfoView extends BaseView implements IReservationPatientInfoView {
	
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
	
	ReservationBean appointment;
	
	interface ModuleUiBinder extends UiBinder<Widget, ReservationPatientInfoView> {}
	
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
	
	public ReservationBean getAppointment() {
		
		appointment.setVerificationCode(ClientUtil.trim(verificationCodeTb.getValue()));
		appointment.setPatientName(ClientUtil.trim(patientNameTb.getValue()));
		appointment.setPatientSex(patientSexMaleRb.getValue() ? Constant.SEX_MALE : Constant.SEX_FEMALE);
		appointment.setPatientBirthYear(ClientUtil.trim(patientBirthYearTb.getValue()));
		appointment.setPatientEmail(ClientUtil.trim(patientEmailTb.getValue()));
		
		return appointment;
	}

	public void setAppointment(ReservationBean appointment) {
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