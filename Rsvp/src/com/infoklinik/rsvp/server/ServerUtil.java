package com.infoklinik.rsvp.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.google.appengine.api.utils.SystemProperty;
import com.infoklinik.rsvp.shared.Constant;

public class ServerUtil {
	
	public static String redirectUri = "http://www.infoklinik.com";
	
	private static SimpleDateFormat dateTimeDtf = new SimpleDateFormat("dd/MM/yyyy kk:mm");
	
	public static void main(String[] args) {
		System.out.println(generateReservationCode());
	}
	
	public static String getCallbackUrl() {
		
		if (ServerUtil.isProductionEnvironment()) {
			return Constant.APP_CALLBACK_URL_PROD;
		} else {
			return Constant.APP_CALLBACK_URL_DEV;
		}
	}
	
	public static synchronized String generateReservationCode() {
		
		String code = "";
		
		Calendar cal = Calendar.getInstance();
		long time = cal.getTimeInMillis();		
		
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		time = time - cal.getTimeInMillis();
		
		code = Long.toString(time, 32);
		code = code.toUpperCase();
		
		return code;
	}
	
	public static boolean isProductionEnvironment() {
		
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isEmpty(String string) {

		if (string == null || "".equals(string.trim())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isEmpty(Long id) {

		if (id == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isEmpty(Boolean status) {

		if (status == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Date toDate(Date date) {
		
		return date != null ? new Date(date.getTime()) : null;
	}

	public static String toLowerCase(String string) {
		
		if (!isEmpty(string)) {
			return string.toLowerCase();
		} else {
			return string;
		}
	}
	
	public static String dateTimeToStr(Date date) {
		
		String strDate = "";
		try {
			strDate = dateTimeDtf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	public static int getCurrDay() {
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		
		return day;
	}
	
	public static String dateDayTimeToStr(Date date) {
		
		String strDate = "";
		try {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int day = cal.get(Calendar.DAY_OF_WEEK);
			String dayStr = "";
			
			switch (day) {
				case Calendar.MONDAY: dayStr = "Senin";
			    		break;
				case Calendar.TUESDAY: dayStr = "Selasa";
	    				break;
				case Calendar.WEDNESDAY: dayStr = "Rabu";
					    break;
				case Calendar.THURSDAY: dayStr = "Kamis";
			    		break;
				case Calendar.FRIDAY: dayStr = "Jumat";
						break;
				case Calendar.SATURDAY: dayStr = "Sabtu";
						break;
				case Calendar.SUNDAY: dayStr = "Minggu";
			    		break;
			}
			
			strDate = dayStr + ", " + dateTimeDtf.format(date);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	public static Long strToLong(String text) {
		
		Long number = null;
		try {
			number = Long.valueOf(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}
	
	public static void setJoin(StringBuffer sql, ArrayList<String> joins) {
		
		for (String join : joins) {
			
			sql.append(" " + join);
		}
	}
	
	public static void setFilter(StringBuffer sql, ArrayList<String> filters) {
		
		int index = 1;
		
		for (String filter : filters) {
			
			if (index == 1) {
				sql.append(" WHERE " + filter);
			} else {
				sql.append(" AND " + filter);
			}
			
			index++;
		}
	}
	
	public static void setFilterAnd(StringBuffer sql, ArrayList<String> filters) {
		
		for (String filter : filters) {
			
			sql.append(" AND " + filter);
		}
	}
	
	public static String createSqlFilterIdIn(List<Long> ids) {
		
		StringBuffer str = new StringBuffer();
		
		for (int i = 1; i <= ids.size(); i++) {
			
			if (i > 1) {
				str.append(", ");
			}
			
			str.append(":id" + i);
		}
		
		return str.toString();
	}
	
	public static void setSqlParamIdIn(Query query, List<Long> ids) {
		
		int i = 1;
		for (Long id : ids) {
			query.setParameter("id" + i++, id);
		}
	}
}
