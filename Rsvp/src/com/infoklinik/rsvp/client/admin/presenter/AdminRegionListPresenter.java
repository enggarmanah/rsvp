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
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminRegionListView;
import com.infoklinik.rsvp.client.admin.view.AdminRegionListView;
import com.infoklinik.rsvp.client.main.view.ConfirmDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.RegionServiceAsync;
import com.infoklinik.rsvp.shared.RegionBean;
import com.infoklinik.rsvp.shared.RegionSearchBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminRegionListView.class)
public class AdminRegionListPresenter extends LazyPresenter<IAdminRegionListView, AdminEventBus> {
	
	@Inject
	private RegionServiceAsync regionServiceAsync;
	
	List<GenericBean<RegionBean>> genericBeans;
	
	@Override
	public void bindView() {
		
		view.setSearchBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				getRegions();
			}
		});
		
		view.setAddBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addRegion();
			}
		});
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				view.hide();
			}
		});
	}
	
	public void onLoadRegion() {
		
		view.show();
	}
	
	public void onReloadRegion() {
		
		view.refresh();
	}
	
	private void getRegions() {
		
		RegionSearchBean regionSearchBean = view.getRegionSearch();
		
		ProgressDlg.show();
		regionServiceAsync.getRegions(regionSearchBean, new AsyncCallback<List<RegionBean>>() {
			
			@Override
			public void onSuccess(List<RegionBean> result) {
				
				genericBeans = new ArrayList<GenericBean<RegionBean>>();
				
				for (RegionBean regionBean : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					
					GenericBean<RegionBean> genericBean = new GenericBean<RegionBean>(regionBean, handlerMgr);
					
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
	
	private ClickHandler getUpdateHandler(final GenericBean<RegionBean> genericBean) {
		
		ClickHandler detailHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				RegionBean regionBean = genericBean.getBean();
				
				eventBus.updateRegion(regionBean);
			}
		};
		
		return detailHandler;
	}
	
	private ClickHandler getDeleteHandler(final GenericBean<RegionBean> genericBean) {
		
		ClickHandler deleteHandler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				RegionBean regionBean = genericBean.getBean();
				
				String confirm = "Hapus asuransi \"" + regionBean.getName() + "\" ?";
				
				ConfirmDlg.confirm(confirm, new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						deleteRegion(genericBean);
					}
				});
			}
		};
		
		return deleteHandler;
	}
	
	private void deleteRegion(final GenericBean<RegionBean> genericBean) {
		
		RegionBean regionBean = genericBean.getBean();
		
		ProgressDlg.show();
		regionServiceAsync.deleteRegion(regionBean, new AsyncCallback<RegionBean>() {
			
			@Override
			public void onSuccess(RegionBean regionBean) {
				
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
