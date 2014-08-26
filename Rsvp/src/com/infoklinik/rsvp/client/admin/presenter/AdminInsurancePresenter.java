package com.infoklinik.rsvp.client.admin.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInsuranceView;
import com.infoklinik.rsvp.client.admin.view.AdminInsuranceView;
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
	
	@Override
	public void bindView() {

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
	
	public void onAddInsurance() {
		
		isAdd = true;
		view.setInsuranceBean(new InsuranceBean());
		view.show();
	}
	
	public void onUpdateInsurance(InsuranceBean insuranceBean) {
		
		isAdd = false;
		view.setInsuranceBean(insuranceBean);
		view.show();
	}
	
	private void addServiceReference() {
		
		InsuranceBean insuranceBean = view.getInsuranceBean();
		
		ProgressDlg.show();
		insuranceService.addInsurance(insuranceBean, new AsyncCallback<InsuranceBean>() {
			
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
	
	private void updateServiceReference() {
		
		InsuranceBean insuranceBean = view.getInsuranceBean();
		
		ProgressDlg.show();
		insuranceService.updateInsurance(insuranceBean, new AsyncCallback<InsuranceBean>() {
			
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
}
