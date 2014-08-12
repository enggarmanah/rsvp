package com.infoklinik.rsvp.client.main.view;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;
import com.infoklinik.rsvp.client.main.presenter.LocationListener;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;

public class LocationDlg {
	
	@UiField
	static HTMLPanel infoPanel;
	
	@UiField
	ListBox distanceLb;
	
	@UiField
	static Button okBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, LocationDlg> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	ServiceTypeSearchBean serviceTypeSearchBean = new ServiceTypeSearchBean();
	
	private static DialogBox dialogBox;
	
	private static LocationDlg locationDlg;
	
	private static LatLng locationLatLng;
	
	private static Marker marker;
	
	private static LocationListener locationListener;
	
	private static LocationBean location;
	
	private LocationDlg() {
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
		
		okBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (location == null) {
					location = new LocationBean();
				}
				
				location.setLat(locationLatLng.lat());
				location.setLng(locationLatLng.lng());
				location.setDistance(Integer.valueOf(distanceLb.getValue(distanceLb.getSelectedIndex())));
				
				locationListener.setLocation(location);
				
				hide();
			}
		});
	}
	
	public static void setLocationListener(LocationListener listener) {
		
		locationListener = listener;
	}
	
	public static void setLocation(LocationBean loc) {
		
		location = loc;
	}
	
	private static void init() {
		
		if (locationDlg == null) {
			locationDlg = new LocationDlg();
		}
	}
	
	public static void show() {
		
		init();
		
		Window.scrollTo (0,0);
		
		fadeOut();
		
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft(), 70);
		dialogBox.show();
		
		initMap();
		
		fadeIn();
	}
	
	private static void hide() {
		
		fadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				dialogBox.hide();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}
	
	private static void fadeIn() {
		
		dialogBox.removeStyleName(Constant.STYLE_FADE_OUT);
		dialogBox.addStyleName(Constant.STYLE_FADE_IN);
	}
	
	private static void fadeOut() {
		
		dialogBox.removeStyleName(Constant.STYLE_FADE_IN);
		dialogBox.addStyleName(Constant.STYLE_FADE_OUT);
	}
	
	
	private static void initMap() {
		
		if (location != null) {
			locationLatLng = LatLng.create(location.getLat(), location.getLng());
		} else {
			locationLatLng = LatLng.create(Constant.MAP_DEFAULT_LAT, Constant.MAP_DEFAULT_LNG);
		}
		
		int scale = Constant.MAP_DEFAULT_SCALE;
		
		MapOptions mapOpts = MapOptions.create();
		mapOpts.setZoom(scale);
		mapOpts.setCenter(locationLatLng);
		mapOpts.setMapTypeId(MapTypeId.ROADMAP);
		
		final GoogleMap map = GoogleMap.create(Document.get().getElementById("map_canvas"), mapOpts);
		
		MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setPosition(locationLatLng);
		markerOpts.setMap(map);
		
		marker = Marker.create(markerOpts);
		
		if (Geolocation.isSupported() && location == null) {
			Geolocation.getIfSupported().getCurrentPosition(
				new Callback<Position, PositionError>() {

					@Override
					public void onSuccess(Position result) {
						
						Coordinates coords = result.getCoordinates();
						locationLatLng = LatLng.create(coords.getLatitude(), coords.getLongitude());
						
						marker.setPosition(locationLatLng);
						map.setCenter(locationLatLng);
					}

					@Override
					public void onFailure(PositionError reason) {
					}
				});
		} 
		
		map.addClickListener(new GoogleMap.ClickHandler() {
			
			@Override
			public void handle(MouseEvent event) {
				
				if (marker != null) {
					
					locationLatLng = event.getLatLng();
					marker.setPosition(locationLatLng);
					map.setCenter(locationLatLng);
				}
			}
		});
	}
}