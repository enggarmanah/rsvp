package com.infoklinik.rsvp.client.search.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.search.presenter.interfaces.ISearchLocationView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.GisLatLng;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;
import com.infoklinik.rsvp.shared.SharedUtil;

public class SearchLocationView extends BaseView implements ISearchLocationView {
	
	private DialogBox dialogBox;
	
	@UiField
	HTMLPanel infoPanel;
	
	@UiField
	Button okBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, SearchLocationView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	ServiceTypeSearchBean serviceTypeSearchBean = new ServiceTypeSearchBean();
	
	List<InstitutionBean> institutions;
	
	LatLng searchLocation;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return dialogBox;
	}
	
	public void setSearchLocation(LatLng latLng) {
		
		this.searchLocation = latLng;
	}
	
	public void setInstitutions(List<InstitutionBean> institutions) {
		
		this.institutions = institutions;
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void show() {
		
		goToTop();
		
		fadeOut();
		
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft(), 70);
		dialogBox.show();
		
		initMap();
		
		fadeIn();
	}
	
	public void hide() {
		
		fadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				dialogBox.hide();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}
	
	private void initMap() {
		
		LatLng location = null;
		
		if (institutions != null && institutions.size() > 0) {
			
			List<GisLatLng> latLngs = new ArrayList<GisLatLng>();
			
			if (searchLocation != null) {
				
				latLngs.add(new GisLatLng(searchLocation.lat(), searchLocation.lng()));
			}
			
			for (InstitutionBean institution : institutions) {
				
				LatLng latLng = LatLng.create(institution.getLocationLat(), institution.getLocationLng());
				latLngs.add(new GisLatLng(latLng.lat(), latLng.lng()));
			}
			
			GisLatLng centerLatLng = SharedUtil.getCenter(latLngs);
			int scale = SharedUtil.getScale(latLngs) + 1;
			
			location = LatLng.create(centerLatLng.getLat(), centerLatLng.getLng());
			
			MapOptions mapOpts = MapOptions.create();
			mapOpts.setZoom(scale);
			mapOpts.setCenter(location);
			mapOpts.setMapTypeId(MapTypeId.ROADMAP);
			
			final GoogleMap map = GoogleMap.create(Document.get().getElementById("map_canvas"), mapOpts);
			
			for (InstitutionBean institution : institutions) {
				
				location = LatLng.create(institution.getLocationLat(), institution.getLocationLng());
			
				InfoWindowOptions infowindowOpts = InfoWindowOptions.create();
				infowindowOpts.setContent(institution.getHtmlInfo());
				final InfoWindow infowindow = InfoWindow.create(infowindowOpts);
	
				MarkerOptions markerOpts = MarkerOptions.create();
				markerOpts.setPosition(location);
				markerOpts.setMap(map);
				
				String title = institution.getName();
				
				if (institution.getDistance() != null) {
					title = title + " - " + SharedUtil.getDistanceInfo(institution.getDistance());
				} 
				
				markerOpts.setTitle(title);
				
				final Marker marker = Marker.create(markerOpts);
				marker.addClickListener(new Marker.ClickHandler() {
					public void handle(MouseEvent event) {
						infowindow.open(map, marker);
					}
				});
			}
			
			if (searchLocation != null) {
				
				MarkerOptions markerOpts = MarkerOptions.create();
				
				markerOpts.setPosition(searchLocation);
				markerOpts.setMap(map);
				markerOpts.setIcon(MarkerImage.create("images/marker-blue.png"));
				markerOpts.setTitle("Lokasi anda / pusat pencarian");
				
				Marker.create(markerOpts);
			}
		}
	}
}