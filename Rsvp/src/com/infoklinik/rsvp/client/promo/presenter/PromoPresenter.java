package com.infoklinik.rsvp.client.promo.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.main.presenter.MainPresenter;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.promo.PromoEventBus;
import com.infoklinik.rsvp.client.promo.presenter.interfaces.IPromoView;
import com.infoklinik.rsvp.client.promo.view.PromoView;
import com.infoklinik.rsvp.client.rpc.ServiceServiceAsync;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = PromoView.class)
public class PromoPresenter extends LazyPresenter<IPromoView, PromoEventBus> {
	
	private static int PROMO_SIZE = 5;
	private static int PROMO_REFRESH_RATE = 20;
	
	@Inject
	ServiceServiceAsync serviceService;
	
	List<GenericBean<ServiceBean>> promos;
	
	@Override
	public void bindView() {
	}
	
	public void onLoadPromotion() {
		
		ProgressDlg.show();
		
		serviceService.getPromotions(new AsyncCallback<List<ServiceBean>>() {
			
			@Override
			public void onSuccess(List<ServiceBean> result) {
				
				List<GenericBean<ServiceBean>> promotions = new ArrayList<GenericBean<ServiceBean>>();
				
				for (final ServiceBean service : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					handlerMgr.setShowHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							
							eventBus.loadServiceInfo(service);
						}
					});
					
					GenericBean<ServiceBean> genService = new GenericBean<ServiceBean>(service, handlerMgr);
					promotions.add(genService);
				}
				
				promos = promotions;
				displayPromotions();
				refreshPromotions();
				
				MainPresenter.isPromotionLoaded = true;
				
				if (MainPresenter.isPartnerLoaded) {
					ProgressDlg.hide();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				MainPresenter.isPromotionLoaded = true;
				
				if (MainPresenter.isPartnerLoaded) {
					ProgressDlg.hide();
				}
			}
		});
		
		eventBus.setRightPanel(view.asWidget());
	}
	
	
	private void displayPromotions() {
		
		view.fadeOut();
		
		Timer timerReplace = new Timer() {
			
			@Override
			public void run() {
				
				view.clear();
				
				for (int i = 0; i < PROMO_SIZE && i < promos.size(); i++) {
					
					view.addPromo(promos.get(i));
				}
				
				view.fadeIn();
			}
		};
		
		timerReplace.schedule(1000);
	}
	
	private void refreshPromotions() {
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				if (promos.size() > 0) {
					
					GenericBean<ServiceBean> service = promos.remove(promos.size()-1);
					promos.add(0, service);
				}
				
				displayPromotions();
			}
		};
		
		timer.scheduleRepeating(PROMO_REFRESH_RATE * 1000);
	}
}
