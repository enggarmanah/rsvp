package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminServiceTypeView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ServiceTypeBean;

public class AdminServiceTypeView extends BaseView implements IAdminServiceTypeView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminServiceTypeView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	TextBox nameTb;
	
	@UiField
	ListBox categoryLb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	DialogBox dialogBox;
	
	List<ServiceTypeBean> list;
	
	ServiceTypeBean serviceType;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	public void show() {
		
		goToTop();
		
		dlgFadeOut();
		
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

	public void setCategories(List<String> list) {
		
		categoryLb.clear();
		categoryLb.addItem(Constant.OPTION_PLS_SELECT_CODE_DESC, Constant.OPTION_PLS_SELECT_CODE);
		
		for (String value : list) {
			categoryLb.addItem(value, value);
		}
	}
	
	public ServiceTypeBean getServiceTypeBean() {
		
		serviceType.setName(nameTb.getText());
		serviceType.setCategory(categoryLb.getValue(categoryLb.getSelectedIndex()));
		
		return serviceType;
	}
	
	public void setServiceTypeBean(ServiceTypeBean serviceType) {
		
		if (serviceType.getId() == null) {
			dialogBox.setText("Tambah Layanan Baru");
		} else {
			dialogBox.setText("Perubahan Data Layanan");
		}
		
		this.serviceType = serviceType;
		
		nameTb.setText(serviceType.getName());
		ClientUtil.setSelectedIndex(categoryLb, serviceType.getCategory());
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
}
