package com.infoklinik.rsvp.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.shared.GalleryBean;

public interface GalleryServiceAsync {

	public void getGalleries(Long instId, AsyncCallback<List<GalleryBean>> callback);
}
