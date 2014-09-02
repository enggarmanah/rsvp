package com.infoklinik.rsvp.client.appt.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.appt.AppointmentEventBus;
import com.infoklinik.rsvp.client.appt.presenter.interfaces.IAppointmentPatientInfoView;
import com.infoklinik.rsvp.client.appt.view.AppointmentPatientInfoView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.AppointmentServiceAsync;
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AppointmentPatientInfoView.class)
public class AppointmentPatientInfoPresenter extends LazyPresenter<IAppointmentPatientInfoView, AppointmentEventBus> {
	
	@Inject
	AppointmentServiceAsync appointmentService;
	
	AppointmentBean appointment;
	String verificationCode;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	public void onGetPatientInfo(AppointmentBean appointment) {
		verificationCode = appointment.getVerificationCode();
		this.appointment = appointment;
		view.setAppointment(appointment);
		view.show();
	}
	
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				AppointmentBean appointment = view.getAppointment();
				
				if (!ClientUtil.isEmpty(verificationCode) && !verificationCode.equals(appointment.getVerificationCode())) {
					NotificationDlg.warning(Message.ERR_INVALID_VERIFICATION_CODE);
				} else {
					addAppointment();
				}
			}
		});
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	private void addAppointment() {
		
		ProgressDlg.show();
		
		appointmentService.addAppointment(appointment, new AsyncCallback<AppointmentBean>() {
			
			@Override
			public void onSuccess(AppointmentBean result) {
				
				ProgressDlg.hide();
								
				appointment = result;
				
				if (appointment.getId() != null) {
					view.hide();
					NotificationDlg.info("Reservasi kunjungan dokter telah berhasil. \nKode reservasi \"" + 
						appointment.getReservationCode() + "\" telah dikirim ke handphone anda.");
				} else {
					NotificationDlg.warning(Message.ERR_APPT_NOT_AVAILABLE);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
}