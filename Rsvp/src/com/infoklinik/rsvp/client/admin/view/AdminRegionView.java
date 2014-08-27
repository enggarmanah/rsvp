package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.SuggestionOracle;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminRegionView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.RegionBean;
import com.infoklinik.rsvp.shared.SuggestParameter;

public class AdminRegionView extends BaseView implements IAdminRegionView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminRegionView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	TextBox nameTb;
	
	@UiField(provided = true)
	SuggestBox citySb = new SuggestBox(new SuggestionOracle(new SuggestParameter(SuggestionOracle.SEARCH_CITY)));
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	DialogBox dialogBox;
	
	List<RegionBean> list;
	
	RegionBean region;
	
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

	public RegionBean getRegion() {
		
		region.setName(nameTb.getText());
		
		if (region.getCity() != null && 
			!region.getCity().getName().equals(citySb.getValue())) {
			region.setCity(null);
		} 
		
		return region;
	}
	
	public void setRegion(RegionBean region) {
		
		this.region = region;
		
		citySb.setValue(Constant.EMPTY_STRING);
		if (region.getCity() != null) {
			citySb.setValue(region.getCity().getName());
		}
		
		if (region.getId() == null) {
			dialogBox.setText("Tambah Asuransi Baru");
		} else {
			dialogBox.setText("Perubahan Data Asuransi");
		}
		
		nameTb.setText(region.getName());
	}
	
	public void setCitySelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler) {
		
		citySb.addSelectionHandler(handler);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
}
