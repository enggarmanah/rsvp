package com.infoklinik.rsvp.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.Events.Insert;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.infoklinik.rsvp.shared.ReservationBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class CalendarUtil {
	
	private static final Logger log = Logger.getLogger(CalendarUtil.class.getName());

	static Calendar newCalendar() {
		
		AppIdentityCredential credential = new AppIdentityCredential(Arrays.asList("https://www.googleapis.com/auth/calendar"));

		return new Calendar.Builder(new UrlFetchTransport(), new JacksonFactory(), credential).setApplicationName("info-klinik/1.0").build();
	}

	public static String addCalendarEntry(ReservationBean reservation) {
		
		String eventId = "[Event-ID]";
		
		if (ServerUtil.isProductionEnvironment()) {
			
			Calendar calendar = newCalendar();
		    
		    try {
		        Event event = new Event();
		        
		        String patientTitle = (Constant.SEX_MALE.equals(reservation.getPatientSex()) ? "Tn. " : "Nn. ");
		        
		        String summary = "Jadwal Kunjungan Pasien : " + patientTitle + reservation.getPatientName() + " / " + reservation.getPatientMobile();
		        String location = reservation.getInstitution().getName() + ", " + reservation.getInstitution().getAddress();
		        String description = "Untuk info lebih lanjut / pembatalan / perubahan jadwal silahkan hubungi no. telepon : " + reservation.getInstitution().getTelephone();
		        
		        event.setSummary(summary);
		        event.setLocation(location);
		        event.setDescription(description);
		  
		        ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
		        
		        String patientName = patientTitle + reservation.getPatientName();
		        String patientEmail = reservation.getPatientEmail();
		        
		        EventAttendee patientAtd = new EventAttendee();
		        patientAtd.setDisplayName(patientName);
		        patientAtd.setEmail(patientEmail);
		        attendees.add(patientAtd);
		        	        
		        DoctorBean doctor = reservation.getDoctor();
		        String doctorName = doctor.getNameWithTitle();
		        String doctorEmail = doctor.getEmail();
		        
		        EventAttendee doctorAtd = new EventAttendee();
		        doctorAtd.setDisplayName(doctorName);
		        doctorAtd.setEmail(doctorEmail);
		        attendees.add(doctorAtd);
		        
		        InstitutionBean inst = reservation.getInstitution();
		        String instName = inst.getName();
		        String instEmail = inst.getEmail();
		        
		        EventAttendee instAtd = new EventAttendee();
		        instAtd.setDisplayName(instName);
		        instAtd.setEmail(instEmail);
		        attendees.add(instAtd);
		        
		        event.setAttendees(attendees);
		        
		        int apptInterval = Constant.APPT_INTERVAL_MINUTES * Constant.MIN_SECS * Constant.MILISECS;
		  
		        Date startDate = reservation.getApptDate();
		        Date endDate = new Date(startDate.getTime() + apptInterval);
		        
		        DateTime start = new DateTime(startDate, TimeZone.getTimeZone(Constant.TIMEZONE));
		        event.setStart(new EventDateTime().setDateTime(start));
		        
		        DateTime end = new DateTime(endDate, TimeZone.getTimeZone(Constant.TIMEZONE));
		        event.setEnd(new EventDateTime().setDateTime(end));
		        
		        log.info("Calling Calendar API");
		        
		        log.info("Add events into calendar - Begin");
		        
		        Insert insert = calendar.events().insert("primary", event);
		        insert.setSendNotifications(true);
		        event = insert.execute();
		        
		        log.info("Add events into calendar - End");
		        
		        eventId = event.getId();
		        
		        calendar.events().update("primary", event.getId(), event);
		        
		        log.info("Event ID = " + eventId);
	
		    } catch (Exception e) {
		        log.log(Level.SEVERE, "Exception on addCalendarEvent()", e);
		    }
		}
		
		return eventId;
	}
}
