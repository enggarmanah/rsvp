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
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminCityView;
import com.infoklinik.rsvp.client.admin.view.AdminCityView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CityServiceAsync;
import com.infoklinik.rsvp.shared.CityBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminCityView.class)
public class AdminCityPresenter extends LazyPresenter<IAdminCityView, AdminEventBus> {
	
	private boolean isAdd = true;
	
	@Inject
	private CityServiceAsync cityService;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (isValidated()) { 
					if (isAdd) {
						addServiceReference();
					} else {
						updateServiceReference();
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
	
	public void onAddCity() {
		
		isAdd = true;
		view.setCity(new CityBean());
		view.show();
	}
	
	public void onUpdateCity(CityBean cityBean) {
		
		isAdd = false;
		view.setCity(cityBean);
		view.show();
	}
	
	private void addServiceReference() {
		
		CityBean city = view.getCity();
		city.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		cityService.addCity(city, new AsyncCallback<CityBean>() {
			
			@Override
			public void onSuccess(CityBean result) {
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
		
		CityBean city = view.getCity();
		city.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		cityService.updateCity(city, new AsyncCallback<CityBean>() {
			
			@Override
			public void onSuccess(CityBean result) {
				view.hide();
				eventBus.reloadCity();
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
		
		CityBean city = view.getCity();
		
		if (ClientUtil.isEmpty(city.getName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_CITY_NAME_EMPTY);
		}
		
		return isValidated;
	}
}
