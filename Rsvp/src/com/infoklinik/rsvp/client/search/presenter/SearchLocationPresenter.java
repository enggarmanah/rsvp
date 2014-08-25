package com.infoklinik.rsvp.client.search.presenter;

import java.util.List;

import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.maps.gwt.client.LatLng;
import com.infoklinik.rsvp.client.search.SearchEventBus;
import com.infoklinik.rsvp.client.search.presenter.interfaces.ISearchLocationView;
import com.infoklinik.rsvp.client.search.view.SearchLocationView;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = SearchLocationView.class)
public class SearchLocationPresenter extends LazyPresenter<ISearchLocationView, SearchEventBus> {
	
	List<InstitutionBean> institutions;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	public void onSetSearchLocation(LatLng searchLocation) {
		
		view.setSearchLocation(searchLocation);
	}
	
	public void onLoadSearchLocations(List<InstitutionBean> institutions) {
		
		this.institutions = institutions;
		
		view.setInstitutions(institutions);
		view.show();
	}
}
