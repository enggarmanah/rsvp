package com.infoklinik.rsvp.client.doctor.view;

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
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class InstitutionView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionView> {}
	
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
	
	public InstitutionView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setInstitution(InstitutionBean institution) {
		
		nameLb.setText(institution.getName());
		addressLb.setText(institution.getAddress());
		telephoneLb.setText(institution.getTelephone());
		emailLb.setText(institution.getEmail());
	}
	
	public void setSchedules(List<ScheduleBean> schedules) {
		
		for (ScheduleBean schedule : schedules) {
		
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
