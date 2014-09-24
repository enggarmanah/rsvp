package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminUserProfileView;
import com.infoklinik.rsvp.client.admin.view.AdminUserProfileView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.UserProfileServiceAsync;
import com.infoklinik.rsvp.shared.UserProfileBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminUserProfileView.class)
public class AdminUserProfilePresenter extends LazyPresenter<IAdminUserProfileView, AdminEventBus> {
	
	private boolean isAdd = true;
	
	@Inject
	private UserProfileServiceAsync userProfileService;
	
	List<String> errorMessages;
	
	UserProfileBean userProfile;
	UserProfileBean orgUserProfile;
	
	@Override
	public void bindView() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (isValidated()) { 
					if (isAdd) {
						addUserProfile();
					} else {
						updateUserProfile();
					}
				} else {
					NotificationDlg.error(errorMessages);
				}
			}
		});
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	public void onAddUserProfile() {
		
		orgUserProfile = new UserProfileBean();
		userProfile = new UserProfileBean();
		
		isAdd = true;
		view.setUserProfile(new UserProfileBean());
		view.show();
	}
	
	public void onUpdateUserProfile(UserProfileBean userProfile) {
		
		orgUserProfile = userProfile;
		
		this.userProfile = new UserProfileBean();
		this.userProfile.setBean(orgUserProfile);
		
		isAdd = false;
		view.setUserProfile(this.userProfile);
		view.show();
	}
	
	private void addUserProfile() {
		
		userProfile = view.getUserProfile();
		userProfile.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		userProfileService.addUserProfile(userProfile, new AsyncCallback<UserProfileBean>() {
			
			@Override
			public void onSuccess(UserProfileBean result) {
				view.hide();
				ProgressDlg.success();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
	
	private void updateUserProfile() {
		
		userProfile = view.getUserProfile();
		userProfile.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		userProfileService.updateUserProfile(userProfile, new AsyncCallback<UserProfileBean>() {
			
			@Override
			public void onSuccess(UserProfileBean result) {
				
				userProfile = result;
				orgUserProfile.setBean(userProfile);
				
				view.hide();
				eventBus.reloadUserProfile();
				
				ProgressDlg.success();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
	
	private boolean isValidated() {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		userProfile = view.getUserProfile();
		
		if (ClientUtil.isEmpty(userProfile.getName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_USER_NAME_EMPTY);
		}
		
		if (ClientUtil.isEmpty(userProfile.getEmail())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_USER_EMAIL_EMPTY);
		}
		
		return isValidated;
	}
}
