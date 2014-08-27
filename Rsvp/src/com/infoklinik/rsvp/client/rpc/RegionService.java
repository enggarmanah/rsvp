package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.RegionBean;
import com.infoklinik.rsvp.shared.RegionSearchBean;

@RemoteServiceRelativePath("regionService")
public interface RegionService extends RemoteService {
	
	RegionBean addRegion(RegionBean regionBean);

	RegionBean updateRegion(RegionBean regionBean);
	
	RegionBean deleteRegion(RegionBean regionBean);
	
	List<RegionBean> getRegions(RegionSearchBean regionSearch);	
}
