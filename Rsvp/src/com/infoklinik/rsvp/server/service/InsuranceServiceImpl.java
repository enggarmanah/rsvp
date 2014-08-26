package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.InsuranceService;
import com.infoklinik.rsvp.server.dao.InsuranceDAO;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.InsuranceSearchBean;

@SuppressWarnings("serial")
public class InsuranceServiceImpl extends BaseServiceServlet implements InsuranceService {
	
	public InsuranceBean addInsurance(InsuranceBean insuranceBean) {
		
		InsuranceDAO insuranceDAO = new InsuranceDAO();
		InsuranceBean insurance = insuranceDAO.addInsurance(insuranceBean);
		
		return insurance;
	}
	
	public InsuranceBean updateInsurance(InsuranceBean insuranceBean) {
		
		InsuranceDAO insuranceDAO = new InsuranceDAO();
		InsuranceBean insurance = insuranceDAO.updateInsurance(insuranceBean);
		
		return insurance;
	}
	
	public InsuranceBean deleteInsurance(InsuranceBean insuranceBean) {
		
		InsuranceDAO insuranceDAO = new InsuranceDAO();
		InsuranceBean insurance = insuranceDAO.deleteInsurance(insuranceBean);
		
		return insurance;
	}
	
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
	
	public List<InsuranceBean> getInsurances(InsuranceSearchBean insuranceSearchBean) {
		
		InsuranceDAO insuranceDAO = new InsuranceDAO();
		List<InsuranceBean> insurances = insuranceDAO.getInsurances(insuranceSearchBean);
		
		return insurances;
	}
}
