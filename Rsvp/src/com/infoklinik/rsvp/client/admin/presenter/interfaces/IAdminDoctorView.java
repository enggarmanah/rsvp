package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import java.util.List;

import gwtupload.client.IUploader;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.SpecialityBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminDoctorView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	DoctorBean getDoctor();
	
	void setDoctor(DoctorBean doctor);
	
	void setSpecialities(List<SpecialityBean> specialityBeans);
	
	void setOnFinishUploadHandler(IUploader.OnFinishUploaderHandler handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
