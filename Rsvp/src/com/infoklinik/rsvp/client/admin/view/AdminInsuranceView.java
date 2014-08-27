package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInsuranceView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InsuranceBean;

public class AdminInsuranceView extends BaseView implements IAdminInsuranceView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminInsuranceView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	TextBox nameTb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	DialogBox dialogBox;
	
	List<InsuranceBean> list;
	
	InsuranceBean insurance;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	public void show() {
		
		goToTop();
		
		dlgFadeOut();
		
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft() + Constant.POPUP_L2_LEFT, Constant.POPUP_L2_TOP);
		dialogBox.show();
		
		dlgFadeIn();
	}
	
	public void hide() {
		
		dlgFadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				dialogBox.hide();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}

	public InsuranceBean getInsuranceBean() {
		
		insurance.setName(nameTb.getText());
		
		return insurance;
	}
	
	public void setInsuranceBean(InsuranceBean insuranceBean) {
		
		this.insurance = insuranceBean;
		
		if (insuranceBean.getId() == null) {
			dialogBox.setText("Tambah Asuransi Baru");
		} else {
			dialogBox.setText("Perubahan Data Asuransi");
		}
		
		nameTb.setText(insuranceBean.getName());
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
}
