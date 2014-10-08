package com.infoklinik.rsvp.server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.infoklinik.rsvp.shared.Config;
import com.infoklinik.rsvp.shared.ReservationBean;

public class MailUtil {
	
	public static void sendEmail() {
		
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(Config.ADMIN_EMAIL, Config.ADMIN_EMAIL_DESC));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("enggarmanah@gmail.com", "Mr. User"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("radixe@yahoo.com", "Mr. User"));
            msg.setSubject("Your Example.com account has been activated");
                        
            Multipart mp = new MimeMultipart();            
            
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent("Test", "text/html; charset=utf-8");
            mp.addBodyPart(htmlPart);

            msg.setContent(mp);
            
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void sendReservationEmail(ReservationBean reservation) {
		
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(Config.ADMIN_EMAIL, Config.ADMIN_EMAIL_DESC));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(reservation.getPatientEmail(), reservation.getPatientName()));
            msg.setSubject("Registrasi Layanan : " + reservation.getService().getName() + " @ " + reservation.getInstitution().getName());
                        
            Multipart mp = new MimeMultipart();            
            
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(TemplateUtil.getNotificationMessage(reservation), "text/html; charset=utf-8");
            mp.addBodyPart(htmlPart);

            msg.setContent(mp);
            
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
