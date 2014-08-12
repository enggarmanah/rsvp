package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionInsuranceView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;

public class AdminInstitutionInsuranceView extends BaseView implements IAdminInstitutionInsuranceView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminInstitutionInsuranceView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	ListBox insuranceLb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	DialogBox dialogBox;
	
	List<ServiceTypeBean> list;
	
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
		
		dlgFadeOut();
		
		dialogBox.setText("Tambah Asuransi");
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

	public void setInsurances(List<InsuranceBean> list) {
		
		insuranceLb.clear();
		
		insuranceLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (InsuranceBean insurance : list) {
			insuranceLb.addItem(insurance.getName(), String.valueOf(insurance.getId()));
		}
	}
	
	public InsuranceBean getInsurance() {
		
		if (insuranceLb.getSelectedIndex() != 0) {
			
			insurance.setId(Long.valueOf(insuranceLb.getValue(insuranceLb.getSelectedIndex())));
			insurance.setName(insuranceLb.getItemText(insuranceLb.getSelectedIndex()));
		}
		
		return insurance;
	}
	
	public void setInsurance(InsuranceBean insurance) {
		
		this.insurance = insurance;
		
		ClientUtil.setSelectedIndex(insuranceLb, String.valueOf(insurance.getId()));
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
}
