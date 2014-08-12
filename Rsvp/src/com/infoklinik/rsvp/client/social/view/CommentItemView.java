package com.infoklinik.rsvp.client.social.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.SocialUser;

public class CommentItemView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, CommentItemView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Image fbProfileImg;
	
	@UiField
	Label fbNameLb;
	
	@UiField
	Label dateLb;
	
	@UiField
	Label textLb;
	
	@UiField
	FocusPanel deletePanel;
	
	SocialUser socialUser;
	
	public CommentItemView(SocialUser socialUser, GenericBean<CommentBean> comment) {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		CommentBean cmt = comment.getBean();
		HandlerManager mgr = comment.getHandlerMgr();
		
		fbProfileImg.setUrl("http://graph.facebook.com/"+ cmt.getFbId() +"/picture");
		fbNameLb.setText(cmt.getFbName());
		dateLb.setText(ClientUtil.dateTimeToStr(cmt.getCreateDate()));
		textLb.setText(cmt.getText());
		
		if (socialUser != null && comment.getBean().getFbId().equals(socialUser.getId())) {
			deletePanel.setVisible(true);
			deletePanel.addClickHandler(mgr.getDeleteHandler());
		} else {
			deletePanel.setVisible(false);
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
