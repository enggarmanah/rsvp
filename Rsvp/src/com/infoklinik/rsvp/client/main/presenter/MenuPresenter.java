package com.infoklinik.rsvp.client.main.presenter;

import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.infoklinik.rsvp.client.main.MenuEventBus;
import com.infoklinik.rsvp.client.main.presenter.interfaces.IMenuView;
import com.infoklinik.rsvp.client.main.view.MenuView;
import com.infoklinik.rsvp.shared.Constant;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = MenuView.class)
public class MenuPresenter extends LazyPresenter<IMenuView, MenuEventBus> {
	
	private String currentMenu;
	
	@Override
	public void bindView() {
		
		currentMenu = Constant.MENU_DOCTOR;
		
		view.setDoctorSelected();
		
		view.setClinicLbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				removeOldView();
				
				view.setClinicSelected();
				eventBus.loadClinicSearch();
			}
		});
		
		view.setHospitalLbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				removeOldView();
				
				view.setHospitalSelected();
				eventBus.loadHospitalSearch();
			}
		});
		
		view.setDoctorLbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				removeOldView();
				
				view.setDoctorSelected();
				eventBus.loadDoctorSearch();
			}
		});
		
		view.setServiceLbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				removeOldView();
				
				view.setServiceSelected();
				eventBus.loadServiceSearch();
			}
		});
		
		view.setLabLbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				removeOldView();
				
				view.setLabSelected();
				eventBus.loadLabSearch();
			}
		});
	}
	
	public void onLoadMenu() {
		
		eventBus.setMenuPanel(view.asWidget());
	}
	
	private void removeOldView() {
		
		if (Constant.MENU_CLINIC.equals(currentMenu)) {
			eventBus.removeClinicSearch();
			
		} else if (Constant.MENU_HOSPITAL.equals(currentMenu)) {
			eventBus.removeHospitalSearch();
			
		} else if (Constant.MENU_DOCTOR.equals(currentMenu)) {
			eventBus.removeDoctorSearch();
			
		} else if (Constant.MENU_SERVICE.equals(currentMenu)) {
			eventBus.removeDoctorSearch();
		}
	}
}