package com.infoklinik.rsvp.client.admin.view;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.ScheduleBean;

public class InstitutionDoctorView extends BaseView {
	
	@UiField
	FlowPanel doctorsPanel;
	
	@UiField
	FocusPanel addDoctorBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionDoctorView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	HashMap<GenericBean<DoctorBean>, InstitutionDoctorItemView> doctorViewMap = new HashMap<GenericBean<DoctorBean>, InstitutionDoctorItemView>();
	HashMap<DoctorBean, HashMap<ScheduleBean, HandlerManager>> doctorScheduleHandlerMgrs;
	
	public InstitutionDoctorView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setDoctorScheduleHandlerMgr(HashMap<DoctorBean, HashMap<ScheduleBean, HandlerManager>> doctorScheduleHandlerMgrs) {
		
		this.doctorScheduleHandlerMgrs = doctorScheduleHandlerMgrs;
	}
	
	public void setDoctors(List<GenericBean<DoctorBean>> doctors) {
		
		doctorsPanel.clear();
		
		for (GenericBean<DoctorBean> doctor : doctors) {
			
			HashMap<ScheduleBean, HandlerManager> scheduleHandlerMgr = doctorScheduleHandlerMgrs.get(doctor.getBean());
			
			InstitutionDoctorItemView doctorItemView = new InstitutionDoctorItemView();
			doctorItemView.setScheduleHandlerMgrs(scheduleHandlerMgr);
			doctorItemView.setDoctor(doctor);
			
			doctorsPanel.add(doctorItemView);
			doctorViewMap.put(doctor, doctorItemView);
		}
	}
	
	public void addDoctor(GenericBean<DoctorBean> doctor) {
		
		HashMap<ScheduleBean, HandlerManager> scheduleHandlerMgr = doctorScheduleHandlerMgrs.get(doctor.getBean());
		
		InstitutionDoctorItemView doctorItemView = new InstitutionDoctorItemView();
		doctorItemView.setScheduleHandlerMgrs(scheduleHandlerMgr);
		doctorItemView.setDoctor(doctor);
		
		doctorsPanel.add(doctorItemView);
		doctorViewMap.put(doctor, doctorItemView);
	}
	
	public void updateDoctor(GenericBean<DoctorBean> doctor) {
		
		HashMap<ScheduleBean, HandlerManager> scheduleHandlerMgr = doctorScheduleHandlerMgrs.get(doctor.getBean());
		
		InstitutionDoctorItemView doctorItemView = doctorViewMap.get(doctor);
		doctorItemView.setScheduleHandlerMgrs(scheduleHandlerMgr);
		doctorItemView.setDoctor(doctor);
	}
	
	public void deleteDoctor(GenericBean<DoctorBean> doctor) {
		
		InstitutionDoctorItemView doctorItemView = doctorViewMap.get(doctor);
		doctorsPanel.remove(doctorItemView);
	}
	
	public void setAddDoctorBtnClickHandler(ClickHandler handler) {
		
		addDoctorBtn.addClickHandler(handler);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
