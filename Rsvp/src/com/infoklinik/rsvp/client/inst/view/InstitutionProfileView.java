package com.infoklinik.rsvp.client.inst.view;

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
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.inst.presenter.interfaces.IInstitutionProfileView;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;

public class InstitutionProfileView extends BaseView implements IInstitutionProfileView {
	
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
	Label nameLb;
	
	@UiField
	Label addressLb;
	
	@UiField
	Label phoneLb;
	
	@UiField
	Label emailLb;
	
	@UiField
	Label websiteLb;
	
	@UiField
	Label facebookLb;
	
	@UiField
	Label twitterLb;
	
	@UiField
	SimplePanel infoPanel;
	
	@UiField
	SimplePanel linksPanel;
	
	@UiField
	SimplePanel websiteImg;
	
	@UiField
	SimplePanel websiteInfo;
	
	@UiField
	SimplePanel facebookImg;
	
	@UiField
	SimplePanel facebookInfo;
	
	@UiField
	SimplePanel twitterImg;
	
	@UiField
	SimplePanel twitterInfo;
	
	@UiField
	Image logoImg;
	
	@UiField
	Button okBtn;
	
	private static String STYLE_MENU_SELECTED = "menu-current";
	private static String STYLE_MENU = "menu";
	
	private InstitutionProfileLocationView locationView = new InstitutionProfileLocationView();
	
	private InstitutionProfileServiceView serviceView = new InstitutionProfileServiceView();
	
	private InstitutionProfileDoctorView doctorView = new InstitutionProfileDoctorView();
	
	private InstitutionProfileGalleryView galleryView = new InstitutionProfileGalleryView();
	
	private InstitutionProfileBranchView branchView = new InstitutionProfileBranchView();
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionProfileView> {}
	
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
	
	public void setInstitution(InstitutionBean institution) {
		
		this.institution = institution;
		
		if (Constant.CATEGORY_LABORATORY.equals(institution.getCategory()) ||
				Constant.CATEGORY_PHARMACY.equals(institution.getCategory())) {
			doctorMenuLb.setVisible(false);
		} else {
			doctorMenuLb.setVisible(true);
		}
		
		locationView.setInstitution(institution);
	}
	
	private void hideDisplayLinks() {
		
		if (ClientUtil.isEmpty(institution.getWebsite()) && 
			ClientUtil.isEmpty(institution.getFacebook()) &&
			ClientUtil.isEmpty(institution.getTwitter())) {
			
			linksPanel.setVisible(false);
		} else {
			
			linksPanel.setVisible(true);
			
			if (ClientUtil.isEmpty(institution.getWebsite())) {
				websiteImg.setVisible(false);
				websiteInfo.setVisible(false);
			} else {
				websiteImg.setVisible(true);
				websiteInfo.setVisible(true);
			}
			
			if (ClientUtil.isEmpty(institution.getFacebook())) {
				facebookImg.setVisible(false);
				facebookInfo.setVisible(false);
			} else {
				facebookImg.setVisible(true);
				facebookInfo.setVisible(true);
			}
			
			if (ClientUtil.isEmpty(institution.getTwitter())) {
				twitterImg.setVisible(false);
				twitterInfo.setVisible(false);
			} else {
				twitterImg.setVisible(true);
				twitterInfo.setVisible(true);
			}
		}
	}
	
	public void setServices(List<ServiceBean> services) {
		
		serviceView.setServices(services);
	}
	
	public void setPromotions(List<ServiceBean> services) {
		
		serviceView.setPromotions(services);
	}
	
	public void setInsurances(List<InsuranceBean> insurances) {
		
		serviceView.setInsurances(insurances);
	}
	
	public void setDoctors(List<GenericBean<DoctorBean>> doctors) {
		
		doctorView.setDoctors(doctors);
	}
	
	public void setGalleries(List<GalleryBean> galleries) {
		
		galleryView.setGalleries(galleries);
	}
	
	public void setBranches(List<BranchBean> branches) {
		
		branchView.setBranches(branches);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void show() {
		
		goToTop();
		
		fadeOut();
		
		hideDisplayLinks();
		
		logoImg.setUrl("/image?id=" + institution.getImageId());
		nameLb.setText(institution.getName());
		addressLb.setText(institution.getAddress());
		phoneLb.setText(institution.getTelephone());
		emailLb.setText(institution.getEmail());
		websiteLb.setText(institution.getWebsite());
		facebookLb.setText(institution.getFacebook());
		twitterLb.setText(institution.getTwitter());
		
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
		setView(locationView);
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
	
	public void setWebsiteClickHandler(ClickHandler handler) {
		
		websiteLb.addClickHandler(handler);
	}
	
	public void setFacebookClickHandler(ClickHandler handler) {
		
		facebookLb.addClickHandler(handler);
	}
	
	public void setTwitterClickHandler(ClickHandler handler) {
		
		twitterLb.addClickHandler(handler);
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