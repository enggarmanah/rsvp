package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.DoctorSearchBean;

public interface DoctorServiceAsync {
	
	public void getDoctor(Long doctorId, AsyncCallback<DoctorBean> callback);
	
	public void addDoctor(DoctorBean doctor, AsyncCallback<DoctorBean> callback);
	
	public void updateDoctor(DoctorBean doctor, AsyncCallback<DoctorBean> callback);

	public void getDoctors(Long instId, AsyncCallback<List<DoctorBean>> callback);
	
	public void getDoctors(DoctorSearchBean doctorSearch, AsyncCallback<List<DoctorBean>> callback);
	
	public void increaseViewCount(Long doctorId, AsyncCallback<Void> callback);
}
