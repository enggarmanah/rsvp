package com.infoklinik.rsvp.client.admin.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminStatisticView;

public class AdminStatisticView extends BaseView implements IAdminStatisticView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminStatisticView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	public void createView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
	
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
