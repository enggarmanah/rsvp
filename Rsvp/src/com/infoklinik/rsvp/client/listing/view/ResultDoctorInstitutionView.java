package com.infoklinik.rsvp.client.listing.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class ResultDoctorInstitutionView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, ResultDoctorInstitutionView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Label nameLb;
	
	@UiField
	Label addressLb;
	
	@UiField
	Label telephoneLb;
	
	@UiField
	Label emailLb;
	
	@UiField
	VerticalPanel schedulesPanel;
	
	private static String STYLE_SCHEDULE = "doctor-schedule";
	private static String STYLE_SCHEDULE_DAY = "day";
	private static String STYLE_SCHEDULE_TIME = "time";
	
	public ResultDoctorInstitutionView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setInstitution(GenericBean<InstitutionBean> genInstitution) {
		
		InstitutionBean institution = genInstitution.getBean();
		HandlerManager handlerMgr = genInstitution.getHandlerMgr();
		
		nameLb.setText(institution.getName());
		addressLb.setText(institution.getAddress());
		telephoneLb.setText(institution.getTelephone());
		emailLb.setText(institution.getEmail());
		
		nameLb.addClickHandler(handlerMgr.getShowHandler());
	}
	
	public void setSchedules(List<GenericBean<ScheduleBean>> schedules) {
		
		for (GenericBean<ScheduleBean> genSchedule : schedules) {
			
			ScheduleBean schedule = genSchedule.getBean();
			HandlerManager handlerMgr = genSchedule.getHandlerMgr();
		
			Label dayLb = new Label();
			dayLb.setText(ClientUtil.dayToStr(schedule.getDay()) + " :");
			dayLb.setStyleName(STYLE_SCHEDULE_DAY);
			
			Label timeLb = new Label();
			String timeStr = ClientUtil.timeToStr(schedule.getOpStart()) + "-" + ClientUtil.timeToStr(schedule.getOpEnd()); 
			timeLb.setText(timeStr);
			timeLb.setStyleName(STYLE_SCHEDULE_TIME);
			
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			horizontalPanel.setStyleName(STYLE_SCHEDULE);
			horizontalPanel.add(dayLb);
			horizontalPanel.add(timeLb);
			
			FocusPanel schedulePanel = new FocusPanel();
			
			schedulePanel.add(horizontalPanel);
			schedulePanel.addClickHandler(handlerMgr.getShowHandler());
			
			schedulesPanel.add(schedulePanel);
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
