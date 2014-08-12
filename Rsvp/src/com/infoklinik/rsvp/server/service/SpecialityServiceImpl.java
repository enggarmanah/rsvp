package com.infoklinik.rsvp.server.service;

import java.util.ArrayList;
import java.util.List;

import com.infoklinik.rsvp.client.rpc.SpecialityService;
import com.infoklinik.rsvp.server.dao.SpecialityDAO;
import com.infoklinik.rsvp.shared.SpecialityBean;

@SuppressWarnings("serial")
public class SpecialityServiceImpl extends BaseServiceServlet implements SpecialityService {
	
	public List<SpecialityBean> getSpecialities() {
		
		SpecialityDAO specialityDao = new SpecialityDAO();
		List<SpecialityBean> specialities = specialityDao.getSpecialities();
		
		List<Long> ids = new ArrayList<Long>();
		for (SpecialityBean instBean : specialities) {
			ids.add(instBean.getId());
		}
		
		return specialities;
	}
}
