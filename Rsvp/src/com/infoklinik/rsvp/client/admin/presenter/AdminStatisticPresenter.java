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
	
	@Override
	public void bindView() {
	}
	
	public void onLoadStatistic() {
		
		eventBus.setLeftPanel(view.asWidget());
		
		statisticService.getDataStatistic(new AsyncCallback<Map<String,Long>>() {
			
			@Override
			public void onSuccess(Map<String, Long> result) {
				view.setDataCount(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				NotificationDlg.warning(Message.ERR_COMMON_LOAD_FAILED);
			}
		});
	}
}
