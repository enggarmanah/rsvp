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
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminStreetView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.StreetBean;
import com.infoklinik.rsvp.shared.SuggestParameter;

public class AdminStreetView extends BaseView implements IAdminStreetView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminStreetView> {}
	
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
	
	List<StreetBean> list;
	
	StreetBean street;
	
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

	public StreetBean getStreet() {
		
		street.setName(nameTb.getText());
		
		if (street.getCity() != null && 
			!street.getCity().getName().equals(citySb.getValue())) {
			street.setCity(null);
		} 
		
		return street;
	}
	
	public void setStreet(StreetBean street) {
		
		this.street = street;
		
		citySb.setValue(Constant.EMPTY_STRING);
		if (street.getCity() != null) {
			citySb.setValue(street.getCity().getName());
		}
		
		if (street.getId() == null) {
			dialogBox.setText("Tambah Jalan Baru");
		} else {
			dialogBox.setText("Perubahan Data Jalan");
		}
		
		nameTb.setText(street.getName());
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
