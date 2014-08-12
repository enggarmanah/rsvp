package com.infoklinik.rsvp.client.partner.presenter;

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
import com.infoklinik.rsvp.client.partner.PartnerEventBus;
import com.infoklinik.rsvp.client.partner.presenter.interfaces.IPartnerView;
import com.infoklinik.rsvp.client.partner.view.PartnerView;
import com.infoklinik.rsvp.client.rpc.InstitutionServiceAsync;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = PartnerView.class)
public class PartnerPresenter extends LazyPresenter<IPartnerView, PartnerEventBus> {
	
	private static int PARTNER_SIZE = 5;
	private static int PARTNER_REFRESH_RATE = 15;
	
	@Inject
	InstitutionServiceAsync instService;
	
	List<GenericBean<InstitutionBean>> partners;
	
	@Override
	public void bindView() {
	}
	
	public void onLoadPartner() {
		
		if (partners == null) {
			
			ProgressDlg.show();
		
			instService.getPartners(new AsyncCallback<List<InstitutionBean>>() {
				
				@Override
				public void onSuccess(List<InstitutionBean> institutions) {
					
					List<GenericBean<InstitutionBean>> genInstitutions = new ArrayList<GenericBean<InstitutionBean>>();
					
					for (final InstitutionBean institution : institutions) {
						
						HandlerManager handlerMgr = new HandlerManager();
						handlerMgr.setShowHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								
								eventBus.loadClinicProfile(institution);
							}
						});
						
						GenericBean<InstitutionBean> genInstitution = new GenericBean<InstitutionBean>(institution, handlerMgr);
						genInstitutions.add(genInstitution);
					}
					
					partners = genInstitutions;
					
					displayPartners();
					refreshPartners();
					
					MainPresenter.isPartnerLoaded = true;
						
					if (MainPresenter.isPromotionLoaded) {
						ProgressDlg.hide();
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
					ProgressDlg.failure();
				}
			});
		}
		
		eventBus.setLeftPanel(view.asWidget());
	}
	
	private void displayPartners() {
		
		view.fadeOut();
		
		Timer timerReplace = new Timer() {
			
			@Override
			public void run() {
				
				view.clear();
				
				for (int i = 0; i < PARTNER_SIZE && i < partners.size(); i++) {
					
					view.addPartner(partners.get(i));
				}
				
				view.fadeIn();
			}
		};
		
		timerReplace.schedule(1000);
	}
	
	private void refreshPartners() {
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				if (partners.size() > 0) {
					
					GenericBean<InstitutionBean> genInstitution = partners.remove(partners.size()-1);
					partners.add(0, genInstitution);
				}
				
				displayPartners();
			}
		};
		
		timer.scheduleRepeating(PARTNER_REFRESH_RATE * 1000);
	}
}
