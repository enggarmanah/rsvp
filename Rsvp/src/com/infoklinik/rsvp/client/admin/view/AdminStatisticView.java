package com.infoklinik.rsvp.client.admin.view;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminStatisticView;
import com.infoklinik.rsvp.shared.Constant;

public class AdminStatisticView extends BaseView implements IAdminStatisticView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminStatisticView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Label dataClinicLb;
	
	@UiField
	Label dataHospitalLb;
	
	@UiField
	Label dataLabLb;
	
	@UiField
	Label dataPharmacyLb;
	
	@UiField
	Label dataDoctorLb;
	
	@UiField
	Label dataServiceLb;
	
	@UiField
	Label dataInsuranceLb;
	
	@UiField
	Label dataTotalLb;
	
	public void createView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setDataCount(Map<String, Long> map) {
		
		Long total = Long.valueOf(0);
		
		Long count = map.get(Constant.CATEGORY_CLINIC);
		dataClinicLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.CATEGORY_HOSPITAL);
		dataHospitalLb.setText(ClientUtil.numberToStr(count));
		
		count = map.get(Constant.CATEGORY_LABORATORY);
		dataLabLb.setText(ClientUtil.numberToStr(count));
		
		count = map.get(Constant.CATEGORY_HOSPITAL);
		dataHospitalLb.setText(ClientUtil.numberToStr(count));
		
		count = map.get(Constant.CATEGORY_PHARMANCY);
		dataPharmacyLb.setText(ClientUtil.numberToStr(count));
		
		count = map.get(Constant.CATEGORY_DOCTOR);
		dataDoctorLb.setText(ClientUtil.numberToStr(count));
		
		count = map.get(Constant.CATEGORY_SERVICE);
		dataServiceLb.setText(ClientUtil.numberToStr(count));
		
		count = map.get(Constant.CATEGORY_INSURANCE);
		dataInsuranceLb.setText(ClientUtil.numberToStr(count));
		
		dataTotalLb.setText(ClientUtil.numberToStr(count));
	}
	
	public Widget asWidget() {
	
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
