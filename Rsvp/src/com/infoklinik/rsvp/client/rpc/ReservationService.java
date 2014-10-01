package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ReservationSearchBean;

@RemoteServiceRelativePath("reservationService")
public interface ReservationService extends RemoteService {
	
	String sendVerificationCode(String mobile);
	
	ReservationBean addReservation(ReservationBean reservationBean);

	ReservationBean updateReservation(ReservationBean reservationBean);
	
	ReservationBean deleteReservation(ReservationBean reservationBean);
	
	List<ReservationBean> getReservations(ReservationSearchBean reservationSearch);
}
