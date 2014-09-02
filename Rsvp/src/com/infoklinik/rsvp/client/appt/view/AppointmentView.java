package com.infoklinik.rsvp.client.appt.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IAppointmentView;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class AppointmentView extends BaseView implements IAppointmentView {
	
	private DialogBox dialogBox;
	
	@UiField
	Label doctorLb;
	
	@UiField
	Label specialityLb;
	
	@UiField
	Label institutionLb;
	
	@UiField
	DateBox apptDateDb;
	
	@UiField
	ListBox apptTimeLb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, AppointmentView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	DoctorBean doctor;
	InstitutionBean institution;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
		dialogBox.setText("Jadwal Kunjungan Dokter");
		
		DateTimeFormat dtf = ClientUtil.getDateTime(ClientUtil.DATE_TIME_FORMAT_DAYDATE);
		apptDateDb.setFormat(new DateBox.DefaultFormat(dtf));
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return dialogBox;
	}
	
	public void setDoctor(DoctorBean doctor) {
		
		this.doctor = doctor;
		
		doctorLb.setText(doctor.getNameWithTitle());
		specialityLb.setText(doctor.getSpeciality().getDescription());
	}
	
	public void setInstitution(InstitutionBean institution) {
		
		this.institution = institution;
		
		institutionLb.setText(institution.getName());
	}
	
	public void setDate(Date date) {
		
		apptDateDb.setValue(date);
	}
	
	public void setSchedules(List<ScheduleBean> schedules) {
		
		if (schedules.size() > 0) {
			
			ArrayList<Integer> apptTimes = new ArrayList<Integer>();
			int apptInterval = Constant.APPT_INTERVAL_MINUTES * Constant.MIN_SECS * Constant.MILISECS;
			
			for (ScheduleBean scheBean : schedules) {
				
				for (int i = scheBean.getOpStart(); i < scheBean.getOpEnd(); i += apptInterval) {
					if (!apptTimes.contains(i)) {
						apptTimes.add(i);
					}
				}
			}
			
			Collections.sort(apptTimes);
			apptTimeLb.clear();
			
			for (Integer apptTime : apptTimes) {
				String time = ClientUtil.timeToStr(apptTime);
				apptTimeLb.addItem(time, String.valueOf(apptTime));
			}
		}
	}
	
	public AppointmentBean getAppointment() {
		
		Date apptDate = apptDateDb.getValue();
		apptDate.setTime(apptDate.getTime() + Long.valueOf(apptTimeLb.getValue(apptTimeLb.getSelectedIndex())));
		
		AppointmentBean appointment = new AppointmentBean();
		appointment.setDoctor(doctor);
		appointment.setInstitution(institution);
		appointment.setApptDate(apptDate);
		
		return appointment;
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