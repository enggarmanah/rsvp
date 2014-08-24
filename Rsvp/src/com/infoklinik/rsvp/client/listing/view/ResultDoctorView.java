
package com.infoklinik.rsvp.client.listing.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class ResultDoctorView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, ResultDoctorView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Label indexLb;
	
	@UiField
	Label nameLb;
	
	@UiField
	Label specialityLb;
	
	@UiField
	Label viewCountLb;
	
	@UiField
	Label likeCountLb;
	
	@UiField
	Label commentCountLb;
	
	@UiField
	Image viewCountImg;
	
	@UiField
	Image likeCountImg;
	
	@UiField
	Image commentCountImg;
	
	@UiField
	VerticalPanel schedulesPanel;
	
	public ResultDoctorView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setDoctor(int index, DoctorBean doctor, HandlerManager handlerMgr, HashMap<Long, HandlerManager> instHandlerMgrs,
			HashMap<Long, HashMap<Long, HandlerManager>> instScheduleHandlerMgrs) {
		
		indexLb.setText(index + ".");
		nameLb.setText(doctor.getName());
		specialityLb.setText(doctor.getSpeciality().getDescription());
		
		viewCountLb.setText(String.valueOf(doctor.getViewCount()));
		likeCountLb.setText(String.valueOf(doctor.getLikeCount()));
		commentCountLb.setText(String.valueOf(doctor.getCommentCount()));
		
		nameLb.addClickHandler(handlerMgr.getShowHandler());
		
		likeCountLb.addClickHandler(handlerMgr.getLikeHandler());
		likeCountImg.addClickHandler(handlerMgr.getLikeHandler());
		
		commentCountLb.addClickHandler(handlerMgr.getCommentHandler());
		commentCountImg.addClickHandler(handlerMgr.getCommentHandler());
		
		schedulesPanel.clear();
		
		HashMap<Long, List<ScheduleBean>> scheduleMap = new HashMap<Long, List<ScheduleBean>>();
		HashMap<Long, InstitutionBean> instMap = new HashMap<Long, InstitutionBean>();
		
		for (ScheduleBean schedule : doctor.getSchedules()) {
			
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
			
			HandlerManager instHandlerMgr = instHandlerMgrs.get(institution.getId());
			HashMap<Long, HandlerManager> instScheduleHandlerMgr = instScheduleHandlerMgrs.get(institution.getId());
			
			GenericBean<InstitutionBean> genInstitution = new GenericBean<InstitutionBean>(institution, instHandlerMgr);
			
			List<GenericBean<ScheduleBean>> genSchedules = new ArrayList<GenericBean<ScheduleBean>>();
			
			for (ScheduleBean schedule : instSchedules) {
				GenericBean<ScheduleBean> genSchedule = new GenericBean<ScheduleBean>(schedule, instScheduleHandlerMgr.get(schedule.getId()));
				genSchedules.add(genSchedule);
			}
			
			ResultDoctorInstitutionView docInstitutionView = new ResultDoctorInstitutionView();
			docInstitutionView.setInstitution(genInstitution);
			docInstitutionView.setSchedules(genSchedules);
			
			schedulesPanel.add(docInstitutionView);
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}