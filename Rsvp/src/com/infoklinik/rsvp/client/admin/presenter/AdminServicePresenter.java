package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminServiceView;
import com.infoklinik.rsvp.client.admin.view.AdminServiceView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.rpc.ServiceTypeServiceAsync;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminServiceView.class)
public class AdminServicePresenter extends LazyPresenter<IAdminServiceView, AdminEventBus> {
	
	@Inject
	private ServiceTypeServiceAsync serviceTypeServiceAsync;
	
	boolean isAdd = true;
	ServiceBean service;
	List<String> errorMessages;
	
	@Override
	public void bindView() {

		initServiceTypes();
		
		initPromoChangeHandler();
		initOkBtnClickHandler();
		initCancelBtnClickHandler();
	}
	
	public void onAddService() {
		
		isAdd = true;
		
		view.setService(new ServiceBean());
		view.show();
	}
	
	public void onUpdateService(ServiceBean serviceBean) {
		
		isAdd = false;
		
		view.setService(serviceBean);
		view.show();
	}
	
	private void initPromoChangeHandler() {
		
		view.setPromoChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				service = view.getService();
				view.setService(service);
			}
		});
	}
	
	private void initOkBtnClickHandler() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				service = view.getService();
				
				service.setUpdateBy(ClientUtil.getUser().getName());
				
				if (isValidated()) {
				
					service.setUpdateBy(ClientUtil.getUser().getName());
					
					if (isAdd) {
						eventBus.addInstService(service);
					} else {
						eventBus.updateInstService(service);
					}
						
					view.hide();
					
				} else {
					NotificationDlg.error(errorMessages);
				}
			}
		});
		
	}
	
	private void initCancelBtnClickHandler() {
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	private void initServiceTypes() {
		
		serviceTypeServiceAsync.getServiceTypes(new ServiceTypeSearchBean(), new AsyncCallback<List<ServiceTypeBean>>() {
			
			@Override
			public void onSuccess(List<ServiceTypeBean> result) {
				view.setServiceTypes(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private boolean isValidated() {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		if (ClientUtil.isEmpty(service.getName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_SERVICE_NAME_EMPTY);
		}
		if (ClientUtil.isEmpty(service.getServiceType())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_SERVICE_TYPE_EMPTY);
		}
		if (ClientUtil.isEmpty(service.getPrice())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_SERVICE_PRICE_EMPTY);
		}
		if (ClientUtil.isEmpty(service.getDescription())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_SERVICE_DESC_EMPTY);
		}
		
		return isValidated;
	}
}
