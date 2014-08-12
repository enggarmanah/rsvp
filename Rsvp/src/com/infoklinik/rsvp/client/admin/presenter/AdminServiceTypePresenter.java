package com.infoklinik.rsvp.client.admin.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminServiceTypeView;
import com.infoklinik.rsvp.client.admin.view.AdminServiceTypeView;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.ServiceTypeServiceAsync;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminServiceTypeView.class)
public class AdminServiceTypePresenter extends LazyPresenter<IAdminServiceTypeView, AdminEventBus> {
	
	private boolean isAdd = true;
	
	@Inject
	private ServiceTypeServiceAsync serviceTypeServiceAsync;
	
	@Override
	public void bindView() {

		setCategories();
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (isAdd) {
					addServiceReference();
				} else {
					updateServiceReference();
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
	
	public void onAddServiceReference() {
		
		isAdd = true;
		view.setServiceTypeBean(new ServiceTypeBean());
		view.show();
	}
	
	public void onUpdateServiceReference(ServiceTypeBean serviceTypeBean) {
		
		isAdd = false;
		view.setServiceTypeBean(serviceTypeBean);
		view.show();
	}
	
	private void setCategories() {
		
		serviceTypeServiceAsync.getCategories(new AsyncCallback<List<String>>() {
			
			@Override
			public void onSuccess(List<String> result) {
				view.setCategories(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void addServiceReference() {
		
		ServiceTypeBean serviceTypeBean = view.getServiceTypeBean();
		
		ProgressDlg.show();
		serviceTypeServiceAsync.addServiceType(serviceTypeBean, new AsyncCallback<ServiceTypeBean>() {
			
			@Override
			public void onSuccess(ServiceTypeBean result) {
				view.hide();
				ProgressDlg.success();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
	
	private void updateServiceReference() {
		
		ServiceTypeBean serviceTypeBean = view.getServiceTypeBean();
		
		ProgressDlg.show();
		serviceTypeServiceAsync.updateServiceType(serviceTypeBean, new AsyncCallback<ServiceTypeBean>() {
			
			@Override
			public void onSuccess(ServiceTypeBean result) {
				view.hide();
				eventBus.reloadServiceReference();
				ProgressDlg.success();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
}
