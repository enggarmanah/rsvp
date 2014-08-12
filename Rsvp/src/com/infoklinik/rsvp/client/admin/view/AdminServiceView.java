package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminServiceView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;

public class AdminServiceView extends BaseView implements IAdminServiceView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminServiceView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	TextBox nameTb;
	
	@UiField
	ListBox serviceTypeLb;
	
	@UiField
	TextBox priceTb;
	
	@UiField
	TextArea descriptionTa;
	
	@UiField
	ListBox isPromoLb;
	
	@UiField
	TextBox promoPriceTb;
	
	@UiField
	DateBox promoStartDateDb;
	
	@UiField
	DateBox promoEndDateDb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	DialogBox dialogBox;
	
	List<ServiceTypeBean> list;
	
	ServiceBean serviceBean;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
		
		isPromoLb.addItem(Constant.YES_DESC, Constant.YES);
		isPromoLb.addItem(Constant.NO_DESC, Constant.NO);
		isPromoLb.setSelectedIndex(1);
		
		DateTimeFormat dtf = ClientUtil.getDateTime(ClientUtil.DATE_TIME_FORMAT_DATE);
		promoStartDateDb.setFormat(new DateBox.DefaultFormat(dtf));
		promoEndDateDb.setFormat(new DateBox.DefaultFormat(dtf));
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	public void show() {
		
		dlgFadeOut();
		
		dialogBox.setText("Tambah Layanan Baru");
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft() + Constant.POPUP_L2_LEFT, Constant.POPUP_L2_TOP);
		dialogBox.show();
		
		dlgFadeIn();
	}
	
	public void hide() {
		
		dlgFadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				dialogBox.hide();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}

	public void setServiceTypes(List<ServiceTypeBean> list) {
		
		serviceTypeLb.clear();
		
		serviceTypeLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (ServiceTypeBean serviceType : list) {
			serviceTypeLb.addItem(serviceType.getName(), String.valueOf(serviceType.getId()));
		}
	}
	
	public ServiceBean getService() {
		
		serviceBean.setName(nameTb.getText());
		
		if (serviceTypeLb.getSelectedIndex() != 0) {
			
			ServiceTypeBean serviceType = serviceBean.getServiceType();
			serviceType = serviceType == null ? new ServiceTypeBean() : serviceType; 
			serviceType.setId(Long.valueOf(serviceTypeLb.getValue(serviceTypeLb.getSelectedIndex())));
			serviceType.setName(serviceTypeLb.getItemText(serviceTypeLb.getSelectedIndex()));
			serviceBean.setServiceType(serviceType);
		}
		
		serviceBean.setPrice(priceTb.getValue());
		serviceBean.setDescription(descriptionTa.getValue());
		serviceBean.setPromo(Constant.YES.equals(isPromoLb.getValue(isPromoLb.getSelectedIndex())));
		serviceBean.setPromoPrice(promoPriceTb.getValue());
		serviceBean.setPromoStartDate(promoStartDateDb.getValue());
		serviceBean.setPromoEndDate(promoEndDateDb.getValue());
		
		return serviceBean;
	}
	
	public void setService(ServiceBean serviceBean) {
		
		this.serviceBean = serviceBean;
		ServiceTypeBean serviceType = serviceBean.getServiceType();
		
		nameTb.setText(serviceBean.getName());
		
		if (serviceType != null) {
			ClientUtil.setSelectedIndex(serviceTypeLb, String.valueOf(serviceType.getId()));
		}
		
		priceTb.setValue(serviceBean.getPrice());
		descriptionTa.setValue(serviceBean.getDescription());
		ClientUtil.setSelectedIndex(isPromoLb, serviceBean.isPromo() ? Constant.YES : Constant.NO);
		
		if (serviceBean.isPromo()) {
			promoPriceTb.setEnabled(true);
			promoStartDateDb.setEnabled(true);
			promoEndDateDb.setEnabled(true);
		} else {
			promoPriceTb.setEnabled(false);
			promoStartDateDb.setEnabled(false);
			promoEndDateDb.setEnabled(false);
		}
		
		promoPriceTb.setValue(serviceBean.getPromoPrice());
		promoStartDateDb.setValue(serviceBean.getPromoStartDate());
		promoEndDateDb.setValue(serviceBean.getPromoEndDate());
	}
	
	public void setPromoChangeHandler(ChangeHandler handler) {
		
		isPromoLb.addChangeHandler(handler);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
}
