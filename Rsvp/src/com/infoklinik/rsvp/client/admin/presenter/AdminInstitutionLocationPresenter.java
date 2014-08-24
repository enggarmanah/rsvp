package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionLocationView;
import com.infoklinik.rsvp.client.admin.view.AdminInstitutionLocationView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminInstitutionLocationView.class)
public class AdminInstitutionLocationPresenter extends LazyPresenter<IAdminInstitutionLocationView, AdminEventBus> {
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initOkBtnClickHandler();
	}
	
	public void onGetInstitutionLocation(InstitutionBean institution) {
		
		view.setInstitution(institution);
		view.show();
	}
	
	private void initOkBtnClickHandler() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				InstitutionBean institution = view.getInstitution();
				
				if (isValidated()) {
					
					eventBus.updateInstitution(institution);
					view.hide();
					
				} else {
					NotificationDlg.error(errorMessages);
				}
			}
		});
	}
	
	private boolean isValidated() {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		return isValidated;
	}
}
