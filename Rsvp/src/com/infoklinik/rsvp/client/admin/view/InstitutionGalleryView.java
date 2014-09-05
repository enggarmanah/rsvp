package com.infoklinik.rsvp.client.admin.view;

import gwtupload.client.IUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;
import gwtupload.client.SingleUploader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.CustomUploadStatus;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
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
	FocusPanel addGalleryBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionGalleryView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institution;
	Long imageId;
	
	SingleUploader logoUploader;
	
	Map<GalleryBean, InstitutionGalleryItemView> galleryMap = new HashMap<GalleryBean, InstitutionGalleryItemView>();
	
	public InstitutionGalleryView() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		// Create a new uploader panel and attach it to the document
		logoUploader = new SingleUploader();
		logoUploader.getWidget().setStyleName("gallery-upload");
		
		uploadLogoPanel.add(logoUploader);
		
		// Add a finish handler which will load the image once the upload finishes
		logoUploader.addOnFinishUploadHandler(onLogoFinishUploaderHandler);
		logoUploader.addOnStartUploadHandler(onStartUploaderHandler);
	}
	
	public void addGallery(GenericBean<GalleryBean> genGallery) {
		
		GalleryBean gallery = genGallery.getBean();
		HandlerManager handlerMgr = genGallery.getHandlerMgr();
		
		Image galleryImg = new Image();
		galleryImg.setUrl(Constant.IMAGE_URL + gallery.getImageId());
		
		InstitutionGalleryItemView galleryItem = new InstitutionGalleryItemView();
		
		galleryItem.setGallery(gallery);
		galleryItem.setUpdateBtnClickHandler(handlerMgr.getUpdateHandler());
		galleryItem.setDeleteBtnClickHandler(handlerMgr.getDeleteHandler());
		
		galleryPanel.add(galleryItem);
		
		galleryMap.put(gallery, galleryItem);
	}
	
	public void updateGallery(GenericBean<GalleryBean> genGallery) {
		
		GalleryBean gallery = genGallery.getBean();
	
		InstitutionGalleryItemView galleryItem = galleryMap.get(gallery);
		galleryItem.setGallery(gallery);
	}
	
	public void deleteGallery(GenericBean<GalleryBean> genGallery) {
		
		GalleryBean gallery = genGallery.getBean();
		InstitutionGalleryItemView galleryItem = galleryMap.remove(gallery);
		galleryPanel.remove(galleryItem);
	}
	
	public InstitutionBean getInstitution() {
		
		institution.setImageId(imageId);
		
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
	
	public void setAddGalleryBtnClickHandler(ClickHandler handler) {
		
		addGalleryBtn.addClickHandler(handler);
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
				imageId = ClientUtil.strToLong(imgId);
				
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
	
	public void setGalleries(final List<GenericBean<GalleryBean>> galleries) {
		
		galleryPanel.clear();
		
		for (final GenericBean<GalleryBean> genGallery : galleries) {
			
			GalleryBean gallery = genGallery.getBean();
			HandlerManager handlerMgr = genGallery.getHandlerMgr();
			
			Image galleryImg = new Image();
			galleryImg.setUrl(Constant.IMAGE_URL + gallery.getImageId());
			
			final InstitutionGalleryItemView galleryItem = new InstitutionGalleryItemView();
			
			galleryItem.setGallery(gallery);
			galleryItem.setUpdateBtnClickHandler(handlerMgr.getUpdateHandler());
			galleryItem.setDeleteBtnClickHandler(handlerMgr.getDeleteHandler());
			
			galleryPanel.add(galleryItem);
			galleryMap.put(gallery, galleryItem);
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
