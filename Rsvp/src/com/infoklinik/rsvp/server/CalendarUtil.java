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
import com.infoklinik.rsvp.shared.AppointmentBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class CalendarUtil {
	
	private static final Logger log = Logger.getLogger(CalendarUtil.class.getName());

	static Calendar newCalendar() {
		
		AppIdentityCredential credential = new AppIdentityCredential(Arrays.asList("https://www.googleapis.com/auth/calendar"));

		return new Calendar.Builder(new UrlFetchTransport(), new JacksonFactory(), credential).setApplicationName("info-klinik/1.0").build();
	}

	public static String addCalendarEntry(AppointmentBean appointment) {
		
		String eventId = null;
		
		if (ServerUtil.isProductionEnvironment()) {
			
			Calendar calendar = newCalendar();
		    
		    try {
		        Event event = new Event();
		        
		        String patientTitle = (Constant.SEX_MALE.equals(appointment.getPatientSex()) ? "Tn. " : "Nn. ");
		        
		        String summary = "Jadwal Kunjungan Pasien : " + patientTitle + appointment.getPatientName() + " / " + appointment.getPatientMobile();
		        String location = appointment.getInstitution().getName() + ", " + appointment.getInstitution().getAddress();
		        String description = "Untuk info lebih lanjut silahkan hubungi no. telepon : " + appointment.getInstitution().getTelephone();
		        
		        event.setSummary(summary);
		        event.setLocation(location);
		        event.setDescription(description);
		  
		        ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
		        
		        String patientName = patientTitle + appointment.getPatientName();
		        String patientEmail = appointment.getPatientEmail();
		        
		        EventAttendee patientAtd = new EventAttendee();
		        patientAtd.setDisplayName(patientName);
		        patientAtd.setEmail(patientEmail);
		        attendees.add(patientAtd);
		        	        
		        DoctorBean doctor = appointment.getDoctor();
		        String doctorName = doctor.getNameWithTitle();
		        String doctorEmail = doctor.getEmail();
		        
		        EventAttendee doctorAtd = new EventAttendee();
		        doctorAtd.setDisplayName(doctorName);
		        doctorAtd.setEmail(doctorEmail);
		        attendees.add(doctorAtd);
		        
		        InstitutionBean inst = appointment.getInstitution();
		        String instName = inst.getName();
		        String instEmail = inst.getEmail();
		        
		        EventAttendee instAtd = new EventAttendee();
		        instAtd.setDisplayName(instName);
		        instAtd.setEmail(instEmail);
		        attendees.add(instAtd);
		        
		        event.setAttendees(attendees);
		        
		        int apptInterval = Constant.APPT_INTERVAL_MINUTES * Constant.MIN_SECS * Constant.MILISECS;
		  
		        Date startDate = appointment.getApptDate();
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
		        
		        log.info("Event ID = " + eventId);
	
		    } catch (Exception e) {
		        log.log(Level.SEVERE, "Exception on addCalendarEvent()", e);
		    }
		}
		
		return eventId;
	}
}
