package com.infoklinik.rsvp.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.infoklinik.rsvp.shared.Config;
import com.infoklinik.rsvp.shared.Constant;

public class SmsUtil {
	
	private static final Logger log = Logger.getLogger(SmsUtil.class.getName());
	
	public static boolean sendSms(String recipient, String message) {
		
		if (Config.SMS_GATEWAY.equals(Constant.SMS_GW_BULKSMS)) {
			return sendSmsBulkSms(recipient, message);
		} else if (Config.SMS_GATEWAY.equals(Constant.SMS_GW_BULKSMS)) {
			return sendSmsWebSms(recipient, message);
		} else {
			return false;
		}
		
	} 
	
	private static boolean sendSmsBulkSms(String recipient, String message) {
		
		boolean isSuccess = false;
		
		log.info("Recipient : " + recipient + "\nMessage : " + message);
		
		if (Config.SMS_ACTIVE_STATUS) {
		
			try {
				
				if (!ServerUtil.isEmpty(recipient) && (recipient.indexOf("0") == 0)) {
					recipient = "62" + recipient.substring(1);
				}			
				
				if (!ServerUtil.isEmpty(message) && message.length() > 160) {
					message = message.substring(0,160);
				}
				
	            // Construct data
	            String data = "";
	            /*
	             * Note the suggested encoding for certain parameters, notably
	             * the username, password and especially the message.  ISO-8859-1
	             * is essentially the character set that we use for message bodies,
	             * with a few exceptions for e.g. Greek characters.  For a full list,
	             * see:  http://bulksms.vsms.net/docs/eapi/submission/character_encoding/
	             */
	            
	            data += "username=" + URLEncoder.encode("radixe", "ISO-8859-1");
	            data += "&password=" + URLEncoder.encode("enggar123", "ISO-8859-1");
	            data += "&message=" + URLEncoder.encode(message, "ISO-8859-1");
	            data += "&want_report=1";
	            data += "&msisdn=" + recipient;
	
	            // Send data
	            //URL url = new URL("http://bulksms.vsms.net:5567/eapi/submission/send_sms/2/2.0");
	            URL url = new URL("http://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");
	            
	            /*
	            * If your firewall blocks access to port 5567, you can fall back to port 80:
	            * URL url = new URL("http://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");
	            * (See FAQ for more details.)
	            */
	
	            URLConnection conn = url.openConnection();
	            conn.setDoOutput(true);
	            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	            wr.write(data);
	            wr.flush();
	
	            // Get the response
	            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = null;
	            String resp = null;
	            while ((line = rd.readLine()) != null) {
	                // Print the response output...
	                log.info("SMS Response : " + line);
	                resp = line;
	            }
	            
	            if (resp != null) {
	            	String[] responses = resp.split("|");
	            	String statusCode = responses[0];
	            	if ("0".equals(statusCode)) {
	            		isSuccess = true;
	            	}
	            }
	            
	            wr.close();
	            rd.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		} else {
			isSuccess = true;
		}
		
		return isSuccess;
	}
	
	private static boolean sendSmsWebSms(String recipient, String message) {
		
		boolean isSuccess = false;
		
		log.info("Recipient : " + recipient + "\nMessage : \n" + message);
		log.info("Sms Active : " + Config.SMS_ACTIVE_STATUS);
		
		if (Config.SMS_ACTIVE_STATUS) {
			
			try {
				
				message = message + "\n" + ServerUtil.dateTimeToStr(new Date());
				message = message.replace("\n", "~");
				
				String url = "http://websms.dyndns.biz:8080/sms.php?user=radixe&hpku=083865222131&kirimke="+recipient+"&isi="+ URLEncoder.encode(message, "ISO-8859-1");
				
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
				// optional default is GET
				con.setRequestMethod("GET");
	
				// add request header
				con.setRequestProperty("User-Agent", "AppEngine");
	
				int responseCode = con.getResponseCode();
				log.info("\nSending 'GET' request to URL : " + url);
				log.info("Response Code : " + responseCode);
	
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
	
				// print result
				log.info(response.toString());
				
				if (responseCode == 200 && response.toString().indexOf("sukses") != -1) {
					isSuccess = true;
				}
				
				log.info("SMS Delivery status : " + isSuccess);
				
			} catch (Exception e) {
				log.log(Level.SEVERE, "SMS Delivery Exception", e);
				e.printStackTrace();
			}
		} else {
			isSuccess = true;
		}
		
		return isSuccess;
	}
}
