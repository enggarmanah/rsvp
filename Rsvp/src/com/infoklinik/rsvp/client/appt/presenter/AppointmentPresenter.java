package com.infoklinik.rsvp.client.appt.presenter;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.appt.AppointmentEventBus;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IAppointmentView;
import com.infoklinik.rsvp.client.appt.view.AppointmentView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.ScheduleServiceAsync;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AppointmentView.class)
public class AppointmentPresenter extends LazyPresenter<IAppointmentView, AppointmentEventBus> {
	
	@Inject
	ScheduleServiceAsync scheduleService;
	
	DoctorBean doctor;
	InstitutionBean institution;
	Integer apptDay;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
		
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
				eventBus.verifyPatientMobile(view.getAppointment());
			}
		});
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	public void onLoadAppointment(ScheduleBean schedule) {
		
		this.doctor = schedule.getDoctor();
		this.institution = schedule.getInstitutionBean();
		this.apptDay = schedule.getDay();
		
		view.setDoctor(doctor);
		view.setInstitution(institution);
		
		Date apptDate = ClientUtil.getDateOfWeek(apptDay); 
		view.setDate(apptDate);
		
		initSchedules();
		
		view.show();
	}
	
	private void initSchedules() {
		
		ProgressDlg.show();
		
		scheduleService.getSchedules(doctor.getId(), institution.getId(), apptDay, new AsyncCallback<List<ScheduleBean>>() {
			
			@Override
			public void onSuccess(List<ScheduleBean> schedules) {
				view.setSchedules(schedules);
				ProgressDlg.hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.hidePrompt();
				NotificationDlg.warning(Message.ERR_COMMON_LOAD_FAILED);
			}
		});
	}
}
