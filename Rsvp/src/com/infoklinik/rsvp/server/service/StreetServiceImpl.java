package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.StreetService;
import com.infoklinik.rsvp.server.dao.StreetDAO;
import com.infoklinik.rsvp.shared.StreetBean;
import com.infoklinik.rsvp.shared.StreetSearchBean;

@SuppressWarnings("serial")
public class StreetServiceImpl extends BaseServiceServlet implements StreetService {
	
	public StreetBean addStreet(StreetBean streetBean) {
		
		StreetDAO streetDAO = new StreetDAO();
		StreetBean street = streetDAO.addStreet(streetBean);
		
		return street;
	}
	
	public StreetBean updateStreet(StreetBean streetBean) {
		
		StreetDAO streetDAO = new StreetDAO();
		StreetBean street = streetDAO.updateStreet(streetBean);
		
		return street;
	}
	
	public StreetBean deleteStreet(StreetBean streetBean) {
		
		StreetDAO streetDAO = new StreetDAO();
		StreetBean street = streetDAO.deleteStreet(streetBean);
		
		return street;
	}
	
	public List<StreetBean> getStreets(StreetSearchBean streetSearchBean) {
		
		StreetDAO streetDAO = new StreetDAO();
		List<StreetBean> streets = streetDAO.getStreets(streetSearchBean);
		
		return streets;
	}
}
