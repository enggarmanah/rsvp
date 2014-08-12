package com.infoklinik.rsvp.client.admin.view;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class InstitutionDoctorItemView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionDoctorItemView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	FocusPanel nameLinkLb;
	
	@UiField
	Label nameLb;
	
	@UiField
	Label specialityLb;
	
	@UiField
	Image deleteImg;
	
	@UiField
	FlowPanel schedulePanel;
	
	@UiField
	FocusPanel addScheduleBtn;
	
	HashMap<ScheduleBean, HandlerManager> scheduleHandlerMgrs;
	
	public InstitutionDoctorItemView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setScheduleHandlerMgrs(HashMap<ScheduleBean, HandlerManager> scheduleHandlerMgr) {
		
		this.scheduleHandlerMgrs = scheduleHandlerMgr;
	}
	
	public void setDoctor(GenericBean<DoctorBean> genDoctor) {
		
		DoctorBean doctor = genDoctor.getBean();
		HandlerManager handlerMgr = genDoctor.getHandlerMgr();
		
		nameLb.setText(doctor.getName());
		specialityLb.setText(doctor.getSpeciality().getDescription());
		
		nameLinkLb.addClickHandler(handlerMgr.getUpdateHandler());
		deleteImg.addClickHandler(handlerMgr.getDeleteHandler());
		
		addScheduleBtn.addClickHandler(handlerMgr.getAddHandler());
		
		if (doctor.getSchedules() != null) {
			
			schedulePanel.clear();
			
			for (ScheduleBean schedule : doctor.getSchedules()) {
				
				HandlerManager handlerMg = scheduleHandlerMgrs.get(schedule);
				GenericBean<ScheduleBean> genSchedule = new GenericBean<ScheduleBean>(schedule, handlerMg);
				
				InstitutionDoctorItemScheduleView scheduleItem = new InstitutionDoctorItemScheduleView();
				scheduleItem.setSchedule(genSchedule);
				
				schedulePanel.add(scheduleItem);
			}
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
