package com.infoklinik.rsvp.client.social.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.LikeBean;
import com.infoklinik.rsvp.shared.SocialUser;

public class LikeItemView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, LikeItemView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Image fbProfileImg;
	
	SocialUser socialUser;
	
	public LikeItemView(SocialUser socialUser, GenericBean<LikeBean> like) {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		LikeBean cmt = like.getBean();
		
		fbProfileImg.setUrl("http://graph.facebook.com/"+ cmt.getFbId() +"/picture");
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
