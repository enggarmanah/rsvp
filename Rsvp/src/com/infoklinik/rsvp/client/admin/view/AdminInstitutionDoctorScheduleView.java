package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionDoctorScheduleView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;

public class AdminInstitutionDoctorScheduleView extends BaseView implements IAdminInstitutionDoctorScheduleView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminInstitutionDoctorScheduleView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	ListBox dayLb;
	
	@UiField
	ListBox opHourStartLb;
	
	@UiField
	ListBox opHourEndLb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	DialogBox dialogBox;
	
	List<ServiceTypeBean> list;
	
	ScheduleBean schedule;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
		
		dayLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.ZERO_STRING);
		opHourStartLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.ZERO_STRING);
		opHourEndLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.ZERO_STRING);
		
		for (int i = Constant.DAY_START; i <= Constant.DAY_END; i++) {
			
			dayLb.addItem(ClientUtil.dayToStr(i), String.valueOf(i));
		}
		
		for (int i = Constant.OPENING_TIME_START; i <= Constant.OPENING_TIME_END; i += Constant.OPENING_PERIOD) {
			
			opHourStartLb.addItem(ClientUtil.timeToStr(i), String.valueOf(i));
			opHourEndLb.addItem(ClientUtil.timeToStr(i), String.valueOf(i));
		}
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	public void show() {
		
		dlgFadeOut();
		
		dialogBox.setText("Tambah Jadwal Praktek Baru");
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft() + Constant.POPUP_L2_LEFT, Constant.POPUP_L2_TOP);
		dialogBox.show();
		
		dlgFadeIn();
	}
	
	public void hide() {
		
		dlgFadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				dialogBox.hide();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}

	public ScheduleBean getSchedule() {
		
		schedule.setDay(Integer.valueOf(dayLb.getValue(dayLb.getSelectedIndex())));
		schedule.setOpStart(Integer.valueOf(opHourStartLb.getValue(opHourStartLb.getSelectedIndex())));
		schedule.setOpEnd(Integer.valueOf(opHourEndLb.getValue(opHourEndLb.getSelectedIndex())));
		
		return schedule;
	}
	
	public void setSchedule(ScheduleBean schedule) {
		
		this.schedule = schedule;
		
		dayLb.setSelectedIndex(0);
		opHourStartLb.setSelectedIndex(0);
		opHourEndLb.setSelectedIndex(0);
		
		ClientUtil.setSelectedIndex(dayLb, String.valueOf(schedule.getDay()));
		ClientUtil.setSelectedIndex(opHourStartLb, String.valueOf(schedule.getOpStart()));
		ClientUtil.setSelectedIndex(opHourEndLb, String.valueOf(schedule.getOpEnd()));
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
}
