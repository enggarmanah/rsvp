package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminStreetView;
import com.infoklinik.rsvp.client.admin.view.AdminStreetView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.StreetServiceAsync;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.StreetBean;
import com.infoklinik.rsvp.shared.SearchSuggestion;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminStreetView.class)
public class AdminStreetPresenter extends LazyPresenter<IAdminStreetView, AdminEventBus> {
	
	private boolean isAdd = true;
	
	@Inject
	private StreetServiceAsync streetService;
	
	List<String> errorMessages;
	
	StreetBean street;
	StreetBean orgStreet;
	
	@Override
	public void bindView() {
		
		view.setCitySelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				
				street = view.getStreet();
				
				SearchSuggestion suggestion = (SearchSuggestion) event.getSelectedItem();
				
				CityBean city = new CityBean();
				city.setId(Long.valueOf(suggestion.getValue()));
				city.setName(suggestion.getReplacementString());
				
				street.setCity(city);
				
				view.setStreet(street);
			}
		});
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (isValidated()) { 
					if (isAdd) {
						addStreet();
					} else {
						updateStreet();
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
	
	public void onAddStreet() {
		
		orgStreet = new StreetBean();
		street = new StreetBean();
		
		isAdd = true;
		view.setStreet(new StreetBean());
		view.show();
	}
	
	public void onUpdateStreet(StreetBean street) {
		
		orgStreet = street;
		
		this.street = new StreetBean();
		this.street.setBean(orgStreet);
		
		isAdd = false;
		view.setStreet(this.street);
		view.show();
	}
	
	private void addStreet() {
		
		street = view.getStreet();
		street.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		streetService.addStreet(street, new AsyncCallback<StreetBean>() {
			
			@Override
			public void onSuccess(StreetBean result) {
				view.hide();
				ProgressDlg.success();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
	
	private void updateStreet() {
		
		street = view.getStreet();
		street.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		streetService.updateStreet(street, new AsyncCallback<StreetBean>() {
			
			@Override
			public void onSuccess(StreetBean result) {
				
				street = result;
				orgStreet.setBean(street);
				
				view.hide();
				eventBus.reloadStreet();
				
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
		
		street = view.getStreet();
		
		if (ClientUtil.isEmpty(street.getName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_STREET_NAME_EMPTY);
		}
		
		if (street.getCity() == null) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_CITY_EMPTY);
		} 
		
		return isValidated;
	}
}
