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
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminStreetListView;
import com.infoklinik.rsvp.client.admin.view.AdminStreetListView;
import com.infoklinik.rsvp.client.main.view.ConfirmDlg;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CityServiceAsync;
import com.infoklinik.rsvp.client.rpc.StreetServiceAsync;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;
import com.infoklinik.rsvp.shared.StreetBean;
import com.infoklinik.rsvp.shared.StreetSearchBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminStreetListView.class)
public class AdminStreetListPresenter extends LazyPresenter<IAdminStreetListView, AdminEventBus> {
	
	@Inject
	private StreetServiceAsync streetService;
	
	@Inject
	private CityServiceAsync cityService;
	
	List<GenericBean<StreetBean>> genericBeans;
	
	@Override
	public void bindView() {
		
		view.setSearchBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				getStreets();
			}
		});
		
		view.setAddBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addStreet();
			}
		});
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				view.hide();
			}
		});
		
		cityService.getCities(new CitySearchBean(), new AsyncCallback<List<CityBean>>() {
			
			@Override
			public void onSuccess(List<CityBean> result) {
				
				view.setCities(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				NotificationDlg.warning(Message.ERR_COMMON_LOAD_FAILED);
			}
		});
	}
	
	public void onLoadStreet() {
		
		view.show();
	}
	
	public void onReloadStreet() {
		
		view.refresh();
	}
	
	private void getStreets() {
		
		StreetSearchBean streetSearch = view.getStreetSearch();
		
		ProgressDlg.show();
		streetService.getStreets(streetSearch, new AsyncCallback<List<StreetBean>>() {
			
			@Override
			public void onSuccess(List<StreetBean> result) {
				
				genericBeans = new ArrayList<GenericBean<StreetBean>>();
				
				for (StreetBean streetBean : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					
					GenericBean<StreetBean> genericBean = new GenericBean<StreetBean>(streetBean, handlerMgr);
					
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
	
	private ClickHandler getUpdateHandler(final GenericBean<StreetBean> genericBean) {
		
		ClickHandler updateHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				StreetBean streetBean = genericBean.getBean();
				
				eventBus.updateStreet(streetBean);
			}
		};
		
		return updateHandler;
	}
	
	private ClickHandler getDeleteHandler(final GenericBean<StreetBean> genericBean) {
		
		ClickHandler deleteHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				StreetBean streetBean = genericBean.getBean();
				
				String confirm = "Hapus wilayah \"" + streetBean.getName() + "\" ?";
				
				ConfirmDlg.confirm(confirm, new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						deleteStreet(genericBean);
					}
				});
			}
		};
		
		return deleteHandler;
	}
	
	private void deleteStreet(final GenericBean<StreetBean> genericBean) {
		
		StreetBean street = genericBean.getBean();
		
		ProgressDlg.show();
		streetService.deleteStreet(street, new AsyncCallback<StreetBean>() {
			
			@Override
			public void onSuccess(StreetBean streetBean) {
				
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
