package com.infoklinik.rsvp.client.listing.view;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.listing.presenter.interfaces.ISearchResultView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;

public class SearchResultView extends BaseView implements ISearchResultView {
	
	interface ModuleUiBinder extends UiBinder<Widget, SearchResultView> {}
	
	@UiField
	VerticalPanel searchPanel;
	
	@UiField
	FlowPanel pagePanel;
	
	@UiField
	Label fromIndexLb;
	
	@UiField
	Label toIndexLb;
	
	@UiField
	Label countLb;
	
	@UiField
	Label searchLocationLb;
	
	List<GenericBean<InstitutionBean>> institutions;
	List<GenericBean<DoctorBean>> doctors;
	List<GenericBean<ServiceBean>> services;
	
	HashMap<Long, HashMap<Long, HandlerManager>> doctorInstHandlerMgrs;
	HashMap<Long, HashMap<Long, HashMap<Long, HandlerManager>>> doctorInstScheduleHandlerMgrs;
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	private static String currentSearch;
	
	public void createView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setSearchLocationHandler(ClickHandler handler) {
		
		searchLocationLb.addClickHandler(handler);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	@Override
	public void fadeIn() {
		
		searchPanel.setStyleName(Constant.STYLE_FADE_IN);
	}
	
	@Override
	public void fadeOut() {
		
		searchPanel.setStyleName(Constant.STYLE_FADE_OUT);
	}
	
	@Override
	protected FlowPanel getPagePanel() {
		
		return pagePanel;
	}
	
	public void setInstitutions(List<GenericBean<InstitutionBean>> institutions) {
		
		currentSearch = Constant.SEARCH_INSTITUTION;
		
		this.institutions = institutions;
		this.size = institutions.size();
		
		loadNavigation();
		
		setPage(1);
	}
	
	public void setDoctors(List<GenericBean<DoctorBean>> doctors) {
		
		currentSearch = Constant.SEARCH_DOCTOR;
		
		this.doctors = doctors;
		this.size = doctors.size();
		
		loadNavigation();
		
		setPage(1);
	}
	
	public void setServices(List<GenericBean<ServiceBean>> services) {
		
		currentSearch = Constant.SEARCH_SERVICE;
		
		this.services = services;
		this.size = services.size();
		
		loadNavigation();
		
		setPage(1);
	}
	
	public void setDoctorInstHandlerMgrs(HashMap<Long, HashMap<Long, HandlerManager>> doctorInstHandlerMgrs) {
		
		this.doctorInstHandlerMgrs = doctorInstHandlerMgrs;
	}
	
	public void setDoctorInstScheduleHandlerMgrs(HashMap<Long, HashMap<Long, HashMap<Long, HandlerManager>>> doctorInstScheduleHandlerMgrs) {
		
		this.doctorInstScheduleHandlerMgrs = doctorInstScheduleHandlerMgrs;
	}
	
	@Override
	protected void clear() {
		
		searchPanel.clear();
	}
	
	@Override
	public void loadContent() {
		
		int fromIndex = getFromIndex();
		int toIndex = getToIndex();
		
		fromIndexLb.setText(String.valueOf(fromIndex + 1));
		toIndexLb.setText(String.valueOf(toIndex));
		
		int index = fromIndex + 1;
		
		if (Constant.SEARCH_INSTITUTION.equals(currentSearch)) {
			
			countLb.setText(String.valueOf(institutions.size()));
			
			List<GenericBean<InstitutionBean>> sublist = institutions.subList(fromIndex, toIndex);
			
			for (GenericBean<InstitutionBean> genericBean : sublist) {
				
				InstitutionBean institutionBean = genericBean.getBean();
				HandlerManager handlerMgr = genericBean.getHandlerMgr();
				
				ResultInstitutionView instView = new ResultInstitutionView();
				instView.setInstitution(index, institutionBean, handlerMgr);
				
				searchPanel.add(instView);
				
				index++;
			}
			
		} else if (Constant.SEARCH_DOCTOR.equals(currentSearch)) {
			
			countLb.setText(String.valueOf(doctors.size()));
			
			List<GenericBean<DoctorBean>> sublist = doctors.subList(fromIndex, toIndex);
			
			for (GenericBean<DoctorBean> genericBean : sublist) {
				
				DoctorBean doctorBean = genericBean.getBean();
				
				HandlerManager handlerMgr = genericBean.getHandlerMgr();
				
				HashMap<Long, HandlerManager> instHandlerMgrs = doctorInstHandlerMgrs.get(doctorBean.getId());
				HashMap<Long, HashMap<Long, HandlerManager>> instScheduleHandlerMgrs = doctorInstScheduleHandlerMgrs.get(doctorBean.getId());
				
				ResultDoctorView doctorView = new ResultDoctorView();
				doctorView.setDoctor(index, doctorBean, handlerMgr, instHandlerMgrs, instScheduleHandlerMgrs);
				
				searchPanel.add(doctorView);
				
				index++;
			}
			
		} else if (Constant.SEARCH_SERVICE.equals(currentSearch)) {
			
			countLb.setText(String.valueOf(services.size()));
			
			List<GenericBean<ServiceBean>> sublist = services.subList(fromIndex, toIndex);
			
			for (GenericBean<ServiceBean> genericBean : sublist) {
				
				ResultServiceView serviceView = new ResultServiceView();
				serviceView.setService(index, genericBean);
				
				searchPanel.add(serviceView);
				
				index++;
			}
		} 
	}
}