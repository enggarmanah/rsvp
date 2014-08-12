package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionDoctorScheduleView;
import com.infoklinik.rsvp.client.admin.view.AdminInstitutionDoctorScheduleView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminInstitutionDoctorScheduleView.class)
public class AdminInstitutionDoctorSchedulePresenter extends LazyPresenter<IAdminInstitutionDoctorScheduleView, AdminEventBus> {
	
	boolean isAdd = true;
	
	ScheduleBean schedule;
	ScheduleBean orgSchedule;
	
	List<ScheduleBean> existingSchedules;
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initOkBtnClickHandler();
		initCancelBtnClickHandler();
	}
	
	public void onAddInstitutionDoctorSchedule(DoctorBean doctor) {
		
		isAdd = true;
		orgSchedule = null;
		
		schedule = new ScheduleBean();
		schedule.setDoctor(doctor);
		
		view.setSchedule(schedule);
		view.show();
	}
	
	public void onUpdateInstitutionDoctorSchedule(ScheduleBean schedule) {
				
		isAdd = false;
		orgSchedule = schedule;
		
		this.schedule = new ScheduleBean();
		this.schedule.setBean(orgSchedule);
		
		view.setSchedule(this.schedule);
		view.show();
	}
	
	private void initOkBtnClickHandler() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				schedule = view.getSchedule();
				
				if (isValidated()) {
					
					if (isAdd) {
						eventBus.addInstDoctorSchedule(schedule);
					} else {
						orgSchedule.setBean(schedule);
						eventBus.updateInstDoctorSchedule(orgSchedule);
					}
						
					view.hide();
					ProgressDlg.hide();
					
				} else {
					NotificationDlg.error(errorMessages);
				}
			}
		});
		
	}
	
	private void initCancelBtnClickHandler() {
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	private boolean isValidated() {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		schedule = view.getSchedule();
		
		if (schedule.getDay() == 0) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_DOC_SCHEDULE_DAY_EMPTY);
		}
		
		if (schedule.getOpStart() == 0) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_DOC_SCHEDULE_TIME_START_EMPTY);
		}
		
		if (schedule.getOpEnd() == 0) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_DOC_SCHEDULE_TIME_END_EMPTY);
		}
		
		return isValidated;
	}
}
