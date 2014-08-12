package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceSearchBean;

public interface ServiceServiceAsync {

	public void getService(Long serviceId, AsyncCallback<ServiceBean> callback);
	
	public void getPromotions(AsyncCallback<List<ServiceBean>> callback);
	
	public void getServices(Long institutionId, AsyncCallback<List<ServiceBean>> callback);
	
	public void getPromotions(Long institutionId, AsyncCallback<List<ServiceBean>> callback);
	
	public void getServices(ServiceSearchBean instSearch, AsyncCallback<List<ServiceBean>> callback);
	
	public void increaseViewCount(Long serviceId, AsyncCallback<Void> callback);
}
