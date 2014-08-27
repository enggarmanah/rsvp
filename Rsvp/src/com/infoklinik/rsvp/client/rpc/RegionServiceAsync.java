
package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.RegionBean;
import com.infoklinik.rsvp.shared.RegionSearchBean;

public interface RegionServiceAsync {
	
	public void addRegion(RegionBean regionBean, AsyncCallback<RegionBean> callback);

	public void updateRegion(RegionBean regionBean, AsyncCallback<RegionBean> callback);
	
	public void deleteRegion(RegionBean regionBean, AsyncCallback<RegionBean> callback);

	public void getRegions(RegionSearchBean regionSearch, AsyncCallback<List<RegionBean>> callback);
}
