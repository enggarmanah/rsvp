package com.infoklinik.rsvp.shared;

import java.util.Comparator;

public class InstDistanceComparator implements Comparator<InstitutionBean> {
	
	public int compare(InstitutionBean inst1, InstitutionBean inst2) {
		
		if (inst1.getDistance() == null || inst2.getDistance() == null) {
			return 0;
		} else {
			return inst1.getDistance().compareTo(inst2.getDistance());
		}
	}
}
