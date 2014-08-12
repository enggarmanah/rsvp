package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;

public interface ServiceTypeServiceAsync {

	public void getServiceTypes(ServiceTypeSearchBean serviceTypeSearchBean, AsyncCallback<List<ServiceTypeBean>> callback);
	
	public void addServiceType(ServiceTypeBean serviceTypeBean, AsyncCallback<ServiceTypeBean> callback);
	
	public void updateServiceType(ServiceTypeBean serviceTypeBean, AsyncCallback<ServiceTypeBean> callback);
	
	public void deleteServiceType(ServiceTypeBean serviceTypeBean, AsyncCallback<ServiceTypeBean> callback);
	
	public void getCategories(AsyncCallback<List<String>> callback);
}
