package com.infoklinik.rsvp.client.main.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.shared.Constant;

public class ProgressDlg {
	
	interface ProgressBarUiBinder extends UiBinder<Widget, ProgressDlg> {}
	
	private static ProgressBarUiBinder uiBinder = GWT.create(ProgressBarUiBinder.class);
	
	private static DialogBox progressDlgBox;
	
	private static ProgressDlg progressBar;
	
	@UiField
	static Label progressText;
	
	@UiField
	static Image progressImage;
	
	private static String INFO = "Sistem sedang memproses permintaan anda.";
	private static String INFO_PERCENTAGE = "Sistem sedang memproses permintaan anda .... ";
	private static String SUCCESS = "Sistem telah berhasil memproses permintaan anda.";
	private static String FAIL = "Sistem gagal memproses permintaan anda.";
	
	private static Timer timer;
	private static Timer changeTimer;
	private static Timer fadeTimer;
	private static Timer hideTimer;
	private static boolean isSuccess = false;
	private static boolean isHideInProgress = false;
	
	private static int POSITION_TOP = 155;
	
	private ProgressDlg() {
		
		progressDlgBox = new DialogBox();
		progressDlgBox.setText("Loading ...");
		progressDlgBox.setStyleName("popupDialog");
		progressDlgBox.setWidget(uiBinder.createAndBindUi(this));
		
		timer = new Timer() {
			
			@Override
			public void run() {
				
				changeTimer.schedule(Constant.FADE_TIME);
				
				fadeOut();
			}
	    };
	    
	    fadeTimer = new Timer() {
			
			@Override
			public void run() {
				
				fadeOut();
				
				hideTimer.schedule(Constant.FADE_TIME);
			}
	    };
	    
	    hideTimer = new Timer() {
			
			@Override
			public void run() {
				
				progressDlgBox.hide();
				
				progressDlgBox.setText("Loading ...");
				progressText.setText(INFO);
				progressImage.setUrl("images/loading.gif");
				isHideInProgress = false;
			}
		};
	    
	    changeTimer = new Timer() {
			
			@Override
			public void run() {
				
				progressDlgBox.setText("Informasi");
				
				if (isSuccess) {
					progressImage.setUrl("images/success.png");
					progressText.setText(SUCCESS);
				} else {
					progressImage.setUrl("images/warning.png");
					progressText.setText(FAIL);
				}
				
				ClientUtil.goToTop();
				
				progressDlgBox.center();
				progressDlgBox.setPopupPosition(progressDlgBox.getPopupLeft(), POSITION_TOP);
				progressDlgBox.show();
				
				fadeIn();
				
				fadeTimer.schedule(Constant.DISPLAY_TIME);
			}
	    };
	}
	
	private static void fadeIn() {
		
		progressDlgBox.removeStyleName(Constant.STYLE_FADE_OUT);
		progressDlgBox.addStyleName(Constant.STYLE_FADE_IN);
	}
	
	private static void fadeOut() {
		
		progressDlgBox.removeStyleName(Constant.STYLE_FADE_IN);
		progressDlgBox.addStyleName(Constant.STYLE_FADE_OUT);
	}
	
	public static void hide() {
		
		if (!isHideInProgress) {
			fadeTimer.schedule(Constant.DISPLAY_TIME);
		}
	}
	
	public static void hidePrompt() {
		
		if (!isHideInProgress) {
			progressDlgBox.hide();
		}
	}
	
	private static void init() {
		
		if (progressBar == null) {
			progressBar = new ProgressDlg();
		}
	}
	
	
	public static void show() {
		
		init();
		
		ClientUtil.goToTop();
		
		fadeOut();
		
		progressText.setText(INFO);
		progressImage.setUrl("images/loading.gif");
		progressDlgBox.center();
		progressDlgBox.setPopupPosition(progressDlgBox.getPopupLeft(), POSITION_TOP);
		progressDlgBox.show();
		
		fadeIn();
	}
	
	public static void showPercentage() {
		
		show();
		
		progressText.setText(INFO_PERCENTAGE + "0 %");
		progressDlgBox.center();
	}
	
	public static void success() {	
		
		isHideInProgress = true;
		isSuccess = true;
		
		timer.schedule(Constant.DISPLAY_TIME);
		center();
	}
	
	public static void failure() {	
		
		isHideInProgress = true;
		isSuccess = false;
		
		timer.schedule(Constant.DISPLAY_TIME);
		center();
	}
	
	public static void failure(String message) {	
		
		isHideInProgress = true;
		isSuccess = false;
		progressText.setText(message);
		
		timer.schedule(Constant.DISPLAY_TIME);
		center();
	}
	
	public static void setPercentage(int percent) {
		
		progressText.setText(INFO_PERCENTAGE + percent + " %");
		center();
	}
	
	private static void center() {
		
		int top = progressDlgBox.getAbsoluteTop();
		progressDlgBox.center();
		progressDlgBox.setPopupPosition(progressDlgBox.getPopupLeft(), top);
	}
}
