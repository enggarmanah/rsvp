package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.SpecialityBean;

@RemoteServiceRelativePath("specialityService")
public interface SpecialityService extends RemoteService {
	
	List<SpecialityBean> getSpecialities();	
}
