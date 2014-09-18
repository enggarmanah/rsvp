package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import gwtupload.client.IUploader;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminServiceView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setServiceTypes(List<ServiceTypeBean> list);
	
	ServiceBean getService();
	
	void setService(ServiceBean serviceBean);
	
	void setPromoChangeHandler(ChangeHandler handler);
	
	void setOnFinishUploadHandler(IUploader.OnFinishUploaderHandler handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
