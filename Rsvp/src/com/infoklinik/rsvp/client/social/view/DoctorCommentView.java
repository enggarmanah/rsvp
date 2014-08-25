package com.infoklinik.rsvp.client.social.view;

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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.social.presenter.interfaces.IDoctorCommentView;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;
import com.infoklinik.rsvp.shared.SocialUser;

public class DoctorCommentView extends BaseView implements IDoctorCommentView {
	
	private DialogBox dialogBox;
	
	@UiField
	Label nameLb;
	
	@UiField
	Label specialityLb;
	
	@UiField
	Image profileImg;
		
	@UiField
	VerticalPanel commentsPanel;
	
	@UiField
	ScrollPanel scrollPanel;
	
	@UiField
	Button addBtn;
	
	@UiField
	Button okBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, DoctorCommentView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	ServiceTypeSearchBean serviceTypeSearchBean = new ServiceTypeSearchBean();
	
	DoctorBean doctor;
	SocialUser socialUser;
	
	List<GenericBean<CommentBean>> comments;
	
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
	
	public void setSocialUser(SocialUser socialUser) {
		
		this.socialUser = socialUser;
	}
	
	public void setComments(List<GenericBean<CommentBean>> comments) {
		
		this.comments = comments;
	}
	
	public void addComment(GenericBean<CommentBean> comment) {
		
		comments.add(0, comment);
		commentsPanel.clear();
		
		for (GenericBean<CommentBean> cmt : comments) {
		
			CommentItemView commentView = new CommentItemView(socialUser, cmt);
			commentsPanel.add(commentView.asWidget());
		}
		
		scrollPanel.scrollToTop();
	}
	
	public void deleteComment(GenericBean<CommentBean> comment) {
		
		int index = comments.indexOf(comment);
		comments.remove(comment);
		commentsPanel.remove(index);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setAddBtnClickHandler(ClickHandler handler) {
		
		addBtn.addClickHandler(handler);
	}
	
	public void show() {
		
		fadeOut();
		
		profileImg.setUrl("images/doctor.jpg");
		
		if (doctor.getImageId() != null) {
			profileImg.setUrl("/image?id=" + doctor.getImageId());
		}
		
		nameLb.setText(doctor.getName());
		specialityLb.setText(doctor.getSpeciality().getDescription());
		
		commentsPanel.clear();
		
		for (GenericBean<CommentBean> cmt : comments) {
			
			CommentItemView commentItemView = new CommentItemView(socialUser, cmt);			
			commentsPanel.add(commentItemView.asWidget());
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