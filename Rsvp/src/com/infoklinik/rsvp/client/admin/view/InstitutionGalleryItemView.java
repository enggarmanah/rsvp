package com.infoklinik.rsvp.client.admin.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;

public class InstitutionGalleryItemView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionGalleryItemView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	SimplePanel imagePanel;
	
	@UiField
	FocusPanel deleteBtn;
	
	public InstitutionGalleryItemView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setImage(Image image) {
		
		imagePanel.add(image);
	}
	
	public void setDeleteBtnClickHandler(ClickHandler handler) {
		
		deleteBtn.addClickHandler(handler);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
