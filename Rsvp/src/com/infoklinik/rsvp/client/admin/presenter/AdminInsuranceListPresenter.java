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
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInsuranceListView;
import com.infoklinik.rsvp.client.admin.view.AdminInsuranceListView;
import com.infoklinik.rsvp.client.main.view.ConfirmDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.InsuranceServiceAsync;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.InsuranceSearchBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminInsuranceListView.class)
public class AdminInsuranceListPresenter extends LazyPresenter<IAdminInsuranceListView, AdminEventBus> {
	
	@Inject
	private InsuranceServiceAsync insuranceServiceAsync;
	
	List<GenericBean<InsuranceBean>> genericBeans;
	
	@Override
	public void bindView() {
		
		view.setSearchBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				getInsurances();
			}
		});
		
		view.setAddBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addInsurance();
			}
		});
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				view.hide();
			}
		});
	}
	
	public void onLoadInsurance() {
		
		view.show();
	}
	
	public void onReloadInsurance() {
		
		view.refresh();
	}
	
	private void getInsurances() {
		
		InsuranceSearchBean insuranceSearchBean = view.getInsuranceSearchBean();
		
		ProgressDlg.show();
		insuranceServiceAsync.getInsurances(insuranceSearchBean, new AsyncCallback<List<InsuranceBean>>() {
			
			@Override
			public void onSuccess(List<InsuranceBean> result) {
				
				genericBeans = new ArrayList<GenericBean<InsuranceBean>>();
				
				for (InsuranceBean insuranceBean : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					
					GenericBean<InsuranceBean> genericBean = new GenericBean<InsuranceBean>(insuranceBean, handlerMgr);
					
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
	
	private ClickHandler getUpdateHandler(final GenericBean<InsuranceBean> genericBean) {
		
		ClickHandler detailHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				InsuranceBean insuranceBean = genericBean.getBean();
				
				eventBus.updateInsurance(insuranceBean);
			}
		};
		
		return detailHandler;
	}
	
	private ClickHandler getDeleteHandler(final GenericBean<InsuranceBean> genericBean) {
		
		ClickHandler deleteHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				InsuranceBean insuranceBean = genericBean.getBean();
				
				String confirm = "Hapus asuransi \"" + insuranceBean.getName() + "\" ?";
				
				ConfirmDlg.confirm(confirm, new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						deleteInsurance(genericBean);
					}
				});
			}
		};
		
		return deleteHandler;
	}
	
	private void deleteInsurance(final GenericBean<InsuranceBean> genericBean) {
		
		InsuranceBean insuranceBean = genericBean.getBean();
		
		ProgressDlg.show();
		insuranceServiceAsync.deleteInsurance(insuranceBean, new AsyncCallback<InsuranceBean>() {
			
			@Override
			public void onSuccess(InsuranceBean insuranceBean) {
				
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
