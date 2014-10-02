package com.infoklinik.rsvp.client.appt.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.infoklinik.rsvp.client.appt.ReservationEventBus;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IReservationServiceView;
import com.infoklinik.rsvp.client.appt.view.ReservationServiceView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.rpc.ReservationServiceAsync;
import com.infoklinik.rsvp.client.rpc.ScheduleServiceAsync;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = ReservationServiceView.class)
public class ReservationServicePresenter extends LazyPresenter<IReservationServiceView, ReservationEventBus> {
	
	@Inject
	ScheduleServiceAsync scheduleService;
	
	@Inject
	ReservationServiceAsync appointmentService;
	
	ServiceBean service;
	ReservationBean reservation;
	
	Date apptDate;
	Integer apptDay;
	
	boolean isWindowLv2 = false;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (isValidated(view.getReservation())) {
					view.hide();
					eventBus.verifyPatientMobile(view.getReservation());
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
	
	private void loadReservation(ServiceBean service) {
		
		this.service = service;
		
		reservation = new ReservationBean();
		reservation.setInstitution(service.getInstitution());
		
		view.setReservation(reservation);
		
		if (isWindowLv2) {
			view.showLv2();
		} else {
			view.show();
		}
	}
	
	public void onLoadReservationService(ServiceBean service) {
		
		isWindowLv2 = false;
		loadReservation(service);
	}
	
	public void onLoadReservationServiceLv2(ServiceBean service) {
		
		isWindowLv2 = true;
		loadReservation(service);
	}
	
	private boolean isValidated(ReservationBean reservation) {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		return isValidated;
	}
}
