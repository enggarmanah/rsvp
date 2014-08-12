package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.InsuranceService;
import com.infoklinik.rsvp.server.dao.InsuranceDAO;
import com.infoklinik.rsvp.shared.InsuranceBean;

@SuppressWarnings("serial")
public class InsuranceServiceImpl extends BaseServiceServlet implements InsuranceService {
	
	public List<InsuranceBean> getInsurances() {
		
		InsuranceDAO insuranceDao = new InsuranceDAO();
		List<InsuranceBean> insurances = insuranceDao.getInsurances();
		
		List<Long> ids = new ArrayList<Long>();
		for (InsuranceBean instBean : insurances) {
			ids.add(instBean.getId());
		}
		
		return insurances;
	}
	
	public List<InsuranceBean> getInsurances(Long institutionId) {
		
		InsuranceDAO insuranceDao = new InsuranceDAO();
		List<InsuranceBean> insurances = insuranceDao.getInsurances(institutionId);
		
		List<Long> ids = new ArrayList<Long>();
		for (InsuranceBean instBean : insurances) {
			ids.add(instBean.getId());
		}
		
		return insurances;
	}
}
