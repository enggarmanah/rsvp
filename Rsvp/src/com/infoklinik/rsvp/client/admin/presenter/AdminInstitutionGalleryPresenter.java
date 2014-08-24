package com.infoklinik.rsvp.client.admin.presenter;

import gwtupload.client.IUploader;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.UploadedInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionGalleryView;
import com.infoklinik.rsvp.client.admin.view.AdminInstitutionGalleryView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminInstitutionGalleryView.class)
public class AdminInstitutionGalleryPresenter extends LazyPresenter<IAdminInstitutionGalleryView, AdminEventBus> {
	
	boolean isAdd = true;
	
	GalleryBean gallery;
	GalleryBean orgGallery;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initGalleryFinishUploaderHandler();
		
		initOkBtnClickHandler();
		initCancelBtnClickHandler();
	}
	
	public void onAddInstitutionGallery(GalleryBean gallery) {
		
		isAdd = true;
		orgGallery = null;
		
		this.gallery = gallery;
		
		view.setGallery(gallery);
		view.show();
	}
	
	public void onUpdateInstitutionGallery(GalleryBean gallery) {
				
		isAdd = false;
		orgGallery = gallery;
		
		this.gallery = new GalleryBean();
		this.gallery.setBean(gallery);
		
		view.setGallery(this.gallery);
		view.show();
	}
	
	private void initGalleryFinishUploaderHandler() {
		
		IUploader.OnFinishUploaderHandler handler = new IUploader.OnFinishUploaderHandler() {
			
			public void onFinish(IUploader uploader) {
				
				if (uploader.getStatus() == Status.SUCCESS) {
					
					UploadedInfo info = uploader.getServerInfo();
					
					String imageId = info.message;
					
					gallery.setImageId(Long.valueOf(imageId));
					
					view.setGallery(gallery);
					
					ProgressDlg.hide();
				}
			}
		};
		
		view.setOnFinishUploadHandler(handler);
	}
	
	private void initOkBtnClickHandler() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				gallery = view.getGallery();
				gallery.setUpdateBy(ClientUtil.getUser().getName());
				
				if (isValidated()) {
					
					ProgressDlg.show();
					
					if (isAdd) {
						eventBus.addInstGallery(gallery);
					} else {
						orgGallery.setBean(gallery);
						eventBus.updateInstGallery(orgGallery);
					}
						
					view.hide();
					ProgressDlg.hide();
					
				} else {
					NotificationDlg.error(errorMessages);
				}
			}
		});
	}
	
	private void initCancelBtnClickHandler() {
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	private boolean isValidated() {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		return isValidated;
	}
}
