package com.infoklinik.rsvp.client.admin.presenter;

import gwtupload.client.IUploader;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.UploadedInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminDoctorView;
import com.infoklinik.rsvp.client.admin.view.AdminDoctorView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.DoctorServiceAsync;
import com.infoklinik.rsvp.client.rpc.SpecialityServiceAsync;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.SpecialityBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminDoctorView.class)
public class AdminDoctorPresenter extends LazyPresenter<IAdminDoctorView, AdminEventBus> {
	
	@Inject
	DoctorServiceAsync doctorService;
	
	@Inject
	SpecialityServiceAsync specialityService;
	
	boolean isAdd = true;
	
	DoctorBean doctor;
	DoctorBean orgDoctor;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initDoctorFinishUploaderHandler();
		initSpecialities();
		
		initOkBtnClickHandler();
		initCancelBtnClickHandler();
	}
	
	public void onAddDoctor() {
		
		isAdd = true;
		orgDoctor = null;
		
		doctor = new DoctorBean();
		
		view.setDoctor(doctor);
		view.show();
	}
	
	public void onUpdateDoctor(DoctorBean doctor) {
				
		isAdd = false;
		orgDoctor = doctor;
		
		this.doctor = new DoctorBean();
		this.doctor.setBean(orgDoctor);
		
		view.setDoctor(this.doctor);
		view.show();
	}
	
	private void initSpecialities() {
		
		specialityService.getSpecialities(new AsyncCallback<List<SpecialityBean>>() {
			
			@Override
			public void onSuccess(List<SpecialityBean> specialityBeans) {
				view.setSpecialities(specialityBeans);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void initDoctorFinishUploaderHandler() {
		
		IUploader.OnFinishUploaderHandler handler = new IUploader.OnFinishUploaderHandler() {
			
			public void onFinish(IUploader uploader) {
				
				if (uploader.getStatus() == Status.SUCCESS) {
					
					UploadedInfo info = uploader.getServerInfo();
					
					String imageId = info.message;
					
					doctor.setImageId(Long.valueOf(imageId));
					
					view.setDoctor(doctor);
					
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
				
				doctor = view.getDoctor();
				doctor.setUpdateBy(ClientUtil.getUser().getName());
				
				if (isValidated()) {
					
					ProgressDlg.show();
					
					if (isAdd) {
						
						doctorService.addDoctor(doctor, new AsyncCallback<DoctorBean>() {
							
							@Override
							public void onSuccess(DoctorBean result) {
								doctor = result;
								view.hide();
								ProgressDlg.hide();
							}
							
							@Override
							public void onFailure(Throwable caught) {
								view.hide();
								ProgressDlg.failure();
							}
						});
					
					} else {
						
						doctorService.updateDoctor(doctor, new AsyncCallback<DoctorBean>() {
							
							@Override
							public void onSuccess(DoctorBean result) {
								doctor = result;
								orgDoctor.setBean(doctor);
								view.hide();
								ProgressDlg.hide();
							}
							
							@Override
							public void onFailure(Throwable caught) {
								view.hide();
								ProgressDlg.failure();
							}
						});
					}
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
