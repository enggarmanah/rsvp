package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionDoctorView;
import com.infoklinik.rsvp.client.admin.view.AdminInstitutionDoctorView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.DoctorServiceAsync;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.SearchSuggestion;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminInstitutionDoctorView.class)
public class AdminInstitutionDoctorPresenter extends LazyPresenter<IAdminInstitutionDoctorView, AdminEventBus> {
	
	@Inject
	DoctorServiceAsync doctorService;
	
	boolean isAdd = true;
	
	DoctorBean doctor;
	DoctorBean orgDoctor;
	
	List<DoctorBean> existingDoctors;
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initDoctorSelectionHandler();
		initOkBtnClickHandler();
		initCancelBtnClickHandler();
	}
	
	public void onAddInstitutionDoctor() {
		
		isAdd = true;
		orgDoctor = null;
		
		doctor = new DoctorBean();
		
		view.setDoctor(doctor);
		view.show();
	}
	
	public void onUpdateInstitutionDoctor(DoctorBean doctor) {
				
		isAdd = false;
		orgDoctor = doctor;
		
		this.doctor = new DoctorBean();
		this.doctor.setBean(orgDoctor);
		
		view.setDoctor(this.doctor);
		view.show();
	}
	
	public void onSetInstitutionExistingDoctors(List<DoctorBean> doctors) {
		
		existingDoctors = doctors;
	}
	
	private void initDoctorSelectionHandler() {
		
		view.setDoctorSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				
				doctor = view.getDoctor();
				
				SearchSuggestion suggestion = (SearchSuggestion) event.getSelectedItem();
				
				doctor.setId(ClientUtil.strToLong(suggestion.getValue()));
				doctor.setName(suggestion.getReplacementString());
			}
		});
	}
	
	private void initOkBtnClickHandler() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				doctor = view.getDoctor();
				
				if (isValidated()) {
					
					ProgressDlg.show();
					
					doctorService.getDoctor(doctor.getId(), new AsyncCallback<DoctorBean>() {
						
						@Override
						public void onSuccess(DoctorBean result) {
							
							if (isAdd) {
								doctor = result;
								eventBus.addInstDoctor(doctor);
							} else {
								orgDoctor.setBean(result);
								eventBus.updateInstDoctor(orgDoctor);
							}
								
							view.hide();
							ProgressDlg.hide();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							
							ProgressDlg.failure();
						}
					});
					
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
		
		DoctorBean doc = view.getDoctor();
		
		if (ClientUtil.isEmpty(doctor.getId())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_DOCTOR_EMPTY);
		
		} else if (!ClientUtil.isEmpty(doc.getName()) &&
				!doc.getName().equals(doctor.getName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_DOCTOR_EMPTY);
			
		} else if (isAdd || (orgDoctor.getId() != doctor.getId())) {
			
			for (DoctorBean existingDoctor : existingDoctors) {
				
				if (existingDoctor.getId().equals(doctor.getId())) {
					
					isValidated = false;
					errorMessages.add(Message.ERR_INST_DOCTOR_EXIST);
					break;
				}
			}
		}
		
		return isValidated;
	}
}
