package com.infoklinik.rsvp.client.admin.view;

import gwtupload.client.BaseUploadStatus;
import gwtupload.client.IUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;
import gwtupload.client.SingleUploader;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class InstitutionGalleryView extends BaseView {
	
	@UiField
	SimplePanel logoPanel;
	
	@UiField
	SimplePanel uploadLogoPanel;
	
	@UiField
	FlowPanel galleryPanel;
	
	@UiField
	SimplePanel uploadGalleryPanel;
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionGalleryView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	List<GalleryBean> galleries;
	
	InstitutionBean institution;
	Long imageId;
	
	SingleUploader logoUploader;
	SingleUploader galleryUploader;
	
	public InstitutionGalleryView() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		// Create a new uploader panel and attach it to the document
		logoUploader = new SingleUploader();
		logoUploader.getWidget().setStyleName("gallery-upload");
		
		uploadLogoPanel.add(logoUploader);
		
		// Add a finish handler which will load the image once the upload finishes
		logoUploader.addOnFinishUploadHandler(onLogoFinishUploaderHandler);
		logoUploader.addOnStartUploadHandler(onStartUploaderHandler);
		
		galleryUploader = new SingleUploader();
		galleryUploader.getWidget().setStyleName("gallery-upload");
		
		uploadGalleryPanel.add(galleryUploader);
		
		// Add a finish handler which will load the image once the upload finishes
		galleryUploader.addOnFinishUploadHandler(onGalleryFinishUploaderHandler);
		galleryUploader.addOnStartUploadHandler(onStartUploaderHandler);
	}
	
	public InstitutionBean getInstitution() {
		
		institution.setImageId(imageId);
		institution.setGalleries(galleries);
		
		return institution;
	}
	
	public void setInstitution(InstitutionBean institution) {
		
		this.institution = institution;
		Image logoImg = new Image();
		logoImg.setUrl(Constant.IMAGE_URL + institution.getImageId());
		imageId = institution.getImageId();
		logoPanel.clear();
		logoPanel.add(logoImg);
	}
	
	private IUploader.OnStartUploaderHandler onStartUploaderHandler = new IUploader.OnStartUploaderHandler() {
		
		@Override
		public void onStart(IUploader uploader) {
			
			uploader.setStatusWidget(new CustomUploadStatus());
			ProgressDlg.show();
		}
	};
	
	// Load the image in the document and in the case of success attach it to the viewer
	private IUploader.OnFinishUploaderHandler onLogoFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		
		public void onFinish(IUploader uploader) {
			
			if (uploader.getStatus() == Status.SUCCESS) {
				
				new PreloadedImage(uploader.fileUrl(), showLogo);

				// The server sends useful information to the client by default
				UploadedInfo info = uploader.getServerInfo();
				
				String imgId = info.message;
				imageId = Long.valueOf(imgId);
				
				ProgressDlg.hide();
			}
		}
	};
	
	private IUploader.OnFinishUploaderHandler onGalleryFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		
		public void onFinish(IUploader uploader) {
			
			if (uploader.getStatus() == Status.SUCCESS) {
				
				new PreloadedImage(uploader.fileUrl(), showGallery);

				UploadedInfo info = uploader.getServerInfo();
				
				String imageId = info.message;
				
				GalleryBean gallery = new GalleryBean();
				gallery.setInstitution(institution);
				gallery.setImageId(Long.valueOf(imageId));
				galleries.add(gallery);
				
				ProgressDlg.hide();
			}
		}
	};
	
	// Attach an image to the pictures viewer
	private OnLoadPreloadedImageHandler showLogo = new OnLoadPreloadedImageHandler() {
		public void onLoad(PreloadedImage image) {
			logoPanel.clear();
			logoPanel.add(image);
		}
	};
	
	private OnLoadPreloadedImageHandler showGallery = new OnLoadPreloadedImageHandler() {
		public void onLoad(PreloadedImage image) {
			galleryPanel.add(image);
		}
	};
	
	public void setGalleries(final List<GalleryBean> galleries) {
		
		this.galleries = galleries;
		galleryPanel.clear();
		
		for (GalleryBean gallery : galleries) {
			
			Image galleryImg = new Image();
			galleryImg.setUrl(Constant.IMAGE_URL + gallery.getImageId());
			galleryPanel.add(galleryImg);
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	private class CustomUploadStatus extends BaseUploadStatus {
		
		@Override
		public void setError(String msg) {
			setStatus(Status.ERROR);
			
			ProgressDlg.hidePrompt();
			
			if (msg.indexOf(Message.ERR_FILE_UPLOAD_EXCEED_MAX_SIZE) > -1) {
				NotificationDlg.warning(Message.ERR_FILE_UPLOAD_EXCEED_MAX_SIZE);
			} else {
				NotificationDlg.warning(Message.ERR_FILE_UPLOAD);
			}
		}
		
		@Override
		public void setProgress(long done, long total) {
			int percent =(int) (total > 0 ? done * 100 / total : 0);
			ProgressDlg.setPercentage(percent);
			super.setProgress(done, total);
		}
	}
}
