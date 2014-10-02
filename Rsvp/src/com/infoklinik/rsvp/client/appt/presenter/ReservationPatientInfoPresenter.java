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
	ReservationServiceAsync reservationService;
	
	ReservationBean reservation;
	String verificationCode;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	public void onGetPatientInfo(ReservationBean reservation) {
		verificationCode = reservation.getVerificationCode();
		this.reservation = reservation;
		view.setReservation(reservation);
		view.show();
	}
	
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				reservation = view.getReservation();
				
				if (isValidated(reservation)) {
					
					if (!ClientUtil.isEmpty(verificationCode) && !verificationCode.equals(reservation.getVerificationCode())) {
						NotificationDlg.warning(Message.ERR_INVALID_VERIFICATION_CODE);
					} else {
						addReservation();
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
	
	private void addReservation() {
		
		ProgressDlg.show();
		
		reservationService.addReservation(reservation, new AsyncCallback<ReservationBean>() {
			
			@Override
			public void onSuccess(ReservationBean result) {
				
				ProgressDlg.hide();
								
				reservation = result;
				
				if (reservation.getId() != null) {
					view.hide();
					
					
					if (reservation.getDoctor() != null) {
						NotificationDlg.info("Reservasi kunjungan dokter telah berhasil. \nKode Reservasi: \"" + 
							reservation.getReservationCode() + "\" telah dikirim ke handphone anda.");
						
					} else if (reservation.getService() != null) {
						NotificationDlg.info("Registrasi telah berhasil. \nKode Voucher: \"" + 
								reservation.getReservationCode() + "\" telah dikirim ke handphone anda.");
					}
					
				} else {
					NotificationDlg.warning(Message.ERR_APPT_NOT_AVAILABLE, new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							
							view.hide();
							eventBus.selectAnotherDate(reservation);
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
	
	private boolean isValidated(ReservationBean reservation) {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		if (ClientUtil.isEmpty(reservation.getPatientName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_APPT_PATIENT_NAME_EMPTY);
		}
		
		if (ClientUtil.isEmpty(reservation.getPatientBirthYear()) ||
			reservation.getPatientBirthYear().trim().length() != 4) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_APPT_PATIENT_BIRTH_YEAR_INVALID);
		}
		
		return isValidated;
	}
}