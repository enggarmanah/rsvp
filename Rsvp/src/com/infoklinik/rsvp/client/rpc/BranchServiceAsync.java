package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.BranchBean;

public interface BranchServiceAsync {

	public void getBranches(Long instId, AsyncCallback<List<BranchBean>> callback);
}