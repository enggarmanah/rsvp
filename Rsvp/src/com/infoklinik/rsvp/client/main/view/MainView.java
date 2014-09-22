package com.infoklinik.rsvp.client.main.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.main.presenter.interfaces.IMainView;
import com.infoklinik.rsvp.shared.Config;
import com.infoklinik.rsvp.shared.Constant;

public class MainView extends Composite implements IMainView {
	
	@UiField
	Label infoLb;
	
	@UiField
	Image mainImg;
	
	@UiField
	SimplePanel menuPanel;
	
	@UiField
	SimplePanel searchPanel;
	
	@UiField
	SimplePanel leftPanel;
	
	@UiField
	SimplePanel rightPanel;
	
	interface MainViewUiBinder extends UiBinder<Widget, MainView> {}
	
	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);
	
	public MainView() {	
		
		initWidget(uiBinder.createAndBindUi(this));
		init();
		
		//String info = "Portal Informasi Layanan Kesehatan";
		String info = "";
		if (Config.ENV_CURRENT.equals(Constant.ENV_STAGING)) {
			info += " - Testing Version";
		}
		
		infoLb.setText(info);
	}
	
	private void init() {
	}
	
	public void setMenuImage(String currentMenu) {
		
		if (Constant.MENU_DOCTOR.equals(currentMenu)) {
			mainImg.setUrl("images/doctor-main.jpg");
		} else if (Constant.MENU_CLINIC.equals(currentMenu)) {
			mainImg.setUrl("images/clinic-main.jpg");
		} else if (Constant.MENU_HOSPITAL.equals(currentMenu)) {
			mainImg.setUrl("images/hospital-main.jpg");
		} else if (Constant.MENU_LAB.equals(currentMenu)) {
			mainImg.setUrl("images/lab-main.jpg");
		} else if (Constant.MENU_PHARMACY.equals(currentMenu)) {
			mainImg.setUrl("images/pharmacy-main.jpg");
		} else if (Constant.MENU_SERVICE.equals(currentMenu)) {
			mainImg.setUrl("images/service-main.jpg");
		}
	}
		
	public void setMenuPanel(Widget menuPanel) {
		
		this.menuPanel.setWidget(menuPanel);
	}
	
	public void setSearchPanel(Widget searchPanel) {
		
		this.searchPanel.setWidget(searchPanel);
	}
	
	public void setLeftPanel(Widget leftPanel) {
		
		this.leftPanel.setWidget(leftPanel);
	}
	
	public void setRightPanel(Widget rightPanel) {
		
		this.rightPanel.setWidget(rightPanel);
	}

	public Widget getViewWidget() {
		
		return this;
	}
}
