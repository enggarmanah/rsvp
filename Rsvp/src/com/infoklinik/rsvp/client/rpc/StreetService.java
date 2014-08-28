package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.StreetBean;
import com.infoklinik.rsvp.shared.StreetSearchBean;

@RemoteServiceRelativePath("streetService")
public interface StreetService extends RemoteService {
	
	StreetBean addStreet(StreetBean streetBean);

	StreetBean updateStreet(StreetBean streetBean);
	
	StreetBean deleteStreet(StreetBean streetBean);
	
	List<StreetBean> getStreets(StreetSearchBean streetSearch);	
}
