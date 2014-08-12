package com.infoklinik.rsvp.client.doctor.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class DoctorProfileScheduleView extends BaseView {
	
	@UiField
	FlowPanel schedulesPanel;
	
	@UiField
	SimplePanel scheduleWrapperPanel;
	
	interface ModuleUiBinder extends UiBinder<Widget, DoctorProfileScheduleView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institutionBean;
	
	public DoctorProfileScheduleView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setSchedules(List<ScheduleBean> schedules) {
		
		schedulesPanel.clear();
		
		HashMap<Long, List<ScheduleBean>> scheduleMap = new HashMap<Long, List<ScheduleBean>>();
		HashMap<Long, InstitutionBean> instMap = new HashMap<Long, InstitutionBean>(); 
		
		for (ScheduleBean schedule : schedules) {
			
			InstitutionBean institution = schedule.getInstitutionBean();
			List<ScheduleBean> instSchedules =  scheduleMap.get(institution.getId());
			
			if (instSchedules == null) {
				instSchedules = new ArrayList<ScheduleBean>();
			}
			
			instSchedules.add(schedule);
			
			scheduleMap.put(institution.getId(), instSchedules);
			instMap.put(institution.getId(), institution);
		}
		
		for (Long instId : scheduleMap.keySet()) {
			
			List<ScheduleBean> instSchedules = scheduleMap.get(instId);
			InstitutionBean institution = instMap.get(instId);
			
			InstitutionView institutionView = new InstitutionView();
			institutionView.setInstitution(institution);
			institutionView.setSchedules(instSchedules);
			
			schedulesPanel.add(institutionView);
		}
	}
	
	@Override
	public void loadStyle() {
		
		if (scheduleWrapperPanel.getOffsetHeight() >= 290) {
			scheduleWrapperPanel.addStyleName("profile-inst-doctor-sche-wrapper-border");
		} else {
			scheduleWrapperPanel.removeStyleName("profile-inst-doctor-sche-wrapper-border");
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
