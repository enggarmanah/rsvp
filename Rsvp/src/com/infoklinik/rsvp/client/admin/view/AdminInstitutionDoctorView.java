package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.SuggestionOracle;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionDoctorView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.infoklinik.rsvp.shared.SuggestParameter;

public class AdminInstitutionDoctorView extends BaseView implements IAdminInstitutionDoctorView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminInstitutionDoctorView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField(provided = true)
	SuggestBox doctorSb = new SuggestBox(new SuggestionOracle(new SuggestParameter(SuggestionOracle.SEARCH_DOCTOR)));
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	DialogBox dialogBox;
	
	List<ServiceTypeBean> list;
	
	DoctorBean doctor;
	
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
		
		dialogBox.setText("Tambah Dokter Baru");
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

	public DoctorBean getDoctor() {
		
		return doctor;
	}
	
	public void setDoctor(DoctorBean doctor) {
		
		this.doctor = doctor;
		
		doctorSb.setText(Constant.EMPTY_STRING);
		
		if (!ClientUtil.isEmpty(doctor.getName())) {
			doctorSb.setText(doctor.getName() + ", " + doctor.getSpeciality().getDescription());
		}
	}
	
	public void setDoctorSelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler) {
		
		doctorSb.addSelectionHandler(handler);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
}
