package com.infoklinik.rsvp.client.inst.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class InstitutionProfileLocationView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionProfileLocationView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institution;
	
	public InstitutionProfileLocationView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setInstitution(InstitutionBean institution) {
		
		this.institution = institution;
	}
	
	@Override
	public void loadContent() {
		
		initMap();
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	private void initMap() {
		
		LatLng location = LatLng.create(institution.getLocationLat(), institution.getLocationLng());
		
		MapOptions mapOpts = MapOptions.create();
		mapOpts.setZoom(15);
		mapOpts.setCenter(location);
		mapOpts.setMapTypeId(MapTypeId.ROADMAP);
		final GoogleMap map = GoogleMap.create(Document.get().getElementById("map_canvas"), mapOpts);

		MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setPosition(location);
		markerOpts.setMap(map);
		markerOpts.setTitle(institution.getName());
		
		Marker.create(markerOpts);
	}
}
