package com.infoklinik.rsvp.client.doctor.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.doctor.presenter.interfaces.IDoctorProfileView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;

public class DoctorProfileView extends BaseView implements IDoctorProfileView {
	
	private DialogBox dialogBox;
	
	@UiField
	Label scheduleMenuLb;
	
	@UiField
	Label locationMenuLb;
	
	@UiField
	Label infoMenuLb;
	
	@UiField
	Label nameLb;
	
	@UiField
	Label specialityLb;
	
	@UiField
	SimplePanel infoPanel;
	
	@UiField
	Image profileImg;
	
	@UiField
	Button okBtn;
	
	private static String STYLE_MENU_SELECTED = "menu-current";
	private static String STYLE_MENU = "menu";
	
	private DoctorProfileScheduleView scheduleView = new DoctorProfileScheduleView();
	
	private DoctorProfileLocationView locationView = new DoctorProfileLocationView();
	
	private DoctorProfileInfoView infoView = new DoctorProfileInfoView();
	
	interface ModuleUiBinder extends UiBinder<Widget, DoctorProfileView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	ServiceTypeSearchBean serviceTypeSearchBean = new ServiceTypeSearchBean();
	
	DoctorBean doctor;
	
	Label currentMenu;
	
	BaseView currentView;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return dialogBox;
	}
	
	public void setDoctor(DoctorBean doctor) {
		
		this.doctor = doctor;
	}
	
	public void setSchedules(List<ScheduleBean> schedules) {
		
		scheduleView.setSchedules(schedules);
	}
	
	public void setInstitutions(List<InstitutionBean> institutions) {
		
		locationView.setInstitutions(institutions);
	}
	
	public void setInfo(String info) {
		
		infoView.setInfo(info);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void show() {
		
		goToTop();
		
		fadeOut();
		
		if (doctor.getImageId() != null) {
			profileImg.setUrl("/image?id=" + doctor.getImageId());
		} else {
			profileImg.setUrl("images/doctor.jpg");
		}
		
		nameLb.setText(doctor.getNameWithTitle());
		specialityLb.setText(doctor.getSpeciality().getDescription());
		
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

	public void showSchedule() {
		
		setMenu(scheduleMenuLb);
		setView(scheduleView);
	}
	
	public void showLocation() {
		
		setMenu(locationMenuLb);
		setView(locationView);
	}
	
	public void showInfo() {
		
		setMenu(infoMenuLb);
		setView(infoView);
	}
	
	public void setScheduleMenuClickHandler(ClickHandler handler) {
		
		scheduleMenuLb.addClickHandler(handler);
	}
	
	public void setLocationMenuClickHandler(ClickHandler handler) {
		
		locationMenuLb.addClickHandler(handler);
	}
	
	public void setInfoMenuClickHandler(ClickHandler handler) {
		
		infoMenuLb.addClickHandler(handler);
	}
	
	private void setMenu(Label menuLb) {
		
		if (currentMenu != null) {
			currentMenu.removeStyleName(STYLE_MENU_SELECTED);
			currentMenu.setStyleName(STYLE_MENU);
		}
		
		menuLb.removeStyleName(STYLE_MENU);
		menuLb.setStyleName(STYLE_MENU_SELECTED);
		
		currentMenu = menuLb;
	}
	
	private void setView(final BaseView view) {
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				currentView = view;
				
				infoPanel.clear();
				infoPanel.setWidget(view);
				
				view.fadeIn();
				view.loadStyle();
			}
		};
		
		if (currentView != null) {	
			currentView.fadeOut();
		}
		
		timer.schedule(Constant.FADE_TIME);
	}
}