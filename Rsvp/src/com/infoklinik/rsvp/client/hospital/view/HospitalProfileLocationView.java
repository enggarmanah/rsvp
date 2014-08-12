package com.infoklinik.rsvp.client.hospital.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class HospitalProfileLocationView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, HospitalProfileLocationView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institution;
	
	public HospitalProfileLocationView() {
		
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

		InfoWindowOptions infowindowOpts = InfoWindowOptions.create();
		infowindowOpts.setContent(institution.getHtmlInfo());
		final InfoWindow infowindow = InfoWindow.create(infowindowOpts);

		MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setPosition(location);
		markerOpts.setMap(map);
		markerOpts.setTitle(institution.getName());
		
		final Marker marker = Marker.create(markerOpts);
		marker.addClickListener(new Marker.ClickHandler() {
			public void handle(MouseEvent event) {
				infowindow.open(map, marker);
			}
		});
		
		map.addClickListener(new GoogleMap.ClickHandler() {
			
			@Override
			public void handle(MouseEvent event) {
				
				MarkerOptions markerOpts = MarkerOptions.create();
				markerOpts.setPosition(event.getLatLng());
				markerOpts.setMap(map);
				
				final Marker marker = Marker.create(markerOpts);
				InfoWindowOptions infowindowOpts = InfoWindowOptions.create();
				infowindowOpts.setContent(event.getLatLng().toString() + ":" + map.getZoom());
				final InfoWindow infowindow = InfoWindow.create(infowindowOpts);
				infowindow.open(map, marker);
			}
		});
	}
}
