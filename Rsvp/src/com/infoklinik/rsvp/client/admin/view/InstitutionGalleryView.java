package com.infoklinik.rsvp.client.admin.view;

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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.shared.GalleryBean;

public class InstitutionGalleryView extends BaseView {
	
	@UiField
	SimplePanel imagePanel;
	
	@UiField
	SimplePanel uploadPanel;
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionGalleryView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	List<GalleryBean> galleries;
	
	public InstitutionGalleryView() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		// Create a new uploader panel and attach it to the document
		SingleUploader defaultUploader = new SingleUploader();
		defaultUploader.getWidget().setStyleName("gallery-upload");
		
		uploadPanel.add(defaultUploader);
		
		// Add a finish handler which will load the image once the upload finishes
		defaultUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
		defaultUploader.addOnStartUploadHandler(onStartUploaderHandler);	
	}
	
	private IUploader.OnStartUploaderHandler onStartUploaderHandler = new IUploader.OnStartUploaderHandler() {
		
		@Override
		public void onStart(IUploader uploader) {
			
			//ProgressDlg.show();
		}
	};
	
	// Load the image in the document and in the case of success attach it to the viewer
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				
				new PreloadedImage(uploader.fileUrl(), showImage);

				// The server sends useful information to the client by default
				UploadedInfo info = uploader.getServerInfo();
				System.out.println("File name " + info.name);
				System.out.println("File content-type " + info.ctype);
				System.out.println("File size " + info.size);

				// You can send any customized message and parse it
				System.out.println("Server message " + info.message);
				
				//ProgressDlg.hide();
			
			} else if (uploader.getStatus() == Status.ERROR) {
				
				UploadedInfo info = uploader.getServerInfo();
				
				//ProgressDlg.hidePrompt();
				//NotificationDlg.warning(info.message);
			}
		}
	};

	// Attach an image to the pictures viewer
	private OnLoadPreloadedImageHandler showImage = new OnLoadPreloadedImageHandler() {
		public void onLoad(PreloadedImage image) {
			image.setHeight("60px");
			imagePanel.clear();
			imagePanel.add(image);
		}
	};
	
	public void setGalleries(final List<GalleryBean> galleries) {
		
		this.galleries = galleries;
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
