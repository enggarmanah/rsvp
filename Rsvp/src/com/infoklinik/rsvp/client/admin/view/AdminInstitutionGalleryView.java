package com.infoklinik.rsvp.client.admin.view;

import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.CustomUploadStatus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionGalleryView;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.GalleryBean;

public class AdminInstitutionGalleryView extends BaseView implements IAdminInstitutionGalleryView {
	
	private DialogBox dialogBox;
	
	SingleUploader galleryUploader;
	
	@UiField
	Image galleryImg;
	
	@UiField
	CheckBox mainGalleryCb;
	
	@UiField
	TextBox descriptionTb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminInstitutionGalleryView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	GalleryBean gallery;
	
	@UiField
	SimplePanel uploadGalleryPanel;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
		
		galleryUploader = new SingleUploader();
		galleryUploader.getWidget().setStyleName("gallery-upload");
		
		uploadGalleryPanel.add(galleryUploader);
		galleryUploader.addOnStartUploadHandler(onStartUploaderHandler);
	}
	
	private IUploader.OnStartUploaderHandler onStartUploaderHandler = new IUploader.OnStartUploaderHandler() {
		
		@Override
		public void onStart(IUploader uploader) {
			
			uploader.setStatusWidget(new CustomUploadStatus());
			ProgressDlg.show();
		}
	};
	
	public void setOnFinishUploadHandler(IUploader.OnFinishUploaderHandler handler) {
		
		galleryUploader.addOnFinishUploadHandler(handler);
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return dialogBox;
	}
	
	public GalleryBean getGallery() {
		
		gallery.setMain(mainGalleryCb.getValue());
		gallery.setDescription(descriptionTb.getValue());
		
		return gallery;
	}
	
	public void setGallery(GalleryBean gallery) {
		
		this.gallery = gallery;
		
		dialogBox.setText("Perubahan Info Galeri");
		galleryImg.setUrl(Constant.IMAGE_URL + gallery.getImageId());
		mainGalleryCb.setValue(gallery.isMain());
		descriptionTb.setValue(gallery.getDescription());
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
		dialogBox.setPopupPosition(dialogBox.getPopupLeft() + Constant.POPUP_L2_LEFT, Constant.POPUP_L2_TOP);
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