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
				
				currentMenu = Constant.MENU_CLINIC;
				
				removeOldView();
				
				view.setClinicSelected();
				eventBus.loadClinicSearch();
				eventBus.setMenuImage(currentMenu);
			}
		});
		
		view.setHospitalLbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				currentMenu = Constant.MENU_HOSPITAL;
				
				removeOldView();
				
				view.setHospitalSelected();
				eventBus.loadHospitalSearch();
				eventBus.setMenuImage(currentMenu);
			}
		});
		
		view.setDoctorLbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				currentMenu = Constant.MENU_DOCTOR;
				
				removeOldView();
				
				view.setDoctorSelected();
				eventBus.loadDoctorSearch();
				eventBus.setMenuImage(currentMenu);
			}
		});
		
		view.setServiceLbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				currentMenu = Constant.MENU_SERVICE;
				
				removeOldView();
				
				view.setServiceSelected();
				eventBus.loadServiceSearch();
				eventBus.setMenuImage(currentMenu);
			}
		});
		
		view.setLabLbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				currentMenu = Constant.MENU_LAB;
				
				removeOldView();
				
				view.setLabSelected();
				eventBus.loadLabSearch();
				eventBus.setMenuImage(currentMenu);
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