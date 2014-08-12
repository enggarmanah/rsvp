package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.InsuranceBean;

@RemoteServiceRelativePath("insuranceService")
public interface InsuranceService extends RemoteService {
	
	List<InsuranceBean> getInsurances();	
	
	List<InsuranceBean> getInsurances(Long institutionId);	
}
