package com.infoklinik.rsvp.server;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class TemplateUtil {
	
	private static final Logger log = Logger.getLogger(TemplateUtil.class.getName());
	
	private static VelocityEngine ve = new VelocityEngine();
	
	static {
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    	ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    	ve.init();
	}
	
	public static String getNotificationMessage() {
		
		StringWriter w = new StringWriter();
		
		try
        {
        	VelocityContext context = new VelocityContext();

            context.put("name", "Velocity");
            context.put("project", "Jakarta");

            Template t = ve.getTemplate( "com/infoklinik/rsvp/server/template/service.vm" );
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
