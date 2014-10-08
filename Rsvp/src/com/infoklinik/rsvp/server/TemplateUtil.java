package com.infoklinik.rsvp.server;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.infoklinik.rsvp.shared.ReservationBean;

public class TemplateUtil {
	
	private static final Logger log = Logger.getLogger(TemplateUtil.class.getName());
	
	private static VelocityEngine ve = new VelocityEngine();
	
	static {
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    	ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    	ve.init();
	}
	
	public static String getNotificationMessage(ReservationBean reservation) {
		
		StringWriter w = new StringWriter();
		
		try
        {
        	VelocityContext context = new VelocityContext();

            context.put("voucher", reservation.getReservationCode());
            context.put("service", reservation.getService().getName());
            context.put("institution", reservation.getInstitution().getName());
            context.put("telephone", reservation.getInstitution().getTelephone());
            context.put("address", reservation.getInstitution().getAddress());

            Template t = ve.getTemplate( "com/infoklinik/rsvp/server/template/reservation.vm" );
        	t.merge(context, w);
        	
        	System.out.println(" template : " + w );
        }
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage(), e);
        }
		
		return w.toString();
	}
}
