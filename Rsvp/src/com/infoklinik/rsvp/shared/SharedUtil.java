package com.infoklinik.rsvp.shared;

import java.util.Date;
import java.util.List;

public class SharedUtil {
	
	public static Date getCurrentDate() {
		return new Date();
	}
	
	public static String trim(String str) {
		
		if (str != null) {
			return str.trim();
		} else {
			return "";
		}
	}
	
	public static boolean isEmpty(String string) {
		
		if (string == null || "".equals(string.trim())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static double rad(double x) {

		return x * Math.PI / 180;
	}

	public static long getDistance(GisLatLng p1, GisLatLng p2) {
		
		int R = 6378137; // Earth’s mean radius in meter
		double dLat = rad(p2.getLat() - p1.getLat());
		double dLong = rad(p2.getLng() - p1.getLng());
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(rad(p1.getLat())) * Math.cos(rad(p2.getLat()))
				* Math.sin(dLong / 2) * Math.sin(dLong / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;
		
		return Math.round(d);
	}
	
	public static GisLatLng getCenter(List<GisLatLng> latLngs) {
		
		double minLat = 0;
		double maxLat = 0;
		double minLng = 0;
		double maxLng = 0;
		
		if (latLngs.size() > 0) {
			
			minLat = latLngs.get(0).getLat();
			maxLat = latLngs.get(0).getLat();
			minLng = latLngs.get(0).getLng();
			maxLng = latLngs.get(0).getLng();
		}
		
		for (GisLatLng latLng : latLngs) {
			
			minLat = latLng.getLat() < minLat ? latLng.getLat() : minLat;
			maxLat = latLng.getLat() > maxLat ? latLng.getLat() : maxLat;
			
			minLng = latLng.getLng() < minLng ? latLng.getLng() : minLng;
			maxLng = latLng.getLng() > maxLng ? latLng.getLng() : maxLng;
			
		}
		
		double centerLat = minLat + ((maxLat - minLat) / 2);
		double centerLng = minLng + ((maxLng - minLng) / 2);
		
		return new GisLatLng(centerLat, centerLng);
	}
	
	public static int getScale(List<GisLatLng> latLngs) {
		
		double minLat = 0;
		double maxLat = 0;
		double minLng = 0;
		double maxLng = 0;
		
		if (latLngs.size() > 1) {
			
			minLat = latLngs.get(0).getLat();
			maxLat = latLngs.get(0).getLat();
			minLng = latLngs.get(0).getLng();
			maxLng = latLngs.get(0).getLng();
		
		} else {
			
			return 15;
		}
		
		for (GisLatLng latLng : latLngs) {
			
			minLat = latLng.getLat() < minLat ? latLng.getLat() : minLat;
			maxLat = latLng.getLat() > maxLat ? latLng.getLat() : maxLat;
			
			minLng = latLng.getLng() < minLng ? latLng.getLng() : minLng;
			maxLng = latLng.getLng() > maxLng ? latLng.getLng() : maxLng;
			
		}
		
		GisLatLng topLeft = new GisLatLng(minLat, minLng);
		GisLatLng topRight = new GisLatLng(minLat, maxLng);
		
		GisLatLng bottomLeft = new GisLatLng(maxLat, minLng);
		//LatLng bottomRight = new LatLng(maxLat, maxLng);
		
		double distanceLat = getDistance(topLeft, bottomLeft);
		double distanceLng = getDistance(topLeft, topRight);
		
		int distLat = 16;
		int scaleLat = 21;
		
		while (distLat < distanceLat) {
			distLat = distLat * 2;
			scaleLat--;
		}
		
		int distLng = 16;
		int scaleLng = 21;
		
		while (distLng < distanceLng) {
			distLng = distLng * 2;
			scaleLng--;
		}
		
		return (scaleLat < scaleLng ? scaleLat : scaleLng);
	}
	
	public static String getDistanceInfo(Long distance) {
		
		if (distance == null) {
			return "";
		}
		
		String distInfo = "";
		
		if (distance < 1000) {
			distInfo = distance + " M";
		} else {
			long km = distance / 1000;
			long mt = Math.round((distance % 1000) / 100.0);
			distInfo = km + "," + mt + " KM";
		}
		
		return distInfo;
	}
}
