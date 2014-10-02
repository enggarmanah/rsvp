package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.ReservationService;
import com.infoklinik.rsvp.server.CalendarUtil;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.SmsUtil;
import com.infoklinik.rsvp.server.dao.ReservationDAO;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ReservationSearchBean;

@SuppressWarnings("serial")
public class ReservationServiceImpl extends BaseServiceServlet implements ReservationService {
	
	public String sendVerificationCode(String mobile) {
		
		String code = String.valueOf((new Date()).getTime());
		code = code.substring(code.length()-6);
		
		String message = "Silahkan input kode verifikasi : " + code + " ke sistem. -Info Klinik-";
		
		SmsUtil.sendSms(mobile, message);
		
		return code;
	}
	
	public synchronized ReservationBean addReservation(ReservationBean reservation) {
		
		ReservationDAO reservationDAO = new ReservationDAO();
		
		if (reservation.getDoctor() != null) {
			
			if (!reservationDAO.isConflictWithOtherReservation(reservation)) {
				
				reservation.setReservationCode(ServerUtil.generateReservationCode());
				
				String eventId = CalendarUtil.addCalendarEntry(reservation);
				if (eventId != null) {
					reservation.setEventId(eventId);
					reservation = reservationDAO.addReservation(reservation);
				}
				
				String message = "Reservasi kunjungan dokter telah berhasil.";
				message += "\nKode Reservasi : " + reservation.getReservationCode();
				message += "\nDokter: " + reservation.getDoctor().getNameWithTitle();
				message += "\nHari : " + ServerUtil.dateDayTimeToStr(reservation.getApptDate());
				message += "\n" + reservation.getInstitution().getName() + ", " + reservation.getInstitution().getTelephone();
						
				SmsUtil.sendSms(reservation.getPatientMobile(), message);
			}			
			
		} else if (reservation.getService() != null) {
			
			reservation.setReservationCode(ServerUtil.generateReservationCode());
			
			String message = "Registrasi telah berhasil.";
			message += "\nKode Voucher : " + reservation.getReservationCode();
			message += "\nLayanan: " + reservation.getService().getName();
			message += "\n" + reservation.getInstitution().getName() + ", " + reservation.getInstitution().getTelephone();
					
			SmsUtil.sendSms(reservation.getPatientMobile(), message);
		}
		
		return reservation;
	}
	
	public ReservationBean updateReservation(ReservationBean reservationBean) {
		
		ReservationDAO reservationDAO = new ReservationDAO();
		ReservationBean reservation = reservationDAO.updateReservation(reservationBean);
		
		return reservation;
	}
	
	public ReservationBean deleteReservation(ReservationBean reservationBean) {
		
		ReservationDAO reservationDAO = new ReservationDAO();
		ReservationBean reservation = reservationDAO.deleteReservation(reservationBean);
		
		return reservation;
	}
	
	public List<ReservationBean> getReservations(ReservationSearchBean apptSearch) {
		
		ReservationDAO reservationDao = new ReservationDAO();
		List<ReservationBean> reservations = reservationDao.getReservations(apptSearch);
		
		List<Long> ids = new ArrayList<Long>();
		for (ReservationBean instBean : reservations) {
			ids.add(instBean.getId());
		}
		
		return reservations;
	}
}
