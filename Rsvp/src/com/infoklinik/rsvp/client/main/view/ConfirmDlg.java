package com.infoklinik.rsvp.client.main.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.ClientUtil;

public class ConfirmDlg {
	
	interface ConfirmDlgUiBinder extends UiBinder<Widget, ConfirmDlg> {}
	
	private static ConfirmDlgUiBinder uiBinder = GWT.create(ConfirmDlgUiBinder.class);
	
	private static DialogBox confirmDlgBox;
	
	private static ConfirmDlg confirmDlg;
	
	private static HandlerRegistration okHandlerReg;	
	private static HandlerRegistration cancelHandlerReg;
	
	@UiField
	static Label confirmText;
	
	@UiField
	static Button okBtn;
	
	@UiField
	static Button cancelBtn;
	
	private ConfirmDlg() {
		
		confirmDlgBox = new DialogBox();
		confirmDlgBox.setText("Konfirmasi");
		confirmDlgBox.setStyleName("popupDialog");
		confirmDlgBox.setWidget(uiBinder.createAndBindUi(this));
	}
	
	private static void init(String text) {
		
		if (confirmDlg == null) {
			confirmDlg = new ConfirmDlg();
			
			okBtn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					confirmDlgBox.hide();
				}
			});
			
			cancelBtn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					confirmDlgBox.hide();
				}
			});
		}
		
		ClientUtil.goToTop();
		
		confirmText.setText(text);
		confirmDlgBox.center();
		confirmDlgBox.setPopupPosition(confirmDlgBox.getPopupLeft(), 170);
		confirmDlgBox.show();
	}
	
	public static void confirm(String text, ClickHandler handler) {
		
		init(text);
		
		if (okHandlerReg != null) {
			okHandlerReg.removeHandler();
		}
		
		okHandlerReg = okBtn.addClickHandler(handler);
	}
		
	public static void confirm(String text, ClickHandler okHandler, ClickHandler cancelHandler) {
		
		init(text);
		
		if (okHandlerReg != null) {
			okHandlerReg.removeHandler();
		}
		
		okHandlerReg = okBtn.addClickHandler(okHandler);
		
		if (cancelHandlerReg != null) {
			cancelHandlerReg.removeHandler();
		}
		
		cancelHandlerReg = cancelBtn.addClickHandler(cancelHandler);
	}
}
