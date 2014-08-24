package com.infoklinik.rsvp.client.inst.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.maps.gwt.client.LatLng;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.SuggestionOracle;
import com.infoklinik.rsvp.client.inst.InstitutionEventBus;
import com.infoklinik.rsvp.client.inst.presenter.interfaces.IHospitalSearchView;
import com.infoklinik.rsvp.client.inst.view.HospitalSearchView;
import com.infoklinik.rsvp.client.main.presenter.LocationListener;
import com.infoklinik.rsvp.client.main.view.LocationDlg;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CityServiceAsync;
import com.infoklinik.rsvp.client.rpc.InstitutionServiceAsync;
import com.infoklinik.rsvp.client.rpc.InsuranceServiceAsync;
import com.infoklinik.rsvp.client.rpc.MasterCodeServiceAsync;
import com.infoklinik.rsvp.client.rpc.SpecialityServiceAsync;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.CitySearchBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InstitutionSearchBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = HospitalSearchView.class)
public class HospitalSearchPresenter extends LazyPresenter<IHospitalSearchView, InstitutionEventBus> implements LocationListener {
	
	@Inject
	CityServiceAsync cityService;
	
	@Inject
	InstitutionServiceAsync institutionService;
	
	@Inject
	InsuranceServiceAsync insuranceService;
	
	@Inject
	SpecialityServiceAsync specialityService;
	
	@Inject
	MasterCodeServiceAsync masterCodeService;
	
	LocationBean locationBean;
	
	private HospitalSearchPresenter hospitalSearchPresenter;
	
	@Override
	public void bindView() {
		
		hospitalSearchPresenter = this;
		
		initCities();
		initInsurances();
		initHospitalTypes();
		initSearchOptionRbHandler();
		initCityLbHandler();
		initSearchSbHandler();
		initSearchBtnHandler();
	}
	
	private void initCities() {
		
		cityService.getCities(new CitySearchBean(), new AsyncCallback<List<CityBean>>() {
			
			@Override
			public void onSuccess(final List<CityBean> cities) {
				
				view.setCities(cities);
				
				CityBean nearestCity = ClientUtil.getNearestCity();
				
				if (nearestCity == null) {
				
					if (Geolocation.isSupported() && ClientUtil.isReqGeoLocation()) {
						
						ClientUtil.setReqGeoLocation(false);
						
						Geolocation.getIfSupported().getCurrentPosition(
							new Callback<Position, PositionError>() {
	
								@Override
								public void onSuccess(Position position) {
									
									CityBean city = ClientUtil.getNearestCity(cities, position);
									
									if (city != null) {
										view.setCity(city);
									}
								}
	
								@Override
								public void onFailure(PositionError reason) {
								}
							});
					} 
					
				} else {
					
					view.setCity(nearestCity);
				} 
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void initInsurances() {
		
		insuranceService.getInsurances(new AsyncCallback<List<InsuranceBean>>() {
			
			@Override
			public void onSuccess(List<InsuranceBean> insuranceBeans) {
				view.setInsurances(insuranceBeans);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void initHospitalTypes() {
		
		masterCodeService.getMasterCodes(MasterCodeBean.HOSPITAL_TYPE, new AsyncCallback<List<MasterCodeBean>>() {
			
			@Override
			public void onSuccess(List<MasterCodeBean> masterCodeBeans) {
				view.setInstitutionTypes(masterCodeBeans);
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
	
	private void initCityLbHandler() {
		
		view.setCityLbHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				InstitutionSearchBean instSearch = view.getInstitutionSearch();
				view.setSuggestCityId(instSearch.getCityId().toString());
			}
		});
	}
	
	private void initSearchSbHandler() {
		
		view.setSearchSbHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (Constant.SEARCH_BY_DISTANCE.equals(view.getSearchOptionValue())) {
					
					LocationDlg.show();
					LocationDlg.setLocationListener(hospitalSearchPresenter);
				}
			}
		});
	}
	
	private void initSearchBtnHandler() {
		
		view.setSearchBtnHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ProgressDlg.show();
				
				institutionService.getInstitutions(view.getInstitutionSearch(), new AsyncCallback<List<InstitutionBean>>() {
					
					@Override
					public void onSuccess(List<InstitutionBean> institutions) {
						
						if (institutions.size() == 0) {
							
							ProgressDlg.hidePrompt();
							NotificationDlg.warning(Message.SEARCH_RESULT_EMPTY);
							
							//eventBus.loadPartner();
							
						} else {
							
							LocationBean location = view.getInstitutionSearch().getLocation();
							LatLng latLng = null;
							
							if (location != null) {
								latLng = LatLng.create(location.getLat(), location.getLng());
							}
							
							eventBus.setSearchLocation(latLng);
							eventBus.loadInstitutionSearchResult(institutions);
							
							if (institutions.size() == Constant.QUERY_MAX_RESULT) {
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
	
	public void onLoadHospitalSearch() {
		
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
	
	public void onRemoveHospitalSearch() {
		
		view.fadeOut();
	}
	
	public void setLocation(LocationBean location) {
		
		view.setLocation(location);
	}
}
