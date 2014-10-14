package com.infoklinik.rsvp.client.service.view;

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
import com.infoklinik.rsvp.client.service.presenter.interfaces.IServiceInfoView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;

public class ServiceInfoView extends BaseView implements IServiceInfoView {
	
	private DialogBox dialogBox;
	
	@UiField
	Label instNameLb;
	
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
	Image logoImg;
	
	@UiField
	Image serviceImg;
	
	@UiField
	Label serviceNameLb;
	
	@UiField
	Label priceLb;
	
	@UiField
	Label descriptionLb;
	
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
	Button bookBtn;
	
	@UiField
	Button okBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, ServiceInfoView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	ServiceTypeSearchBean serviceTypeSearchBean = new ServiceTypeSearchBean();
	
	InstitutionBean institution;
	ServiceBean service;
	
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
		
		hideDisplayLinks();
	}
	
	public void setService(ServiceBean service) {
		
		this.service = service;
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
	
	public void setBookBtnClickHandler(ClickHandler handler) {
		
		bookBtn.addClickHandler(handler);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
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
	
	public void show() {
		
		fadeOut();
		
		logoImg.setUrl("/image?id=" + institution.getImageId());
		instNameLb.setText(institution.getName());
		addressLb.setText(institution.getAddress());
		phoneLb.setText(institution.getTelephone());
		emailLb.setText(institution.getEmail());
		websiteLb.setText(institution.getWebsite());
		
		serviceImg.setUrl("/image?id=" + service.getImageId());
		serviceNameLb.setText(service.getServiceType().getName() + " - " + service.getName());
		priceLb.setText("Harga Promo : " + service.getPrice() + ". Harga Normal : " + service.getPromoPrice());
		descriptionLb.setText(service.getDescription());
		
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