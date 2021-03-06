package com.infoklinik.rsvp.client.social.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.social.presenter.interfaces.IServiceLikeView;
import com.infoklinik.rsvp.shared.LikeBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;
import com.infoklinik.rsvp.shared.SocialUser;

public class ServiceLikeView extends BaseView implements IServiceLikeView {
	
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
	FlowPanel likesPanel;
	
	@UiField
	ScrollPanel scrollPanel;
	
	@UiField
	Button likeBtn;
	
	@UiField
	Button okBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, ServiceLikeView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	ServiceTypeSearchBean serviceTypeSearchBean = new ServiceTypeSearchBean();
	
	ServiceBean service;
	InstitutionBean institution;
	SocialUser socialUser;
	
	List<GenericBean<LikeBean>> likes;
	
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
	
	public void setService(ServiceBean service) {
		
		this.service = service;
		this.institution = service.getInstitution();
	}
	
	public void setSocialUser(SocialUser socialUser) {
		
		this.socialUser = socialUser;
	}
	
	public void setLikes(List<GenericBean<LikeBean>> likes) {
		
		this.likes = likes;
	}
	
	public void addLike(GenericBean<LikeBean> like) {
		
		likes.add(0, like);
		likesPanel.clear();
		
		for (GenericBean<LikeBean> cmt : likes) {
		
			LikeItemView likeView = new LikeItemView(socialUser, cmt);
			likesPanel.add(likeView.asWidget());
		}
		
		scrollPanel.scrollToTop();
	}
	
	public void deleteLike(GenericBean<LikeBean> like) {
		
		int index = likes.indexOf(like);
		likes.remove(like);
		likesPanel.remove(index);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setLikeBtnClickHandler(ClickHandler handler) {
		
		likeBtn.addClickHandler(handler);
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
	
	public void setLike(boolean isLike) {
		
		if (isLike) {
			likeBtn.setText("Like");
		} else {
			likeBtn.setText("Unlike");
		}
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
		
		goToTop();
		
		fadeOut();
		
		hideDisplayLinks();
		
		logoImg.setUrl("/image?id=" + institution.getImageId());
		instNameLb.setText(institution.getName());
		addressLb.setText(institution.getAddress());
		phoneLb.setText(institution.getTelephone());
		emailLb.setText(institution.getEmail());
		websiteLb.setText(institution.getWebsite());
		
		likesPanel.clear();
		
		for (GenericBean<LikeBean> cmt : likes) {
			
			LikeItemView likeItemView = new LikeItemView(socialUser, cmt);			
			likesPanel.add(likeItemView.asWidget());
		}
		
		goToTop();
		
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