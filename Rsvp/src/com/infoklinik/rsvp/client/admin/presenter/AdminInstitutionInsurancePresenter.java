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
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionInsuranceView;
import com.infoklinik.rsvp.client.admin.view.AdminInstitutionInsuranceView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.rpc.InsuranceServiceAsync;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminInstitutionInsuranceView.class)
public class AdminInstitutionInsurancePresenter extends LazyPresenter<IAdminInstitutionInsuranceView, AdminEventBus> {
	
	@Inject
	private InsuranceServiceAsync insuranceServiceAsync;
	
	boolean isAdd = true;
	InsuranceBean insurance;
	List<String> errorMessages;
	
	@Override
	public void bindView() {

		initInsurances();
		
		initOkBtnClickHandler();
		initCancelBtnClickHandler();
	}
	
	public void onAddInstitutionInsurance() {
		
		isAdd = true;
		
		view.setInsurance(new InsuranceBean());
		view.show();
	}
	
	public void onUpdateInstitutionInsurance(InsuranceBean insuranceBean) {
		
		isAdd = false;
		
		view.setInsurance(insuranceBean);
		view.show();
	}
	
	private void initOkBtnClickHandler() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				insurance = view.getInsurance();
				
				insurance.setUpdateBy(ClientUtil.getUser().getName());
				
				if (isValidated()) {
				
					insurance.setUpdateBy(ClientUtil.getUser().getName());
					
					if (isAdd) {
						eventBus.addInstInsurance(insurance);
					} else {
						eventBus.updateInstInsurance(insurance);
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
	
	private void initInsurances() {
		
		insuranceServiceAsync.getInsurances(new AsyncCallback<List<InsuranceBean>>() {
			
			@Override
			public void onSuccess(List<InsuranceBean> result) {
				
				view.setInsurances(result);
				//view.setInsurance(insurance);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private boolean isValidated() {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		if (ClientUtil.isEmpty(insurance.getId())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_INSURANCE_EMPTY);
		}
		
		return isValidated;
	}
}
