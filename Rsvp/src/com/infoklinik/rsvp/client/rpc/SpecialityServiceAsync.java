package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.SpecialityBean;

public interface SpecialityServiceAsync {

	public void getSpecialities(AsyncCallback<List<SpecialityBean>> callback);
}
