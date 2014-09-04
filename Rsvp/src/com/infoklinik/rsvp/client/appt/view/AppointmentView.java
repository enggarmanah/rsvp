package com.infoklinik.rsvp.client.appt.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IAppointmentView;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ScheduleAppointmentBean;
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
	
	AppointmentBean appointment;
	
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
		
	public void setSchedulesAndAppointments(ScheduleAppointmentBean scheduleAppointment) {
		
		List<ScheduleBean> schedules = scheduleAppointment.getSchedules();
		List<AppointmentBean> appointments = scheduleAppointment.getAppointments();
		HashMap<Long, AppointmentBean> reservedTimes = new HashMap<Long, AppointmentBean>();
		
		apptTimeLb.clear();
		
		for (AppointmentBean appt : appointments) {
			reservedTimes.put(appt.getApptDate().getTime(), appt);
		}
		
		if (schedules.size() > 0) {
			
			ArrayList<Integer> availableApptTimes = new ArrayList<Integer>();
			int apptInterval = Constant.APPT_INTERVAL_MINUTES * Constant.MIN_SECS * Constant.MILISECS;
			
			for (ScheduleBean scheBean : schedules) {
				
				for (int i = scheBean.getOpStart(); i < scheBean.getOpEnd(); i += apptInterval) {
					if (!availableApptTimes.contains(i)) {
						
						Date apptDate = new Date(scheduleAppointment.getDate().getTime());
						apptDate.setTime(apptDate.getTime() + i);
						
						AppointmentBean appointment = reservedTimes.get(apptDate.getTime());
						
						if (appointment == null) {
							availableApptTimes.add(i);
						}
					}
				}
			}
			
			Collections.sort(availableApptTimes);
			
			for (Integer apptTime : availableApptTimes) {
				String time = ClientUtil.timeToStr(apptTime);
				apptTimeLb.addItem(time, String.valueOf(apptTime));
			}
		}
		
		if (apptTimeLb.getItemCount() == 0) {
			apptTimeLb.addItem(Message.ERR_APPT_NO_SCHEDULE, Constant.EMPTY_STRING);
		}
	}
	
	public AppointmentBean getAppointment() {
		
		Date apptDate = null;
		
		if (apptDateDb.getValue() != null && !ClientUtil.isEmpty(apptTimeLb.getValue(apptTimeLb.getSelectedIndex()))) {
			apptDate = apptDateDb.getValue();
			apptDate.setTime(apptDate.getTime() + Long.valueOf(apptTimeLb.getValue(apptTimeLb.getSelectedIndex())));
		}
		
		appointment.setApptDate(apptDate);
		
		return appointment;
	}
	
	public void setAppointment(AppointmentBean appointment) {
		
		this.appointment = appointment;
		
		doctorLb.setText(appointment.getDoctor().getNameWithTitle());
		specialityLb.setText(appointment.getDoctor().getSpeciality().getDescription());
		institutionLb.setText(appointment.getInstitution().getName());
		apptDateDb.setValue(appointment.getApptDate());
	}
	
	public void setApptDateDbValueChangeHandler(ValueChangeHandler<Date> handler) {
		
		apptDateDb.addValueChangeHandler(handler);
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