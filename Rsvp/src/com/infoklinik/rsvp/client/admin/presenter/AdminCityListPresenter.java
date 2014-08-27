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
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminCityListView;
import com.infoklinik.rsvp.client.admin.view.AdminCityListView;
import com.infoklinik.rsvp.client.main.view.ConfirmDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CityServiceAsync;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminCityListView.class)
public class AdminCityListPresenter extends LazyPresenter<IAdminCityListView, AdminEventBus> {
	
	@Inject
	private CityServiceAsync cityServiceAsync;
	
	List<GenericBean<CityBean>> genericBeans;
	
	@Override
	public void bindView() {
		
		view.setSearchBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				getCitys();
			}
		});
		
		view.setAddBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addCity();
			}
		});
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				view.hide();
			}
		});
	}
	
	public void onLoadCity() {
		
		view.show();
	}
	
	public void onReloadCity() {
		
		view.refresh();
	}
	
	private void getCitys() {
		
		CitySearchBean citySearchBean = view.getCitySearch();
		
		ProgressDlg.show();
		cityServiceAsync.getCities(citySearchBean, new AsyncCallback<List<CityBean>>() {
			
			@Override
			public void onSuccess(List<CityBean> result) {
				
				genericBeans = new ArrayList<GenericBean<CityBean>>();
				
				for (CityBean cityBean : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					
					GenericBean<CityBean> genericBean = new GenericBean<CityBean>(cityBean, handlerMgr);
					
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
	
	private ClickHandler getUpdateHandler(final GenericBean<CityBean> genericBean) {
		
		ClickHandler detailHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				CityBean cityBean = genericBean.getBean();
				
				eventBus.updateCity(cityBean);
			}
		};
		
		return detailHandler;
	}
	
	private ClickHandler getDeleteHandler(final GenericBean<CityBean> genericBean) {
		
		ClickHandler deleteHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				CityBean cityBean = genericBean.getBean();
				
				String confirm = "Hapus asuransi \"" + cityBean.getName() + "\" ?";
				
				ConfirmDlg.confirm(confirm, new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						deleteCity(genericBean);
					}
				});
			}
		};
		
		return deleteHandler;
	}
	
	private void deleteCity(final GenericBean<CityBean> genericBean) {
		
		CityBean cityBean = genericBean.getBean();
		
		ProgressDlg.show();
		cityServiceAsync.deleteCity(cityBean, new AsyncCallback<CityBean>() {
			
			@Override
			public void onSuccess(CityBean cityBean) {
				
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
