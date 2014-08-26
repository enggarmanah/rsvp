package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.InsuranceSearchBean;

public interface InsuranceServiceAsync {
	
	public void addInsurance(InsuranceBean insuranceBean, AsyncCallback<InsuranceBean> callback);

	public void updateInsurance(InsuranceBean insuranceBean, AsyncCallback<InsuranceBean> callback);
	
	public void deleteInsurance(InsuranceBean insuranceBean, AsyncCallback<InsuranceBean> callback);

	public void getInsurances(AsyncCallback<List<InsuranceBean>> callback);
	
	public void getInsurances(Long institutionId, AsyncCallback<List<InsuranceBean>> callback);
	
	public void getInsurances(InsuranceSearchBean insuranceSearchBean, AsyncCallback<List<InsuranceBean>> callback);
}
