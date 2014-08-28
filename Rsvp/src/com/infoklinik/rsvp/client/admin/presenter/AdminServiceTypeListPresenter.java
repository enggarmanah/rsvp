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
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminServiceTypeListView;
import com.infoklinik.rsvp.client.admin.view.AdminServiceTypeListView;
import com.infoklinik.rsvp.client.main.view.ConfirmDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.ServiceTypeServiceAsync;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminServiceTypeListView.class)
public class AdminServiceTypeListPresenter extends LazyPresenter<IAdminServiceTypeListView, AdminEventBus> {
	
	@Inject
	private ServiceTypeServiceAsync serviceTypeServiceAsync;
	
	List<GenericBean<ServiceTypeBean>> genericBeans;
	
	@Override
	public void bindView() {
		
		view.setSearchBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				getServiceTypes();
			}
		});
		
		view.setAddBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addServiceType();
			}
		});
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				view.hide();
			}
		});
	}
	
	public void onLoadServiceType() {
		
		view.show();
	}
	
	public void onReloadServiceType() {
		
		view.refresh();
	}
	
	private void getServiceTypes() {
		
		ServiceTypeSearchBean serviceTypeSearchBean = view.getServiceTypeSearchBean();
		
		ProgressDlg.show();
		serviceTypeServiceAsync.getServiceTypes(serviceTypeSearchBean, new AsyncCallback<List<ServiceTypeBean>>() {
			
			@Override
			public void onSuccess(List<ServiceTypeBean> result) {
				
				genericBeans = new ArrayList<GenericBean<ServiceTypeBean>>();
				
				for (ServiceTypeBean serviceTypeBean : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					
					GenericBean<ServiceTypeBean> genericBean = new GenericBean<ServiceTypeBean>(serviceTypeBean, handlerMgr);
					
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
	
	private ClickHandler getUpdateHandler(final GenericBean<ServiceTypeBean> genericBean) {
		
		ClickHandler updateHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				ServiceTypeBean serviceTypeBean = genericBean.getBean();
				
				eventBus.updateServiceType(serviceTypeBean);
			}
		};
		
		return updateHandler;
	}
	
	private ClickHandler getDeleteHandler(final GenericBean<ServiceTypeBean> genericBean) {
		
		ClickHandler deleteHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				ServiceTypeBean serviceType = genericBean.getBean();
				
				String confirm = "Hapus referensi layanan \"" + serviceType.getName() + "\" ?";
				
				ConfirmDlg.confirm(confirm, new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						deleteServiceType(genericBean);
					}
				});
			}
		};
		
		return deleteHandler;
	}
	
	private void deleteServiceType(final GenericBean<ServiceTypeBean> genericBean) {
		
		ServiceTypeBean serviceType = genericBean.getBean();
		
		ProgressDlg.show();
		serviceTypeServiceAsync.deleteServiceType(serviceType, new AsyncCallback<ServiceTypeBean>() {
			
			@Override
			public void onSuccess(ServiceTypeBean serviceTypeBean) {
				
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
