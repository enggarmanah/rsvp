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
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IReservationPatientMobileView;
import com.infoklinik.rsvp.client.appt.view.ReservationPatientMobileView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.ReservationServiceAsync;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = ReservationPatientMobileView.class)
public class ReservationPatientMobilePresenter extends LazyPresenter<IReservationPatientMobileView, ReservationEventBus> {
	
	@Inject
	ReservationServiceAsync reservationService;
	
	ReservationBean reservation;
	
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
				reservation.setPatientMobile(patientMobile);
				
				if (isValidated(reservation)) {
				
					ProgressDlg.show();
					
					reservationService.sendVerificationCode(patientMobile, new AsyncCallback<String>() {
						
						@Override
						public void onSuccess(String verificationCode) {
							
							reservation.setVerificationCode(verificationCode);
							eventBus.getPatientInfo(reservation);
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
	
	public void onVerifyPatientMobile(ReservationBean reservation) {
		this.reservation = reservation;
		view.show();
	}
	
	private boolean isValidated(ReservationBean reservation) {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		if (ClientUtil.isEmpty(reservation.getPatientMobile())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_APPT_PATIENT_MOBILE_EMPTY);
		}
		
		return isValidated;
	}
}
