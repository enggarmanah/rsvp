package com.infoklinik.rsvp.client.admin.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ServiceBean;

public class InstitutionServiceView extends BaseView {
	
	@UiField
	TextArea profileTa;
	
	@UiField
	TextArea scheduleTa;
	
	@UiField
	Label residentialSrvLbl;	
	
	@UiField
	ListBox residentialSrvLb;
	
	@UiField
	ListBox op24HoursLb;
	
	@UiField
	ListBox opHourStartLb;
	
	@UiField
	ListBox opHourEndLb;
	
	@UiField
	FocusPanel addServiceBtn;
	
	@UiField
	FocusPanel addInsuranceBtn;
	
	@UiField
	FlowPanel servicePanel;
	
	@UiField
	FlowPanel insurancePanel;
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionServiceView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institution;
	List<ServiceBean> services;
	List<InsuranceBean> insurances;
	
	public InstitutionServiceView() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		residentialSrvLb.addItem(Constant.YES_DESC, Constant.YES);
		residentialSrvLb.addItem(Constant.NO_DESC, Constant.NO);
		residentialSrvLb.setSelectedIndex(1);
		
		op24HoursLb.addItem(Constant.YES_DESC, Constant.YES);
		op24HoursLb.addItem(Constant.NO_DESC, Constant.NO);
		op24HoursLb.setSelectedIndex(1);
		
		for (int i = Constant.OPENING_TIME_START; i <= Constant.OPENING_TIME_END; i += Constant.OPENING_PERIOD) {
			opHourStartLb.addItem(ClientUtil.timeToStr(i), String.valueOf(i));
			opHourEndLb.addItem(ClientUtil.timeToStr(i), String.valueOf(i));
		}
	}
	
	public void setAddServiceBtnClickHandler(ClickHandler handler) {
		
		addServiceBtn.addClickHandler(handler);
	}
	
	public void setAddInsuranceBtnClickHandler(ClickHandler handler) {
		
		addInsuranceBtn.addClickHandler(handler);
	}
		
	public InstitutionBean getInstitution() {
		
		institution.setProfile(profileTa.getValue());		
		institution.setOpInfo(scheduleTa.getValue());
		institution.setResidentialService(residentialSrvLb.getValue(residentialSrvLb.getSelectedIndex()));
		institution.setOp24hours(Constant.YES.equals(op24HoursLb.getValue(op24HoursLb.getSelectedIndex())));
		institution.setOpHourStart(opHourStartLb.getValue(opHourStartLb.getSelectedIndex()));
		institution.setOpHourEnd(opHourEndLb.getValue(opHourEndLb.getSelectedIndex()));
		
		institution.setServices(services);
		institution.setInsurances(insurances);
		
		return institution;
	}
	
	public void setInstitution(InstitutionBean institution) {
		
		this.institution = institution;
		
		profileTa.setValue(institution.getProfile());
		scheduleTa.setValue(institution.getOpInfo());
		
		ClientUtil.setSelectedIndex(residentialSrvLb, institution.getResidentialService());
		ClientUtil.setSelectedIndex(op24HoursLb, institution.isOp24hours() ? Constant.YES : Constant.NO);
		
		if (InstitutionBean.CATEGORY_CLINIC.equals(institution.getCategory()) || 
			InstitutionBean.CATEGORY_HOSPITAL.equals(institution.getCategory())) {
			residentialSrvLb.setSelectedIndex(1);
			residentialSrvLb.setEnabled(false);
		} else {
			residentialSrvLb.setEnabled(true);
		}
		
		if (InstitutionBean.CATEGORY_HOSPITAL.equals(institution.getCategory())) {
			op24HoursLb.setSelectedIndex(0);
			op24HoursLb.setEnabled(false);
		} else {
			op24HoursLb.setEnabled(true);
		}
		
		if (Constant.YES.equals(institution.isOp24hours())) {
			opHourStartLb.setSelectedIndex(0);
			opHourStartLb.setEnabled(false);
			opHourEndLb.setSelectedIndex(0);
			opHourEndLb.setEnabled(false);
		} else {
			opHourStartLb.setEnabled(true);
			opHourEndLb.setEnabled(true);
		}
		
		ClientUtil.setSelectedIndex(op24HoursLb, institution.isOp24hours() ? Constant.YES : Constant.NO);
		ClientUtil.setSelectedIndex(opHourStartLb, institution.getOpHourStart());
		ClientUtil.setSelectedIndex(opHourEndLb, institution.getOpHourEnd());
	}
	
	public void setServices(List<GenericBean<ServiceBean>> services) {
		
		this.services = new ArrayList<ServiceBean>(); 
				
		servicePanel.clear();
		
		for (GenericBean<ServiceBean> service : services) {
			
			InstitutionItemView itemView = new InstitutionItemView();
			itemView.setService(service);
			servicePanel.add(itemView);
			
			this.services.add(service.getBean());
		}
	}
	
	public void setInsurances(List<GenericBean<InsuranceBean>> insurances) {
		
		this.insurances = new ArrayList<InsuranceBean>();
		
		insurancePanel.clear();
		
		for (GenericBean<InsuranceBean> insurance : insurances) {
			
			InstitutionItemView itemView = new InstitutionItemView();
			itemView.setInsurance(insurance);
			insurancePanel.add(itemView);
			
			this.insurances.add(insurance.getBean());
		}
	}
	
	public void setOp24HoursChangeHandler(ChangeHandler handler) {
		
		op24HoursLb.addChangeHandler(handler);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
