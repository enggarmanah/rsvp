
package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ReservationSearchBean;

public interface ReservationServiceAsync {
	
	public void sendVerificationCode(String mobile, AsyncCallback<String> callback);
	
	public void addReservation(ReservationBean reservation, AsyncCallback<ReservationBean> callback);

	public void updateReservation(ReservationBean reservation, AsyncCallback<ReservationBean> callback);
	
	public void deleteReservation(ReservationBean reservation, AsyncCallback<ReservationBean> callback);

	public void getReservations(ReservationSearchBean reservationSearch, AsyncCallback<List<ReservationBean>> callback);
}
