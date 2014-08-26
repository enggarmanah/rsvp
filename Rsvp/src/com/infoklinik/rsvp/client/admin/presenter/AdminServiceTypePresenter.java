package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminServiceTypeView;
import com.infoklinik.rsvp.client.admin.view.AdminServiceTypeView;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.MasterCodeServiceAsync;
import com.infoklinik.rsvp.client.rpc.ServiceTypeServiceAsync;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminServiceTypeView.class)
public class AdminServiceTypePresenter extends LazyPresenter<IAdminServiceTypeView, AdminEventBus> {
	
	private boolean isAdd = true;
	
	@Inject
	private ServiceTypeServiceAsync serviceTypeService;
	
	@Inject
	private MasterCodeServiceAsync masterCodeService;
	
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
	
	public void onAddServiceType() {
		
		isAdd = true;
		view.setServiceTypeBean(new ServiceTypeBean());
		view.show();
	}
	
	public void onUpdateServiceType(ServiceTypeBean serviceTypeBean) {
		
		isAdd = false;
		view.setServiceTypeBean(serviceTypeBean);
		view.show();
	}
	
	private void setCategories() {
		
		ProgressDlg.show();
		
		masterCodeService.getMasterCodes(MasterCodeBean.SERVICE_CATEGORY, new AsyncCallback<List<MasterCodeBean>>() {
			
			@Override
			public void onSuccess(List<MasterCodeBean> result) {
				List<String> categories = new ArrayList<String>();
				for (MasterCodeBean masterCode : result) {
					categories.add(masterCode.getValue());
				}
				view.setCategories(categories);
				ProgressDlg.hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure(Message.ERR_COMMON_LOAD_FAILED);
			}
		});
	}
	
	private void addServiceReference() {
		
		ServiceTypeBean serviceTypeBean = view.getServiceTypeBean();
		
		ProgressDlg.show();
		serviceTypeService.addServiceType(serviceTypeBean, new AsyncCallback<ServiceTypeBean>() {
			
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
		serviceTypeService.updateServiceType(serviceTypeBean, new AsyncCallback<ServiceTypeBean>() {
			
			@Override
			public void onSuccess(ServiceTypeBean result) {
				view.hide();
				eventBus.reloadServiceType();
				ProgressDlg.success();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
}
