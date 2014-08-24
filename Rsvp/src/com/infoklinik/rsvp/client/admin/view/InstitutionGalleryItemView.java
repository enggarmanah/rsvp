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
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.GalleryBean;

public class InstitutionGalleryItemView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionGalleryItemView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	GalleryBean gallery;
	
	@UiField
	SimplePanel imagePanel;
	
	@UiField
	FocusPanel updateBtn;
	
	@UiField
	FocusPanel deleteBtn;
	
	public InstitutionGalleryItemView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public GalleryBean getGallery() {
		
		return gallery;
	}

	public void setGallery(GalleryBean gallery) {
		
		this.gallery = gallery;
		Image image = new Image();
		
		if (gallery.getImageId() != null) {
			image.setUrl(Constant.IMAGE_URL + gallery.getImageId());
		}
		
		imagePanel.clear();
		imagePanel.add(image);
	}
	
	public void setUpdateBtnClickHandler(ClickHandler handler) {
		
		updateBtn.addClickHandler(handler);
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
