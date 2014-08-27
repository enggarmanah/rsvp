package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.RegionService;
import com.infoklinik.rsvp.server.dao.RegionDAO;
import com.infoklinik.rsvp.shared.RegionBean;
import com.infoklinik.rsvp.shared.RegionSearchBean;

@SuppressWarnings("serial")
public class RegionServiceImpl extends BaseServiceServlet implements RegionService {
	
	public RegionBean addRegion(RegionBean regionBean) {
		
		RegionDAO regionDAO = new RegionDAO();
		RegionBean region = regionDAO.addRegion(regionBean);
		
		return region;
	}
	
	public RegionBean updateRegion(RegionBean regionBean) {
		
		RegionDAO regionDAO = new RegionDAO();
		RegionBean region = regionDAO.updateRegion(regionBean);
		
		return region;
	}
	
	public RegionBean deleteRegion(RegionBean regionBean) {
		
		RegionDAO regionDAO = new RegionDAO();
		RegionBean region = regionDAO.deleteRegion(regionBean);
		
		return region;
	}
	
	public List<RegionBean> getRegions(RegionSearchBean regionSearchBean) {
		
		RegionDAO regionDAO = new RegionDAO();
		List<RegionBean> regions = regionDAO.getRegions(regionSearchBean);
		
		return regions;
	}
}
