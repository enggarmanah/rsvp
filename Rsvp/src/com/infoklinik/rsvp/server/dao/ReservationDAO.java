package com.infoklinik.rsvp.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.infoklinik.rsvp.server.PersistenceManager;
import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.server.model.Reservation;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.ReservationSearchBean;
import com.infoklinik.rsvp.shared.Constant;

public class ReservationDAO {
	
	public ReservationBean addReservation(ReservationBean reservationBean) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		Reservation reservation = new Reservation();
		reservation.setBean(reservationBean, em);
		reservation.setAppt_create_date(new Date());
		
		em.persist(reservation);
		em.refresh(reservation);
		
		reservationBean =  reservation.getBean();
		
		em.close();

		return reservationBean;
	}
	
	public ReservationBean updateReservation(ReservationBean reservationBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Reservation reservation = em.find(Reservation.class, reservationBean.getId());
		reservation.setBean(reservationBean, em);

		em.close();
		
		return reservationBean;
	}
	
	public ReservationBean deleteReservation(ReservationBean reservationBean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Reservation reservation = em.find(Reservation.class, reservationBean.getId());
		em.remove(reservation);

		em.close();
		
		return reservationBean;
	}
	
	public boolean isConflictWithOtherReservation(ReservationBean reservation) {
		
		ReservationSearchBean apptSearch = new ReservationSearchBean();
		apptSearch.setDoctorId(reservation.getDoctor().getId());
		apptSearch.setInstId(reservation.getInstitution().getId());
		apptSearch.setApptDate(reservation.getApptDate());
		
		List<ReservationBean> reservations = getReservations(apptSearch);
		
		if (reservations.size() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<ReservationBean> getReservations(ReservationSearchBean reservationSearch) {

		List<ReservationBean> list = new ArrayList<ReservationBean>();
		
		ArrayList<String> joins = new ArrayList<String>();
		ArrayList<String> filters = new ArrayList<String>();
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT r FROM Reservation r");
		
		if (reservationSearch.getDoctorId() != null) {
			joins.add(", Doctor d");
			filters.add("r.doctor = d AND d.id = :doctorId");
			parameters.put("doctorId", reservationSearch.getDoctorId());
		}
		
		if (reservationSearch.getInstId() != null) {
			joins.add(", Institution i");
			filters.add("r.institution = i and i.id = :instId");
			parameters.put("instId", reservationSearch.getInstId());
		}
		
		if (reservationSearch.getApptDate() != null) {
			filters.add("(r.appt_date BETWEEN :apptDateStart AND :apptDateEnd)");
			parameters.put("apptDateStart", reservationSearch.getApptDate());
			parameters.put("apptDateEnd", new Date(reservationSearch.getApptDate().getTime() + 24 * Constant.HOUR_SECS * Constant.MILISECS));
		}
		
		ServerUtil.setJoin(sql, joins);
		ServerUtil.setFilter(sql, filters);
		
		sql.append(" ORDER BY r.appt_date");
		
		TypedQuery<Reservation> query = em.createQuery(sql.toString(), Reservation.class);
		
		for (String parameter : parameters.keySet()) {
			
			Object value = parameters.get(parameter);
			query.setParameter(parameter, value);
		}
		
		List<Reservation> result = query.getResultList();
		
		for (Reservation reservation : result) {
			list.add(reservation.getBean());
		}

		em.close();

		return list;
	}
}