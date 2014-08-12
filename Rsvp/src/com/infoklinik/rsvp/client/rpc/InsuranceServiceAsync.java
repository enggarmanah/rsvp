package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.InsuranceBean;

public interface InsuranceServiceAsync {

	public void getInsurances(AsyncCallback<List<InsuranceBean>> callback);
	
	public void getInsurances(Long institutionId, AsyncCallback<List<InsuranceBean>> callback);
}
