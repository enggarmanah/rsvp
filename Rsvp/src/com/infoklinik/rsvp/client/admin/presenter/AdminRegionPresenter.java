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
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminRegionView;
import com.infoklinik.rsvp.client.admin.view.AdminRegionView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.RegionServiceAsync;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.RegionBean;
import com.infoklinik.rsvp.shared.SearchSuggestion;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminRegionView.class)
public class AdminRegionPresenter extends LazyPresenter<IAdminRegionView, AdminEventBus> {
	
	private boolean isAdd = true;
	
	@Inject
	private RegionServiceAsync regionService;
	
	List<String> errorMessages;
	
	RegionBean region;
	
	@Override
	public void bindView() {
		
		view.setCitySelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				
				region = view.getRegion();
				
				SearchSuggestion suggestion = (SearchSuggestion) event.getSelectedItem();
				
				CityBean city = new CityBean();
				city.setId(Long.valueOf(suggestion.getValue()));
				city.setName(suggestion.getReplacementString());
				
				region.setCity(city);
				
				view.setRegion(region);
			}
		});
		
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
	
	public void onAddRegion() {
		
		isAdd = true;
		view.setRegion(new RegionBean());
		view.show();
	}
	
	public void onUpdateRegion(RegionBean regionBean) {
		
		isAdd = false;
		view.setRegion(regionBean);
		view.show();
	}
	
	private void addServiceReference() {
		
		RegionBean region = view.getRegion();
		region.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		regionService.addRegion(region, new AsyncCallback<RegionBean>() {
			
			@Override
			public void onSuccess(RegionBean result) {
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
		
		RegionBean region = view.getRegion();
		region.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		regionService.updateRegion(region, new AsyncCallback<RegionBean>() {
			
			@Override
			public void onSuccess(RegionBean result) {
				view.hide();
				eventBus.reloadRegion();
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
		
		RegionBean region = view.getRegion();
		
		if (ClientUtil.isEmpty(region.getName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INSURANCE_NAME_EMPTY);
		}
		
		return isValidated;
	}
}
