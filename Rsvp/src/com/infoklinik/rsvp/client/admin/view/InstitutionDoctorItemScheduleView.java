package com.infoklinik.rsvp.client.admin.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class InstitutionDoctorItemScheduleView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionDoctorItemScheduleView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Label dayLb;
	
	@UiField
	Label timeLb;
	
	@UiField
	Image deleteImg;
	
	public InstitutionDoctorItemScheduleView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setSchedule(GenericBean<ScheduleBean> genSchedule) {
		
		ScheduleBean schedule = genSchedule.getBean();
		HandlerManager handlerMgr = genSchedule.getHandlerMgr();
		
		dayLb.setText(ClientUtil.dayToStr(schedule.getDay()) + " :");
		timeLb.setText(ClientUtil.timeToStr(schedule.getOpStart()) + " - " + ClientUtil.timeToStr(schedule.getOpEnd()));
		
		dayLb.addClickHandler(handlerMgr.getUpdateHandler());
		timeLb.addClickHandler(handlerMgr.getUpdateHandler());
		
		deleteImg.addClickHandler(handlerMgr.getDeleteHandler());
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
