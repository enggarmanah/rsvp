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

public class MailUtil {
	
	public static void sendEmail() {
		
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("enggarmanah@gmail.com", "Administrator Info Klinik"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("enggarmanah@gmail.com", "Mr. User"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("radixe@yahoo.com", "Mr. User"));
            msg.setSubject("Your Example.com account has been activated");
                        
            Multipart mp = new MimeMultipart();            
            
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(TemplateUtil.getNotificationMessage(), "text/html; charset=utf-8");
            mp.addBodyPart(htmlPart);

            msg.setContent(mp);
            
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
