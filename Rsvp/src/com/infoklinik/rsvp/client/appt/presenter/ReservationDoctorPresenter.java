package com.infoklinik.rsvp.client.appt.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.appt.ReservationEventBus;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IReservationDoctorView;
import com.infoklinik.rsvp.client.appt.view.ReservationDoctorView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.ReservationServiceAsync;
import com.infoklinik.rsvp.client.rpc.ScheduleServiceAsync;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ScheduleReservationBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ScheduleSearchBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = ReservationDoctorView.class)
public class ReservationDoctorPresenter extends LazyPresenter<IReservationDoctorView, ReservationEventBus> {
	
	@Inject
	ScheduleServiceAsync scheduleService;
	
	@Inject
	ReservationServiceAsync reservationService;
	
	ScheduleBean schedule;
	ReservationBean reservation;
	
	Date apptDate;
	Integer apptDay;
	
	boolean isSelectAnotherDate = false;
	boolean isWindowLv2 = false;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initApptDateSelection();
		initBtnHandler();
	}
	
	private void initApptDateSelection() {
		
		view.setResvDateDbValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				
				apptDate = event.getValue();
				apptDay = ClientUtil.dateToDayOfWeek(apptDate);
				
				initSchedules();
			}
		});
	}
	
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (isValidated(view.getReservation())) {
					view.hide();
					
					if (isSelectAnotherDate) {
						addReservation();
					} else {
						eventBus.verifyPatientMobile(view.getReservation());
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
	
	private void loadReservation(ScheduleBean schedule) {
		
		isSelectAnotherDate = false;
		this.schedule = schedule;
		
		reservation = new ReservationBean();
		reservation.setDoctor(schedule.getDoctor());
		reservation.setInstitution(schedule.getInstitutionBean());
		reservation.setApptDate(ClientUtil.getDateOfWeek(schedule.getDay()));
		
		apptDate = reservation.getApptDate();
		apptDay = ClientUtil.dateToDayOfWeek(apptDate);
		
		view.setReservation(reservation);
		
		if (isWindowLv2) {
			view.showLv2();
		} else {
			view.show();
		}
		
		
		initSchedules();
	}
	
	public void onLoadReservationDoctor(ScheduleBean schedule) {
		
		isWindowLv2 = false;
		loadReservation(schedule);
	}
	
	public void onLoadReservationDoctorLv2(ScheduleBean schedule) {
		
		isWindowLv2 = true;
		loadReservation(schedule);
	}
	
	public void onSelectAnotherDate(ReservationBean reservation) {
		
		isSelectAnotherDate = true;
		
		this.reservation = reservation;
		view.setReservation(reservation);
		
		apptDate = reservation.getApptDate();
		apptDay = ClientUtil.dateToDayOfWeek(apptDate);
		
		initSchedules();
		
		view.show();
	}
	
	private void initSchedules() {
		
		ProgressDlg.show();
		
		ScheduleSearchBean scheduleSearch = new ScheduleSearchBean();
		scheduleSearch.setDoctorId(schedule.getDoctor().getId());
		scheduleSearch.setInstId(schedule.getInstitutionBean().getId());
		
		scheduleSearch.setDate(apptDate);
		scheduleSearch.setDay(apptDay);
		
		scheduleService.getSchedulesAndReservations(scheduleSearch, new AsyncCallback<ScheduleReservationBean>() {
			
			@Override
			public void onSuccess(ScheduleReservationBean scheduleReservation) {
				view.setSchedulesAndReservations(scheduleReservation);
				ProgressDlg.hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.hidePrompt();
				NotificationDlg.warning(Message.ERR_COMMON_LOAD_FAILED);
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
					NotificationDlg.info("Reservasi kunjungan dokter telah berhasil. \nKode reservasi \"" + 
						reservation.getReservationCode() + "\" telah dikirim ke handphone anda.");	
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
		
		if (reservation.getApptDate() == null) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_APPT_NO_SCHEDULE);
		}
		
		return isValidated;
	}
}
