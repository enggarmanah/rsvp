package com.infoklinik.rsvp.server;

import java.util.ArrayList;

public class ReferenceUtil {
	
	private static ArrayList<String> months;
	
	public static synchronized ArrayList<String> getMonths() {
		
		if (months == null) {
			initMonth();		
		}
		
		return months;
	}
	
	private static void initMonth() {
		
		months = new ArrayList<String>();
		
		months.add("Januari");
		months.add("Februari");
		months.add("Maret");
		months.add("April");
		months.add("Mei");
		months.add("Juni");
		months.add("Juli");
		months.add("Agustus");
		months.add("September");
		months.add("Oktober");
		months.add("November");
		months.add("Desember");
	}
}
