package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceSearchBean;

@RemoteServiceRelativePath("serviceService")
public interface ServiceService extends RemoteService {
	
	ServiceBean getService(Long serviceId);
	
	List<ServiceBean> getPromotions();	
	
	List<ServiceBean> getServices(Long institutionId);
	
	List<ServiceBean> getPromotions(Long institutionId);
	
	List<ServiceBean> getServices(ServiceSearchBean instSearch);
	
	void increaseViewCount(Long serviceId);
}
