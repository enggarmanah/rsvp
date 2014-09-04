package com.infoklinik.rsvp.client.appt.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.appt.AppointmentEventBus;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IAppointmentPatientMobileView;
import com.infoklinik.rsvp.client.appt.view.AppointmentPatientMobileView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.AppointmentServiceAsync;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AppointmentPatientMobileView.class)
public class AppointmentPatientMobilePresenter extends LazyPresenter<IAppointmentPatientMobileView, AppointmentEventBus> {
	
	@Inject
	AppointmentServiceAsync appointmentService;
	
	AppointmentBean appointment;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
		
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				String patientMobile = view.getPatientMobile();
				appointment.setPatientMobile(patientMobile);
				
				if (isValidated(appointment)) {
				
					ProgressDlg.show();
					
					appointmentService.sendVerificationCode(patientMobile, new AsyncCallback<String>() {
						
						@Override
						public void onSuccess(String verificationCode) {
							
							appointment.setVerificationCode(verificationCode);
							eventBus.getPatientInfo(appointment);
							view.hide();
							ProgressDlg.hide();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							ProgressDlg.failure();
						}
					});
				} else {
					NotificationDlg.error(errorMessages);
				}
			}
		});
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	public void onVerifyPatientMobile(AppointmentBean appointment) {
		this.appointment = appointment;
		view.show();
	}
	
	private boolean isValidated(AppointmentBean appointment) {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		if (ClientUtil.isEmpty(appointment.getPatientMobile())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_APPT_PATIENT_MOBILE_EMPTY);
		}
		
		return isValidated;
	}
}
