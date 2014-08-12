package com.infoklinik.rsvp.client.main.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.shared.Constant;

public class NotificationDlg {
	
	interface ConfirmDlgUiBinder extends UiBinder<Widget, NotificationDlg> {}
	
	private static ConfirmDlgUiBinder uiBinder = GWT.create(ConfirmDlgUiBinder.class);
	
	private static DialogBox confirmDlgBox;
	
	private static NotificationDlg notificationDlg;
	
	private static HandlerRegistration okHandlerReg;	
	
	@UiField
	static Label notificationText;
	
	@UiField
	static Image notificationImg;
	
	@UiField
	static Button okBtn;
	
	private NotificationDlg() {
		
		confirmDlgBox = new DialogBox();
		confirmDlgBox.setText("Notifikasi");
		confirmDlgBox.setStyleName("popupDialog");
		confirmDlgBox.setWidget(uiBinder.createAndBindUi(this));		
	}
	
	public static void init() {
		
		if (notificationDlg == null) {
			notificationDlg = new NotificationDlg();
			
			okBtn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					Timer timer = new Timer() {
						
						@Override
						public void run() {
							confirmDlgBox.hide();
						}
					};
					
					fadeOut();
					
					timer.schedule(Constant.FADE_TIME);
				}
			});
		}
	}
	
	public static void info(String text) {
		
		init();
		
		ClientUtil.goToTop();
		
		fadeOut();
		
		notificationImg.setUrl("images/info.png");
		notificationText.setText(text);
		confirmDlgBox.center();
		confirmDlgBox.setPopupPosition(confirmDlgBox.getPopupLeft(), 155);
		confirmDlgBox.show();
		
		fadeIn();
	}
	
	public static void warning(String text) {
		
		init();
		
		fadeOut();
		
		ClientUtil.goToTop();
		
		notificationImg.setUrl("images/warning.png");
		notificationText.setText(text);
		confirmDlgBox.center();
		confirmDlgBox.setPopupPosition(confirmDlgBox.getPopupLeft(), 155);
		confirmDlgBox.show();
		
		fadeIn();
	}
	
	public static void error(List<String> messages) {
		
		if (messages.size() == 1) {
			
			warning(messages.get(0));
		
		} else {
		
			int i = 1;
			StringBuffer sb = new StringBuffer();
			
			for (String message : messages) {
				sb.append(i + ". " + message + "\n");
				i++;
			}
			
			warning(sb.toString());
		}
	}
	
	public static void warning(String text, ClickHandler handler) {
		
		init();
		
		fadeOut();
		
		if (okHandlerReg != null) {
			okHandlerReg.removeHandler();
		}
		
		okHandlerReg = okBtn.addClickHandler(handler);
		
		ClientUtil.goToTop();
		
		notificationImg.setUrl("images/warning.png");
		notificationText.setText(text);
		confirmDlgBox.center();
		confirmDlgBox.setPopupPosition(confirmDlgBox.getPopupLeft(), 155);
		confirmDlgBox.show();
		
		fadeIn();
	}
	
	private static void fadeIn() {
		
		confirmDlgBox.removeStyleName(Constant.STYLE_FADE_OUT);
		confirmDlgBox.addStyleName(Constant.STYLE_FADE_IN);
	}
	
	private static void fadeOut() {
		
		confirmDlgBox.removeStyleName(Constant.STYLE_FADE_IN);
		confirmDlgBox.addStyleName(Constant.STYLE_FADE_OUT);
	}
}
