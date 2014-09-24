package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminUserProfileListView;
import com.infoklinik.rsvp.client.admin.view.AdminUserProfileListView;
import com.infoklinik.rsvp.client.main.view.ConfirmDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.UserProfileServiceAsync;
import com.infoklinik.rsvp.shared.UserProfileBean;
import com.infoklinik.rsvp.shared.UserProfileSearchBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminUserProfileListView.class)
public class AdminUserProfileListPresenter extends LazyPresenter<IAdminUserProfileListView, AdminEventBus> {
	
	@Inject
	private UserProfileServiceAsync userProfileService;
	
	List<GenericBean<UserProfileBean>> genericBeans;
	
	@Override
	public void bindView() {
		
		view.setSearchBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				getUserProfiles();
			}
		});
		
		view.setAddBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addUserProfile();
			}
		});
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				view.hide();
			}
		});
	}
	
	public void onLoadUserProfile() {
		
		view.show();
	}
	
	public void onReloadUserProfile() {
		
		view.refresh();
	}
	
	private void getUserProfiles() {
		
		UserProfileSearchBean userProfileSearch = view.getUserProfileSearch();
		
		ProgressDlg.show();
		userProfileService.getUserProfiles(userProfileSearch, new AsyncCallback<List<UserProfileBean>>() {
			
			@Override
			public void onSuccess(List<UserProfileBean> result) {
				
				genericBeans = new ArrayList<GenericBean<UserProfileBean>>();
				
				for (UserProfileBean userProfileBean : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					
					GenericBean<UserProfileBean> genericBean = new GenericBean<UserProfileBean>(userProfileBean, handlerMgr);
					
					ClickHandler updateHandler = getUpdateHandler(genericBean);
					ClickHandler deleteHandler = getDeleteHandler(genericBean);
					
					handlerMgr.setUpdateHandler(updateHandler);
					handlerMgr.setDeleteHandler(deleteHandler);
					
					genericBeans.add(genericBean);
				}
				
				view.setList(genericBeans);
				ProgressDlg.hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {

				ProgressDlg.failure();
			}
		});
	}
	
	private ClickHandler getUpdateHandler(final GenericBean<UserProfileBean> genericBean) {
		
		ClickHandler updateHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				UserProfileBean userProfileBean = genericBean.getBean();
				
				eventBus.updateUserProfile(userProfileBean);
			}
		};
		
		return updateHandler;
	}
	
	private ClickHandler getDeleteHandler(final GenericBean<UserProfileBean> genericBean) {
		
		ClickHandler deleteHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				UserProfileBean userProfileBean = genericBean.getBean();
				
				String confirm = "Hapus wilayah \"" + userProfileBean.getName() + "\" ?";
				
				ConfirmDlg.confirm(confirm, new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						deleteUserProfile(genericBean);
					}
				});
			}
		};
		
		return deleteHandler;
	}
	
	private void deleteUserProfile(final GenericBean<UserProfileBean> genericBean) {
		
		UserProfileBean userProfile = genericBean.getBean();
		
		ProgressDlg.show();
		userProfileService.deleteUserProfile(userProfile, new AsyncCallback<UserProfileBean>() {
			
			@Override
			public void onSuccess(UserProfileBean userProfileBean) {
				
				ProgressDlg.success();
				view.remove(genericBean);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure();
			}
		});
	}
}
