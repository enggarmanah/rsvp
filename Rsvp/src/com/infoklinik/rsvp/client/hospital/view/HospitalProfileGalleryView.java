package com.infoklinik.rsvp.client.hospital.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.GalleryBean;

public class HospitalProfileGalleryView extends BaseView {
	
	@UiField
	SimplePanel imagePanel;
	
	@UiField
	Image galleryImg;
	
	int index;
	int maxIndex;
	
	interface ModuleUiBinder extends UiBinder<Widget, HospitalProfileGalleryView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	List<GalleryBean> galleries;
	
	public HospitalProfileGalleryView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setGalleries(final List<GalleryBean> galleries) {
		
		this.galleries = galleries;
		
		if (galleries.size() > 0) {
			imagePanel.setVisible(true);
		} else {
			imagePanel.setVisible(false);
			return;
		}
		
		maxIndex = galleries.size() - 1;
		index = 0;
		
		showGallery(index);
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				index = index < maxIndex ? index + 1 : 0;
				showGallery(index);
			}
		};
		
		timer.scheduleRepeating(Constant.GALLERY_TIME * 1000);
	}
	
	private void showGallery(final int index) {
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {

				GalleryBean gallery = galleries.get(index);
				galleryImg.setUrl("/image?id=" + gallery.getImage_id());
				imagePanel.setStyleName(Constant.STYLE_FADE_IN);
			}
		};
		
		imagePanel.setStyleName(Constant.STYLE_FADE_OUT);
		timer.schedule(1 * 1000);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
