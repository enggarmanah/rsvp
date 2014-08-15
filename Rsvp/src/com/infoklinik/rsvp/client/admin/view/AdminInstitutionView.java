package com.infoklinik.rsvp.client.admin.view;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionView;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;

public class AdminInstitutionView extends BaseView implements IAdminInstitutionView {
	
	private DialogBox dialogBox;
	
	@UiField
	Label locationMenuLb;
	
	@UiField
	Label serviceMenuLb;
	
	@UiField
	Label doctorMenuLb;
	
	@UiField
	Label galleryMenuLb;
	
	@UiField
	Label branchMenuLb;
	
	@UiField
	SimplePanel editPanel;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	private static String STYLE_MENU_SELECTED = "menu-current";
	private static String STYLE_MENU = "menu";
	
	private InstitutionMainView mainView = new InstitutionMainView();
	
	private InstitutionServiceView serviceView = new InstitutionServiceView();
	
	private InstitutionDoctorView doctorView = new InstitutionDoctorView();
	
	private InstitutionGalleryView galleryView = new InstitutionGalleryView();
	
	private InstitutionBranchView branchView = new InstitutionBranchView();
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminInstitutionView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	ServiceTypeSearchBean serviceTypeSearchBean = new ServiceTypeSearchBean();
	
	InstitutionBean institution;
	
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
	
	public InstitutionBean getInstitution() {
		
		institution = mainView.getInstitution();
		institution = serviceView.getInstitution();
		institution = galleryView.getInstitution();
				
		return institution;
	}
	
	public void setInstitution(InstitutionBean institution) {
		
		this.institution = institution;
		
		mainView.setInstitution(institution);
		serviceView.setInstitution(institution);
		galleryView.setInstitution(institution);
		
		if (institution.getId() == null) {
			dialogBox.setText("Tambah Institusi Baru");
		} else {
			dialogBox.setText("Perubahan Data Institusi");
		}
		
	}
	
	public void setCategories(List<MasterCodeBean> masterCodeBeans) {
		
		mainView.setCategories(masterCodeBeans);
	}
	
	public void setTypes(List<MasterCodeBean> masterCodeBeans) {
		
		mainView.setTypes(masterCodeBeans);
	}
	
	public void setPartnerTypes(List<MasterCodeBean> masterCodeBeans) {
		
		mainView.setPartnerTypes(masterCodeBeans);
	}
	
	public void setServices(List<GenericBean<ServiceBean>> services) {
		
		serviceView.setServices(services);
	}
	
	public void setInsurances(List<GenericBean<InsuranceBean>> insurances) {
		
		serviceView.setInsurances(insurances);
	}
	
	public void setDoctors(List<GenericBean<DoctorBean>> doctors) {
		
		doctorView.setDoctors(doctors);
	}
	
	public void setDoctorScheduleHandlerMgrs(HashMap<DoctorBean, HashMap<ScheduleBean, HandlerManager>> doctorScheduleHandlerMgrs) {
		
		doctorView.setDoctorScheduleHandlerMgr(doctorScheduleHandlerMgrs);
	}
	
	public void addDoctor(GenericBean<DoctorBean> doctors) {
		
		doctorView.addDoctor(doctors);
	}
	
	public void updateDoctor(GenericBean<DoctorBean> doctor) {
		
		doctorView.updateDoctor(doctor);
	}
	
	public void deleteDoctor(GenericBean<DoctorBean> doctor) {
		
		doctorView.deleteDoctor(doctor);
	}
	
	public void setGalleries(List<GalleryBean> galleries) {
		
		galleryView.setGalleries(galleries);
	}
	
	public void setBranches(List<BranchBean> branches) {
		
		branchView.setBranches(branches);
	}
	
	public void setLocationMenuClickHandler(ClickHandler handler) {
		
		locationMenuLb.addClickHandler(handler);
	}
	
	public void setServiceMenuClickHandler(ClickHandler handler) {
		
		serviceMenuLb.addClickHandler(handler);
	}
	
	public void setDoctorMenuClickHandler(ClickHandler handler) {
		
		doctorMenuLb.addClickHandler(handler);
	}
	
	public void setGalleryMenuClickHandler(ClickHandler handler) {
		
		galleryMenuLb.addClickHandler(handler);
	}
	
	public void setBranchMenuClickHandler(ClickHandler handler) {
		
		branchMenuLb.addClickHandler(handler);
	}
	
	public void setAddServiceBtnClickHandler(ClickHandler handler) {
		
		serviceView.setAddServiceBtnClickHandler(handler);
	}
	
	public void setAddInsuranceBtnClickHandler(ClickHandler handler) {
		
		serviceView.setAddInsuranceBtnClickHandler(handler);
	}
	
	public void setAddDoctorBtnClickHandler(ClickHandler handler) {
		
		doctorView.setAddDoctorBtnClickHandler(handler);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
	
	public void setCategoryChangeHandler(ChangeHandler handler) {
		
		mainView.setCategoryChangeHandler(handler);
	}
	
	public void setCitySelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler) {
		
		mainView.setCitySelectionHandler(handler);
	}
	
	public void setRegionSelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler) {
		
		mainView.setRegionSelectionHandler(handler);
	}
	
	public void setStreetSelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler) {
		
		mainView.setStreetSelectionHandler(handler);
	}
	
	public void setOp24HoursChangeHandler(ChangeHandler handler) {
		
		serviceView.setOp24HoursChangeHandler(handler);
	}
	
	public void show() {
		
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

	public void showLocation() {
		
		setMenu(locationMenuLb);
		setView(mainView);
	}
	
	public void showService() {
		
		setMenu(serviceMenuLb);
		setView(serviceView);
	}
	
	public void showDoctor() {
		
		setMenu(doctorMenuLb);
		setView(doctorView);
	}
	
	public void showGallery() {
		
		setMenu(galleryMenuLb);
		setView(galleryView);
	}
	
	public void showBranch() {
		
		setMenu(branchMenuLb);
		setView(branchView);
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
				
				editPanel.clear();
				editPanel.setWidget(view);
				
				view.loadContent();
				
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