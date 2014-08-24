package com.infoklinik.rsvp.client.admin.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionLocationView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class AdminInstitutionLocationView extends BaseView implements IAdminInstitutionLocationView {
	
	private DialogBox dialogBox;
	
	@UiField
	Button okBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminInstitutionLocationView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institution;
	
	GoogleMap map;
	Marker marker;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
	}
	
	public InstitutionBean getInstitution() {
		
		institution.setLocationLat(marker.getPosition().lat());
		institution.setLocationLng(marker.getPosition().lng());
		
		return institution;
	}

	public void setInstitution(InstitutionBean institution) {
		
		this.institution = institution;
	}

	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return dialogBox;
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void show() {
		
		fadeOut();
		
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft() + Constant.POPUP_L2_LEFT, Constant.POPUP_L2_TOP);
		dialogBox.show();
		
		fadeIn();
		
		initMap();
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
		
		LatLng location = LatLng.create(institution.getLocationLat(), institution.getLocationLng());
		
		MapOptions mapOpts = MapOptions.create();
		mapOpts.setZoom(15);
		mapOpts.setCenter(location);
		mapOpts.setMapTypeId(MapTypeId.ROADMAP);
		
		map = GoogleMap.create(Document.get().getElementById("map_canvas"), mapOpts);
		
		setMarkerPosition(location);
		
		map.addClickListener(new GoogleMap.ClickHandler() {
			
			@Override
			public void handle(MouseEvent event) {
				
				setMarkerPosition(event.getLatLng());
				map.setCenter(event.getLatLng());
			}
		});
	}
	
	private void setMarkerPosition(LatLng position) {
		
		if (marker != null) {
			GoogleMap emptyMap = null;
			marker.setMap(emptyMap);
		}
		
		MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setPosition(position);
		markerOpts.setMap(map);
		
		marker = Marker.create(markerOpts);
	}
}