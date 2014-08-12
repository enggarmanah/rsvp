package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.MasterCodeBean;

public interface MasterCodeServiceAsync {

	public void getMasterCodes(String type, AsyncCallback<List<MasterCodeBean>> callback);
}
