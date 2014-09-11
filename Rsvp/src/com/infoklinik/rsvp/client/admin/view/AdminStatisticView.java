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
	
	@UiField
	Label searchClinicLb;
	
	@UiField
	Label searchHospitalLb;
	
	@UiField
	Label searchLabLb;
	
	@UiField
	Label searchPharmacyLb;
	
	@UiField
	Label searchDoctorLb;
	
	@UiField
	Label searchServiceLb;
	
	@UiField
	Label searchTotalLb;
	
	@UiField
	Label searchMethodNameLb;
	
	@UiField
	Label searchMethodStreetLb;
	
	@UiField
	Label searchMethodRegionLb;
	
	@UiField
	Label searchMethodDistanceLb;
	
	@UiField
	Label searchMethodTotalLb;
	
	public void createView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setDataStatistic(Map<String, Long> map) {
		
		Long total = Long.valueOf(0);
		
		Long count = map.get(Constant.CATEGORY_CLINIC);
		dataClinicLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.CATEGORY_HOSPITAL);
		dataHospitalLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.CATEGORY_LABORATORY);
		dataLabLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.CATEGORY_PHARMACY);
		dataPharmacyLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.CATEGORY_DOCTOR);
		dataDoctorLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.CATEGORY_SERVICE);
		dataServiceLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.CATEGORY_INSURANCE);
		dataInsuranceLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		dataTotalLb.setText(ClientUtil.numberToStr(total));
	}
	
	public void setSearchTypeStatistic(Map<String, Long> map) {
		
		Long total = Long.valueOf(0);
		
		Long count = map.get(Constant.SEARCH_CLINIC);
		if (count == null) {
			count = Long.valueOf(0);
		}
		
		searchClinicLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.SEARCH_HOSPITAL);
		if (count == null) {
			count = Long.valueOf(0);
		}
		
		searchHospitalLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.SEARCH_LAB);
		if (count == null) {
			count = Long.valueOf(0);
		}
		
		searchLabLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.SEARCH_PHARMACY);
		if (count == null) {
			count = Long.valueOf(0);
		}
		
		searchPharmacyLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.SEARCH_DOCTOR);
		if (count == null) {
			count = Long.valueOf(0);
		}
		
		searchDoctorLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.SEARCH_SERVICE);
		if (count == null) {
			count = Long.valueOf(0);
		}
		
		searchServiceLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		searchTotalLb.setText(ClientUtil.numberToStr(total));
	}
	
	public void setSearchMethodStatistic(Map<String, Long> map) {
		
		Long total = Long.valueOf(0);
		
		Long count = map.get(Constant.SEARCH_BY_NAME);
		searchMethodNameLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.SEARCH_BY_STREET);
		searchMethodStreetLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.SEARCH_BY_REGION);
		searchMethodRegionLb.setText(ClientUtil.numberToStr(count));
		total += count;
		
		count = map.get(Constant.SEARCH_BY_DISTANCE);
		searchMethodDistanceLb.setText(ClientUtil.numberToStr(count));
		total += count;
				
		searchMethodTotalLb.setText(ClientUtil.numberToStr(total));
	}
	
	public Widget asWidget() {
	
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
