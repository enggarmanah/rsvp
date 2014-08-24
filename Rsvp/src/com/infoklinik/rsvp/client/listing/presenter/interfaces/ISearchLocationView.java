package com.infoklinik.rsvp.client.listing.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.LatLng;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.mvp4g.client.view.LazyView;

public interface ISearchLocationView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setInstitutions(List<InstitutionBean> institutitions);
	
	void setSearchLocation(LatLng searchLocation);
	
	void setOkBtnClickHandler(ClickHandler handler);
}
