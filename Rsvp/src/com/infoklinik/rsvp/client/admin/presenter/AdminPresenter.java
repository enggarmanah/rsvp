package com.infoklinik.rsvp.client.admin.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminView;
import com.infoklinik.rsvp.client.admin.view.AdminView;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CommonServiceAsync;
import com.infoklinik.rsvp.shared.UserBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminView.class)
public class AdminPresenter extends LazyPresenter<IAdminView, AdminEventBus> {
	
	@Inject
	CommonServiceAsync commonService;
	
	@Override
	public void bindView() {
		
		view.setInstitutionLinkClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.addInstitution();
			}
		});
		
		view.setDoctorLinkClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.addDoctor();
			}
		});
		
		view.setServiceTypeLinkClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.loadServiceType();
			}
		});
		
		view.setInsuranceLinkClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.loadInsurance();
			}
		});
		
		view.setLogoutLinkClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				logout();
			}
		});
	}
	
	public void onLoadAdmin() {
		
		eventBus.setRightPanel(view.asWidget());
	}
	
	private void logout() {
		
		ProgressDlg.show();
		commonService.logout(new AsyncCallback<UserBean>() {
			
			@Override
			public void onSuccess(UserBean userBean) {
				ProgressDlg.success();
				Window.Location.replace(userBean.getLogoutUrl());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
}
