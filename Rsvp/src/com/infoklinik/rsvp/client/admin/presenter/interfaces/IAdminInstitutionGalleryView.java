package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import gwtupload.client.IUploader;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminInstitutionGalleryView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	GalleryBean getGallery();
	
	void setGallery(GalleryBean gallery);
	
	void setOnFinishUploadHandler(IUploader.OnFinishUploaderHandler handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
