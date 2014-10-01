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
import com.infoklinik.rsvp.client.appt.ReservationEventBus;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IReservationPatientInfoView;
import com.infoklinik.rsvp.client.appt.view.ReservationPatientInfoView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.ReservationServiceAsync;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = ReservationPatientInfoView.class)
public class ReservationPatientInfoPresenter extends LazyPresenter<IReservationPatientInfoView, ReservationEventBus> {
	
	@Inject
	ReservationServiceAsync appointmentService;
	
	ReservationBean appointment;
	String verificationCode;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	public void onGetPatientInfo(ReservationBean appointment) {
		verificationCode = appointment.getVerificationCode();
		this.appointment = appointment;
		view.setAppointment(appointment);
		view.show();
	}
	
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				appointment = view.getAppointment();
				
				if (isValidated(appointment)) {
					
					if (!ClientUtil.isEmpty(verificationCode) && !verificationCode.equals(appointment.getVerificationCode())) {
						NotificationDlg.warning(Message.ERR_INVALID_VERIFICATION_CODE);
					} else {
						addAppointment();
					}
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
	
	private void addAppointment() {
		
		ProgressDlg.show();
		
		appointmentService.addReservation(appointment, new AsyncCallback<ReservationBean>() {
			
			@Override
			public void onSuccess(ReservationBean result) {
				
				ProgressDlg.hide();
								
				appointment = result;
				
				if (appointment.getId() != null) {
					view.hide();
					NotificationDlg.info("Reservasi kunjungan dokter telah berhasil. \nKode reservasi \"" + 
						appointment.getReservationCode() + "\" telah dikirim ke handphone anda.");
					
				} else {
					NotificationDlg.warning(Message.ERR_APPT_NOT_AVAILABLE, new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							
							view.hide();
							eventBus.selectAnotherDate(appointment);
						}
					});
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
	
	private boolean isValidated(ReservationBean appointment) {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		if (ClientUtil.isEmpty(appointment.getPatientName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_APPT_PATIENT_NAME_EMPTY);
		}
		
		if (ClientUtil.isEmpty(appointment.getPatientBirthYear()) ||
			appointment.getPatientBirthYear().trim().length() != 4) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_APPT_PATIENT_BIRTH_YEAR_INVALID);
		}
		
		return isValidated;
	}
}