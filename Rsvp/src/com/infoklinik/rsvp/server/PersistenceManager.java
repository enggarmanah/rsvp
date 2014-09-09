package com.infoklinik.rsvp.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.appengine.api.utils.SystemProperty;
import com.infoklinik.rsvp.shared.Config;
import com.infoklinik.rsvp.shared.Constant;

public class PersistenceManager {
	
	static EntityManagerFactory emf;
	
	private static final Logger log = Logger.getLogger(PersistenceManager.class.getName());
	
	static {
		
		try {
			
			Map<String, String> properties = new HashMap<String, String>();
			
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				
				log.info("Environment Production");
				properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
			
				if (Constant.ENV_PRODUCTION.equals(Config.ENV_CURRENT)) {
					properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url.prd"));
				} else {
					properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url.stg"));
				}
			
			} else {
				log.info("Environment Development");
				properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
				properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url.dev"));
			}
		
			emf = Persistence.createEntityManagerFactory("Demo", properties);
			
		} catch (Exception e) {
			
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public static EntityManager getEntityManager() {
		
		return emf.createEntityManager();
	}
}
