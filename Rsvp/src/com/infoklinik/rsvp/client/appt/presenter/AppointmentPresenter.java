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
import com.infoklinik.rsvp.client.appt.AppointmentEventBus;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IAppointmentView;
import com.infoklinik.rsvp.client.appt.view.AppointmentView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.AppointmentServiceAsync;
import com.infoklinik.rsvp.client.rpc.ScheduleServiceAsync;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.ScheduleAppointmentBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ScheduleSearchBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AppointmentView.class)
public class AppointmentPresenter extends LazyPresenter<IAppointmentView, AppointmentEventBus> {
	
	@Inject
	ScheduleServiceAsync scheduleService;
	
	@Inject
	AppointmentServiceAsync appointmentService;
	
	ScheduleBean schedule;
	AppointmentBean appointment;
	
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
		
		view.setApptDateDbValueChangeHandler(new ValueChangeHandler<Date>() {
			
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
				
				if (isValidated(view.getAppointment())) {
					view.hide();
					
					if (isSelectAnotherDate) {
						addAppointment();
					} else {
						eventBus.verifyPatientMobile(view.getAppointment());
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
	
	private void loadAppointment(ScheduleBean schedule) {
		
		isSelectAnotherDate = false;
		this.schedule = schedule;
		
		appointment = new AppointmentBean();
		appointment.setDoctor(schedule.getDoctor());
		appointment.setInstitution(schedule.getInstitutionBean());
		appointment.setApptDate(ClientUtil.getDateOfWeek(schedule.getDay()));
		
		apptDate = appointment.getApptDate();
		apptDay = ClientUtil.dateToDayOfWeek(apptDate);
		
		view.setAppointment(appointment);
		
		if (isWindowLv2) {
			view.showLv2();
		} else {
			view.show();
		}
		
		
		initSchedules();
	}
	
	public void onLoadAppointment(ScheduleBean schedule) {
		
		isWindowLv2 = false;
		loadAppointment(schedule);
	}
	
	public void onLoadAppointmentLv2(ScheduleBean schedule) {
		
		isWindowLv2 = true;
		loadAppointment(schedule);
	}
	
	public void onSelectAnotherDate(AppointmentBean appointment) {
		
		isSelectAnotherDate = true;
		
		this.appointment = appointment;
		view.setAppointment(appointment);
		
		apptDate = appointment.getApptDate();
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
		
		scheduleService.getSchedulesAndAppointments(scheduleSearch, new AsyncCallback<ScheduleAppointmentBean>() {
			
			@Override
			public void onSuccess(ScheduleAppointmentBean scheduleAppointment) {
				view.setSchedulesAndAppointments(scheduleAppointment);
				ProgressDlg.hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.hidePrompt();
				NotificationDlg.warning(Message.ERR_COMMON_LOAD_FAILED);
			}
		});
	}
	
	private void addAppointment() {
		
		ProgressDlg.show();
		
		appointmentService.addAppointment(appointment, new AsyncCallback<AppointmentBean>() {
			
			@Override
			public void onSuccess(AppointmentBean result) {
				
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
	
	
	private boolean isValidated(AppointmentBean appointment) {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		if (appointment.getApptDate() == null) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_APPT_NO_SCHEDULE);
		}
		
		return isValidated;
	}
}
