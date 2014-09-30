package com.infoklinik.rsvp.client.admin.presenter;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminStatisticEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminStatisticView;
import com.infoklinik.rsvp.client.admin.view.AdminStatisticView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CommonServiceAsync;
import com.infoklinik.rsvp.client.rpc.StatisticServiceAsync;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminStatisticView.class)
public class AdminStatisticPresenter extends LazyPresenter<IAdminStatisticView, AdminStatisticEventBus> {
	
	@Inject
	CommonServiceAsync commonService;
	
	@Inject
	StatisticServiceAsync statisticService;
	
	boolean isLoadFail = false;
	boolean isLoadDataStatCompleted = false;
	boolean isLoadApptStatCompleted = false;
	boolean isLoadSearchTypeStatCompleted = false;
	boolean isLoadSearchMethodStatCompleted = false;
	
	@Override
	public void bindView() {
	}
	
	private void displaySuccess() {
		
		if (isLoadDataStatCompleted && isLoadApptStatCompleted && 
			isLoadSearchTypeStatCompleted && isLoadSearchMethodStatCompleted) {
			ProgressDlg.hide();
		}
	}
	
	private void displayErrOnLoad() {
		
		if (!isLoadFail) {
			isLoadFail = true;
			NotificationDlg.warning(Message.ERR_COMMON_LOAD_FAILED);
		}
	}
	
	public void onLoadStatistic() {
		
		eventBus.setLeftPanel(view.asWidget());
		
		ProgressDlg.show();
		
		statisticService.getDataStatistic(new AsyncCallback<Map<String,Long>>() {
			
			@Override
			public void onSuccess(Map<String, Long> result) {
				view.setDataStatistic(result);
				isLoadDataStatCompleted = true;
				displaySuccess();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				displayErrOnLoad();
			}
		});
		
		statisticService.getSearchTypeStatistic(new AsyncCallback<Map<String,Long>>() {
			
			@Override
			public void onSuccess(Map<String, Long> result) {
				view.setSearchTypeStatistic(result);
				isLoadSearchTypeStatCompleted = true;
				displaySuccess();
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				displayErrOnLoad();
			}
		});
		
		statisticService.getSearchMethodStatistic(new AsyncCallback<Map<String,Long>>() {
			
			@Override
			public void onSuccess(Map<String, Long> result) {
				view.setSearchMethodStatistic(result);
				isLoadSearchMethodStatCompleted = true;
				displaySuccess();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				displayErrOnLoad();
			}
		});
		
		statisticService.getApptStatistic(new AsyncCallback<Map<Long,Long>>() {
			
			@Override
			public void onSuccess(Map<Long, Long> result) {
				view.setApptStatistic(result);
				isLoadApptStatCompleted = true;
				displaySuccess();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				displayErrOnLoad();
			}
		});
	}
}
