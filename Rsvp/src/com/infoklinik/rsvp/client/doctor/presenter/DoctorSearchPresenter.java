package com.infoklinik.rsvp.client.doctor.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.maps.gwt.client.LatLng;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.SuggestionOracle;
import com.infoklinik.rsvp.client.doctor.DoctorEventBus;
import com.infoklinik.rsvp.client.doctor.presenter.interfaces.IDoctorSearchView;
import com.infoklinik.rsvp.client.doctor.view.DoctorSearchView;
import com.infoklinik.rsvp.client.main.presenter.LocationListener;
import com.infoklinik.rsvp.client.main.view.LocationDlg;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CityServiceAsync;
import com.infoklinik.rsvp.client.rpc.DoctorServiceAsync;
import com.infoklinik.rsvp.client.rpc.MasterCodeServiceAsync;
import com.infoklinik.rsvp.client.rpc.SpecialityServiceAsync;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.DoctorSearchBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.SpecialityBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = DoctorSearchView.class)
public class DoctorSearchPresenter extends LazyPresenter<IDoctorSearchView, DoctorEventBus> implements LocationListener {
	
	@Inject
	CityServiceAsync cityService;
	
	@Inject
	DoctorServiceAsync doctorService;
	
	@Inject
	SpecialityServiceAsync specialityService;
	
	@Inject
	MasterCodeServiceAsync masterCodeService;
	
	private DoctorSearchPresenter doctorSearchPresenter;
	
	@Override
	public void bindView() {
		
		doctorSearchPresenter = this;
		
		initCities();
		initSpecialities();
		initDays();
		initSearchOptionRbHandler();
		initSearchSbHandler();
		initCityLbHandler();
		initSearchBtnHandler();		
	}
	
	private void initCities() {
		
		cityService.getCities(new CitySearchBean(), new AsyncCallback<List<CityBean>>() {
			
			@Override
			public void onSuccess(List<CityBean> cityBeans) {
				view.setCities(cityBeans);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void initSpecialities() {
		
		specialityService.getSpecialities(new AsyncCallback<List<SpecialityBean>>() {
			
			@Override
			public void onSuccess(List<SpecialityBean> specialityBeans) {
				view.setSpecialities(specialityBeans);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void initDays() {
		
		masterCodeService.getMasterCodes(MasterCodeBean.DAY, new AsyncCallback<List<MasterCodeBean>>() {
			
			@Override
			public void onSuccess(List<MasterCodeBean> masterCodeBeans) {
				view.setDays(masterCodeBeans);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void initSearchOptionRbHandler() {
		
		view.setSearchOptionRbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				String suggestType = SuggestionOracle.SEARCH_NONE;
				HasText searchText = view.getSearchSb();
				
				if (Constant.SEARCH_BY_NAME.equals(view.getSearchOptionValue())) {
					
					searchText.setText(Constant.EMPTY_STRING);
					
				} else if (Constant.SEARCH_BY_STREET.equals(view.getSearchOptionValue())) {
					
					suggestType = SuggestionOracle.SEARCH_STREET;
					searchText.setText(Constant.EMPTY_STRING);
					
					NotificationDlg.info(Message.NOTIFY_STREET_SELECTED);
					
				} else if (Constant.SEARCH_BY_REGION.equals(view.getSearchOptionValue())) {
					
					suggestType = SuggestionOracle.SEARCH_REGION;
					searchText.setText(Constant.EMPTY_STRING);
					
					NotificationDlg.info(Message.NOTIFY_REGION_SELECTED);
					
				} else if (Constant.SEARCH_BY_DISTANCE.equals(view.getSearchOptionValue())) {
					
					searchText.setText(Constant.EMPTY_STRING);
				}
				
				view.setSuggestType(suggestType);
			}
		});
	}
	
	private void initSearchSbHandler() {
		
		view.setSearchSbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (Constant.SEARCH_BY_DISTANCE.equals(view.getSearchOptionValue())) {
					
					LocationDlg.show();
					LocationDlg.setLocationListener(doctorSearchPresenter);
				}
			}
		});
	}
	
	private void initCityLbHandler() {
		
		view.setCityLbHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				DoctorSearchBean doctorSearch = view.getDoctorSearch();
				view.setSuggestCityId(doctorSearch.getCityId().toString());
			}
		});
	}
	
	private void initSearchBtnHandler() {
		
		view.setSearchBtnHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ProgressDlg.show();
				
				doctorService.getDoctors(view.getDoctorSearch(), new AsyncCallback<List<DoctorBean>>() {
					
					@Override
					public void onSuccess(List<DoctorBean> doctors) {
						
						if (doctors.size() == 0) {
							
							ProgressDlg.hidePrompt();
							NotificationDlg.warning(Message.SEARCH_RESULT_EMPTY);
							
							//eventBus.loadPartner();
							
						} else {
							
							LocationBean location = view.getDoctorSearch().getLocation();
							LatLng latLng = null;
							
							if (location != null) {
								latLng = LatLng.create(location.getLat(), location.getLng());
							}
							
							eventBus.setSearchLocation(latLng);
							eventBus.loadDoctorSearchResult(doctors);
							
							if (doctors.size() == Constant.QUERY_MAX_RESULT) {
								ProgressDlg.hidePrompt();
								NotificationDlg.info(Message.SEARCH_EXCEED_QUERY_MAX_RESULT);
							} else {
								ProgressDlg.hide();
							}
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						
						ProgressDlg.failure();
					}
				});
			}
		});
	}
	
	public void onLoadDoctorSearch() {
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				view.fadeOut();
				eventBus.setSearchPanel(view.asWidget());
				view.fadeIn();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}
	
	public void onRemoveDoctorSearch() {
		
		view.fadeOut();
	}
	
	public void setLocation(LocationBean location) {
		
		view.setLocation(location);
	}
}
