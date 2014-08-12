package com.infoklinik.rsvp.client.admin.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.infoklinik.rsvp.client.admin.AdminStatisticEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminStatisticView;
import com.infoklinik.rsvp.client.admin.view.AdminStatisticView;
import com.infoklinik.rsvp.client.rpc.CommonServiceAsync;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminStatisticView.class)
public class AdminStatisticPresenter extends LazyPresenter<IAdminStatisticView, AdminStatisticEventBus> {
	
	@Inject
	CommonServiceAsync commonService;
	
	@Override
	public void bindView() {
	}
	
	public void onLoadStatistic() {
		
		eventBus.setLeftPanel(view.asWidget());
	}
}
