package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.CustomUploadStatus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminDoctorView;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.SpecialityBean;

public class AdminDoctorView extends BaseView implements IAdminDoctorView {
	
	private DialogBox dialogBox;
	
	SingleUploader profileImgUploader;
	
	@UiField
	Image profileImg;
	
	@UiField
	TextBox nameTb;

	@UiField
	ListBox specialityLb;
	
	@UiField
	TextBox regNoTb;
	
	@UiField
	TextBox mobileTb;
	
	@UiField
	TextBox emailTb;
	
	@UiField
	TextArea addressTa;
	
	@UiField
	TextArea profileTa;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminDoctorView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	DoctorBean doctor;
	
	@UiField
	SimplePanel uploadProfileImgPanel;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
		dialogBox.setText("Bio Data Dokter");
		
		profileImgUploader = new SingleUploader();
		profileImgUploader.getWidget().setStyleName("gallery-upload");
		
		uploadProfileImgPanel.add(profileImgUploader);
		profileImgUploader.addOnStartUploadHandler(onStartUploaderHandler);
	}
	
	private IUploader.OnStartUploaderHandler onStartUploaderHandler = new IUploader.OnStartUploaderHandler() {
		
		@Override
		public void onStart(IUploader uploader) {
			
			uploader.setStatusWidget(new CustomUploadStatus());
			ProgressDlg.show();
		}
	};
	
	public void setOnFinishUploadHandler(IUploader.OnFinishUploaderHandler handler) {
		
		profileImgUploader.addOnFinishUploadHandler(handler);
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return dialogBox;
	}
	
	public DoctorBean getDoctor() {
		
		doctor.setName(nameTb.getValue());
		
		SpecialityBean speciality = new SpecialityBean();
		speciality.setId(Long.valueOf(specialityLb.getValue(specialityLb.getSelectedIndex())));
		
		doctor.setSpeciality(speciality);
		doctor.setRegNo(regNoTb.getValue());
		doctor.setMobile(mobileTb.getValue());
		doctor.setEmail(emailTb.getValue());
		doctor.setAddress(addressTa.getValue());
		doctor.setProfile(profileTa.getValue());
		
		return doctor;
	}
	
	public void setDoctor(DoctorBean doctor) {
		
		this.doctor = doctor;
		
		if (doctor.getImageId() != null) {
			profileImg.setUrl(Constant.IMAGE_URL + doctor.getImageId());
		} else {
			profileImg.setUrl("images/doctor.jpg");
		}
		
		nameTb.setValue(doctor.getName());
		
		if (doctor.getSpeciality() != null) {
			ClientUtil.setSelectedIndex(specialityLb, doctor.getSpeciality().getDescription());
		}
		
		regNoTb.setValue(doctor.getRegNo());
		mobileTb.setValue(doctor.getMobile());
		emailTb.setValue(doctor.getEmail());
		addressTa.setValue(doctor.getAddress());
		profileTa.setValue(doctor.getProfile());
	}
	
	public void setSpecialities(List<SpecialityBean> specialityBeans) {
		
		specialityLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (SpecialityBean specialityBean : specialityBeans) {
			specialityLb.addItem(specialityBean.getDescription(), String.valueOf(specialityBean.getId()));
		}
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
	
	public void show() {
		
		fadeOut();
		
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft(), Constant.POPUP_L1_TOP);
		dialogBox.show();
		
		fadeIn();
	}
	
	public void hide() {
		
		fadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				dialogBox.hide();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}
}