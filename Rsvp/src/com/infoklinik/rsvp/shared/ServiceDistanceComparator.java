package com.infoklinik.rsvp.shared;

import java.util.Comparator;

public class ServiceDistanceComparator implements Comparator<ServiceBean> {
	
	public int compare(ServiceBean service1, ServiceBean service2) {
		
		if (service1.getDistance() == null || service2.getDistance() == null) {
			return 0;
		} else {
			return service1.getDistance().compareTo(service2.getDistance());
		}
	}
}
