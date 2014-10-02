package com.infoklinik.rsvp.client.appt.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IReservationServiceView;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.Constant;

public class ReservationServiceView extends BaseView implements IReservationServiceView {
	
	private DialogBox dialogBox;
	
	@UiField
	Label serviceLb;
	
	@UiField
	Label institutionLb;
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, ReservationServiceView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	ReservationBean reservation;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
		dialogBox.setText("Konfirmasi Layanan");
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return dialogBox;
	}
		
	public ReservationBean getReservation() {
		
		return reservation;
	}
	
	public void setReservation(ReservationBean reservation) {
		
		this.reservation = reservation;
		
		serviceLb.setText(reservation.getService().getName());
		institutionLb.setText(reservation.getInstitution().getName());
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
	
	public void show() {
		
		goToTop();
		
		fadeOut();
		
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft(), Constant.POPUP_L1_TOP);
		dialogBox.show();
		
		fadeIn();
	}
	
	public void showLv2() {
		
		goToTop();
		
		fadeOut();
		
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft() + Constant.POPUP_L2_LEFT, Constant.POPUP_L2_TOP);
		dialogBox.show();
		
		fadeIn();
	}
	
	public void hide() {
		
		fadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				dialogBox.hide();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}
}