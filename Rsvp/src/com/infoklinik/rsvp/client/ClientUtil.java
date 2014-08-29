package com.infoklinik.rsvp.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.GisLatLng;
import com.infoklinik.rsvp.shared.SharedUtil;
import com.infoklinik.rsvp.shared.UserBean;

public class ClientUtil {
	
	private static DateTimeFormat timeDtf = DateTimeFormat.getFormat("kk:mm");
	private static DateTimeFormat dateDtf = DateTimeFormat.getFormat("dd/MM/yyyy");
	private static DateTimeFormat dateTimeDtf = DateTimeFormat.getFormat("dd/MM/yyyy kk:mm");
	private static DateTimeFormat monthStrDtf = DateTimeFormat.getFormat("MMMM");
	private static DateTimeFormat monthIntDtf = DateTimeFormat.getFormat("M");
	private static DateTimeFormat yearDtf = DateTimeFormat.getFormat("yyyy");
	private static DateTimeFormat dayDtf = DateTimeFormat.getFormat("EEE");
		
	public static String DATE_TIME_FORMAT_DATE = "DATE_TIME_FORMAT_DATE";
	public static String DATE_TIME_FORMAT_DATETIME = "DATE_TIME_FORMAT_DATE_TIME";
	
	public static int MIN_SEARCH_CHAR_LENGTH = 3;
	
	public static HashMap<Integer, String> dayMap;
	public static HashMap<Integer, Date> dayWeekMap;
	
	public static CityBean nearestCity;
	public static boolean isReqGeoLocation = true;
	public static boolean isAdminUser = false;
	public static UserBean user;
	
	public static void setUser(UserBean userLogin) {
		user = userLogin;
		isAdminUser = true;
	}
	
	public static UserBean getUser() {
		return user;
	}
	
	public static boolean isAdminUser() {
		return isAdminUser;
	}
	
	public static void goToTop() {
		
		Window.scrollTo(0, 0);
	}
	
