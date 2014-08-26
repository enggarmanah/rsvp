package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.InsuranceSearchBean;

@RemoteServiceRelativePath("insuranceService")
public interface InsuranceService extends RemoteService {
	
	InsuranceBean addInsurance(InsuranceBean insuranceBean);

	InsuranceBean updateInsurance(InsuranceBean insuranceBean);
	
	InsuranceBean deleteInsurance(InsuranceBean insuranceBean);
	
	List<InsuranceBean> getInsurances();	
	
	List<InsuranceBean> getInsurances(Long institutionId);
	
	List<InsuranceBean> getInsurances(InsuranceSearchBean insuranceSearchBean);
}
