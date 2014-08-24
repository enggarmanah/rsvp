package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.DoctorSearchBean;

@RemoteServiceRelativePath("doctorService")
public interface DoctorService extends RemoteService {
	
	DoctorBean getDoctor(Long doctorId);
	
	DoctorBean addDoctor(DoctorBean doctor);
	
	DoctorBean updateDoctor(DoctorBean doctor);
	
	List<DoctorBean> getDoctors(Long instId);
	
	List<DoctorBean> getDoctors(DoctorSearchBean doctorSearch);
	
	void increaseViewCount(Long doctorId);
}
