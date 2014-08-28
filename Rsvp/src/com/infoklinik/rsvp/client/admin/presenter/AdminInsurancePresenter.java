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
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInsuranceView;
import com.infoklinik.rsvp.client.admin.view.AdminInsuranceView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.InsuranceServiceAsync;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminInsuranceView.class)
public class AdminInsurancePresenter extends LazyPresenter<IAdminInsuranceView, AdminEventBus> {
	
	private boolean isAdd = true;
	
	@Inject
	private InsuranceServiceAsync insuranceService;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (isValidated()) { 
					if (isAdd) {
						addInsurance();
					} else {
						updateInsurance();
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
	
	public void onAddInsurance() {
		
		isAdd = true;
		view.setInsurance(new InsuranceBean());
		view.show();
	}
	
	public void onUpdateInsurance(InsuranceBean insuranceBean) {
		
		isAdd = false;
		view.setInsurance(insuranceBean);
		view.show();
	}
	
	private void addInsurance() {
		
		InsuranceBean insurance = view.getInsurance();
		insurance.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		insuranceService.addInsurance(insurance, new AsyncCallback<InsuranceBean>() {
			
			@Override
			public void onSuccess(InsuranceBean result) {
				view.hide();
				ProgressDlg.success();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
	
	private void updateInsurance() {
		
		InsuranceBean insurance = view.getInsurance();
		insurance.setUpdateBy(ClientUtil.getUser().getName());
		
		ProgressDlg.show();
		insuranceService.updateInsurance(insurance, new AsyncCallback<InsuranceBean>() {
			
			@Override
			public void onSuccess(InsuranceBean result) {
				view.hide();
				eventBus.reloadInsurance();
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
		
		InsuranceBean insurance = view.getInsurance();
		
		if (ClientUtil.isEmpty(insurance.getName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INSURANCE_NAME_EMPTY);
		}
		
		return isValidated;
	}
}
