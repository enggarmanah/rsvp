package com.infoklinik.rsvp.client.service.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.rpc.ServiceServiceAsync;
import com.infoklinik.rsvp.client.service.ServiceEventBus;
import com.infoklinik.rsvp.client.service.presenter.interfaces.IServiceInfoView;
import com.infoklinik.rsvp.client.service.view.ServiceInfoView;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = ServiceInfoView.class)
public class ServiceInfoPresenter extends LazyPresenter<IServiceInfoView, ServiceEventBus> {
	
	InstitutionBean institution;
	ServiceBean service;
	
	@Inject
	ServiceServiceAsync serviceService;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	private void initBtnHandler() {
		
		view.setBookBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
				eventBus.loadReservationService(service);
			}
		});

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	public void onLoadServiceInfo(ServiceBean service) {
		
		Window.scrollTo (0,0);
			
		this.service = service;
		this.institution = service.getInstitution();
		
		view.setService(service);
		view.setInstitution(institution);
		view.show();
		
		if (!ClientUtil.isAdminUser) {
			increaseViewCount();
		}
	}
	
	private void increaseViewCount() {
		
		serviceService.increaseViewCount(service.getId(), new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {}
			
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}
