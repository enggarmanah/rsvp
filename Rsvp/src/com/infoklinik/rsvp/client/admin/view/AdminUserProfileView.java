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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminUserProfileView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.UserProfileBean;

public class AdminUserProfileView extends BaseView implements IAdminUserProfileView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminUserProfileView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	TextBox nameTb;
	
	@UiField
	TextBox emailTb;
	
	@UiField
	ListBox userTypeLb;
	
	@UiField
	ListBox statusLb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	DialogBox dialogBox;
	
	List<UserProfileBean> list;
	
	UserProfileBean userProfile;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
		
		userTypeLb.addItem(Constant.OPERATION_DESC, Constant.OPERATION);
		userTypeLb.addItem(Constant.SUPERUSER_DESC, Constant.SUPERUSER);
		userTypeLb.setSelectedIndex(0);
		
		statusLb.addItem(Constant.ACTIVE_DESC, Constant.ACTIVE);
		statusLb.addItem(Constant.INACTIVE_DESC, Constant.INACTIVE);
		statusLb.setSelectedIndex(0);
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	public void show() {
		
		goToTop();
		
		dlgFadeOut();
		
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

	public UserProfileBean getUserProfile() {
		
		userProfile.setName(nameTb.getText());
		userProfile.setEmail(emailTb.getText());
		userProfile.setUserType(userTypeLb.getValue(userTypeLb.getSelectedIndex()));
		userProfile.setStatus(statusLb.getValue(statusLb.getSelectedIndex()));
		
		return userProfile;
	}
	
	public void setUserProfile(UserProfileBean userProfile) {
		
		this.userProfile = userProfile;
		
		if (userProfile.getId() == null) {
			dialogBox.setText("Tambah Pengguna Baru");
		} else {
			dialogBox.setText("Perubahan Data Pengguna");
		}
		
		nameTb.setText(userProfile.getName());
		emailTb.setText(userProfile.getEmail());
		ClientUtil.setSelectedIndex(userTypeLb, userProfile.getUserType());
		ClientUtil.setSelectedIndex(statusLb, userProfile.getStatus());
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
}