	public static boolean isDevelopment() {
		
		String hostName = Window.Location.getHostName();
		if (!ClientUtil.isEmpty(hostName) && 
		    (hostName.indexOf("localhost") != -1 || hostName.indexOf("127.0.0.1") != -1)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getMonthText(String mm) {
		
		HashMap<String,String> monthText = new HashMap<String,String>();
		monthText.put("01", "JAN");
		monthText.put("02", "FEB");
		monthText.put("03", "MAR");
		monthText.put("04", "APR");
		monthText.put("05", "MAY");
		monthText.put("06", "JUN");
		monthText.put("07", "JUL");
		monthText.put("08", "AUG");
		monthText.put("09", "SEP");
		monthText.put("10", "OCT");
		monthText.put("11", "NOV");
		monthText.put("12", "DEC");
		
		return monthText.get(mm);
	}
	
	public static DateTimeFormat getDateTime(String format) {
		
		if (DATE_TIME_FORMAT_DATE.equals(format)) {
			return dateDtf;
		} else if (DATE_TIME_FORMAT_DATETIME.equals(format)) {
			return dateTimeDtf;
		} else {
			return null;
		}
	}
	
	public static int getTime(Date date) {
		
		String dateStr = dateToStr(date);
		Date truncDate = strToDate(dateStr);
		Long time = (date.getTime() - truncDate.getTime()) / Constant.MILISECS;
		
		return time.intValue();
	}
	
	public static String timeToStr(int i) {
		
		i = i / Constant.MILISECS;
		
		int hour = i / Constant.HOUR_SECS;
		int min = (i % Constant.HOUR_SECS) / Constant.MIN_SECS;
		
		String hh = String.valueOf(hour);
		String mm = String.valueOf(min);
		
		hh = hh.length() < 2 ? "0" + hh : hh;
		mm = mm.length() < 2 ? "0" + mm : mm;
		
		String time = hh + ":" + mm;
		
		return time;
	}
		
	public static String timeToStr(Date date) {
		
		String strTime = "";
		try {
			strTime = timeDtf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTime;
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
	
	public static String dayDateTimeToStr(Date date) {
		
		String strDate = "";
		try {
			strDate = dayToStr(dateToDayOfWeek(date)) + ", " + dateDtf.format(date) + " " + timeDtf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	public static String getCurrMonthStr() {
		return monthStrDtf.format(new Date());
	}
	
	public static int getCurrMonthInt() {
		return Integer.parseInt(monthIntDtf.format(new Date()));
	}
	
	public static String getCurrYear() {
		return yearDtf.format(new Date());
	}
	
	public static String dateToStr(Date date) {
		
		String strDate = "";
		try {
			strDate = dateDtf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	public static Date strToDate(String text) {
		
		Date date = new Date();
		try {
			date = dateDtf.parse(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date strToDateTime(String text) {
		
		Date date = null;
		try {
			date = dateTimeDtf.parse(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getDateOfWeek(int d) {
		
		if (dayWeekMap == null) {
			
			dayWeekMap = new HashMap<Integer, Date>();
			
			Date date = new Date();
			int dayOfWeek = dateToDayOfWeek(date);
			
			for (int i = 1; i <= 7; i++) {
				
				Date curDate = dateDtf.parse(dateDtf.format(date));
				
				dayWeekMap.put(Integer.valueOf(dayOfWeek), curDate);
				date.setTime(date.getTime() + (24 * 3600 * 1000));
				
				if (dayOfWeek == 7) {
					dayOfWeek = 1;
				} else {
					dayOfWeek++;
				}
			}
		}
		
		return new Date(dayWeekMap.get(Integer.valueOf(d)).getTime());
	}
	
	public static int dateToDayOfWeek(Date date) {
		
		String day = dayDtf.format(date);
			
		int dayOfWeek = 0;
			
		if ("Sun".equals(day)) {
			dayOfWeek = 1;
		} else if ("Mon".equals(day)) {
			dayOfWeek = 2;
		} else if ("Tue".equals(day)) {
			dayOfWeek = 3;
		} else if ("Wed".equals(day)) {
			dayOfWeek = 4;
		} else if ("Thu".equals(day)) {
			dayOfWeek = 5;
		} else if ("Fri".equals(day)) {
			dayOfWeek = 6;
		} else if ("Sat".equals(day)) {
			dayOfWeek = 7;
		}
			
		return dayOfWeek;
	}
	
	public static String dateToDay(Date date) {
		
		String day = dayDtf.format(date);
			
		return day;
	}
	
	public static String dayToStr(int i) {
		
		if (dayMap == null) {
			
			dayMap = new HashMap<Integer, String>();
			
			dayMap.put(Integer.valueOf(Constant.DAY_MONDAY), Constant.DAY_MONDAY_DESC);
			dayMap.put(Integer.valueOf(Constant.DAY_TUESDAY), Constant.DAY_TUESDAY_DESC);
			dayMap.put(Integer.valueOf(Constant.DAY_WEDNESDAY), Constant.DAY_WEDNESDAY_DESC);
			dayMap.put(Integer.valueOf(Constant.DAY_THURSDAY), Constant.DAY_THURSDAY_DESC);
			dayMap.put(Integer.valueOf(Constant.DAY_FRIDAY), Constant.DAY_FRIDAY_DESC);
			dayMap.put(Integer.valueOf(Constant.DAY_SATURDAY), Constant.DAY_SATURDAY_DESC);
			dayMap.put(Integer.valueOf(Constant.DAY_SUNDAY), Constant.DAY_SUNDAY_DESC);
		}
		
		return dayMap.get(Integer.valueOf(i));
	}
	
	public static Date dateAddDay(Date date, int day) {
		return new Date(date.getTime() + day * 24 * 3600 * 1000);
	}
	
	public static boolean isEmpty(String string) {
		
		if (string == null || "".equals(string.trim())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isEmpty(Object obj) {
		
		if (obj == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean equals(String a, String b) {
		
		if (isEmpty(a) && isEmpty(b)) {
			return true;
		} else if (!isEmpty(a) && a.equals(b)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String trim(String str) {
		
		if (str != null) {
			return str.trim();
		} else {
			return null;
		}
	}
	
	public static String mobileWoMask(String mobile) {
		if (!isEmpty(mobile)) {
			int i = mobile.indexOf("xxxx");
			if (i != -1) {
				return mobile.substring(0, i);
			} else {
				return mobile;
			}
		} else {
			return mobile;
		}
	}
	
	public static String formatNumber(int number, int digit) {
		
		String str = String.valueOf(number);
		
		for (int i = str.length(); i < digit; i++) {
			str = "0" + str;
		}
		
		return str;
	}
	
	public static String numberToStr(Long number) {
		
		if (isEmpty(number)) {
			return "";
		} else {
			return String.valueOf(number);
		}
	}	

	public static void setSelectedIndex(ListBox listBox, String value) {
		
		for (int i = 0; i < listBox.getItemCount(); i++) {
			String val = listBox.getValue(i);
			if (equals(value, val)) {
				listBox.setSelectedIndex(i);
				return;
			}
		}
	}
	
	public static CityBean getNearestCity(List<CityBean> cities, Position position) {
		
		Coordinates coordinates = position.getCoordinates();
		GisLatLng cordLatLng = new GisLatLng(coordinates.getLatitude(), coordinates.getLongitude());
		
		long shortDist = 1000000;
		
		for (CityBean city : cities) {
			
			GisLatLng cityLatLng = new GisLatLng(city.getLocationLat(), city.getLocationLng());
			long distance = SharedUtil.getDistance(cityLatLng, cordLatLng);
			
			if (distance < shortDist) {
				
				shortDist = distance;
				nearestCity = city;
			}
		}
		
		return nearestCity;
	}
	
	public static CityBean getNearestCity() {
		
		return nearestCity;
	}
	
	public static boolean isReqGeoLocation() {
		
		return isReqGeoLocation;
	}
	
	public static void setReqGeoLocation(boolean isReqGeoLoc) {
		
		isReqGeoLocation = isReqGeoLoc;
	}
}
