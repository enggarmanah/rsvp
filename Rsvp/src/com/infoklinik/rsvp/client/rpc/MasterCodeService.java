package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.MasterCodeBean;

@RemoteServiceRelativePath("masterCodeService")
public interface MasterCodeService extends RemoteService {
	
	List<MasterCodeBean> getMasterCodes(String type);	
}
