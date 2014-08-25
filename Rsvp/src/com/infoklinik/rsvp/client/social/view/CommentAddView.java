package com.infoklinik.rsvp.client.social.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.social.presenter.interfaces.ICommentAddView;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;
import com.infoklinik.rsvp.shared.SocialUser;

public class CommentAddView extends BaseView implements ICommentAddView {
	
	private DialogBox dialogBox;
	
	@UiField
	Image fbProfileImg;
	
	@UiField
	TextArea textTa;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, CommentAddView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	ServiceTypeSearchBean serviceTypeSearchBean = new ServiceTypeSearchBean();
	
	SocialUser socialUser;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public CommentBean getComment() {
		
		CommentBean comment = new CommentBean();
		
		comment.setFbId(socialUser.getId());
		comment.setFbName(socialUser.getName());
		comment.setText(textTa.getText());
		
		comment.setUpdateBy(ClientUtil.getUser().getName());
		
		return comment;
	}
	
	public Widget getRootWidget() {
		
		return dialogBox;
	}
	
	public void setSocialUser(SocialUser socialUser) {
		
		this.socialUser = socialUser;
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
	
	public void show() {
		
		fadeOut();
		clear();
		
		fbProfileImg.setUrl("http://graph.facebook.com/"+ socialUser.getId() +"/picture");
		
		goToTop();
		
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft() + 13, 110);
		dialogBox.show();
		
		fadeIn();
	}
	
	public void clear() {
		
		textTa.setText(Constant.EMPTY_STRING);
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