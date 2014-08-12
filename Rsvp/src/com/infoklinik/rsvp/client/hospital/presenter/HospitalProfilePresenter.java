package com.infoklinik.rsvp.client.hospital.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.hospital.HospitalEventBus;
import com.infoklinik.rsvp.client.hospital.presenter.interfaces.IHospitalProfileView;
import com.infoklinik.rsvp.client.hospital.view.HospitalProfileView;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.BranchServiceAsync;
import com.infoklinik.rsvp.client.rpc.DoctorServiceAsync;
import com.infoklinik.rsvp.client.rpc.GalleryServiceAsync;
import com.infoklinik.rsvp.client.rpc.InstitutionServiceAsync;
import com.infoklinik.rsvp.client.rpc.InsuranceServiceAsync;
import com.infoklinik.rsvp.client.rpc.ServiceServiceAsync;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = HospitalProfileView.class)
public class HospitalProfilePresenter extends LazyPresenter<IHospitalProfileView, HospitalEventBus> {
	
	@Inject
	InstitutionServiceAsync institutionService;
	
	@Inject
	DoctorServiceAsync doctorService;
	
	@Inject
	ServiceServiceAsync serviceService;
	
	@Inject
	InsuranceServiceAsync insuranceService;
	
	@Inject
	GalleryServiceAsync galleryService;
	
	@Inject
	BranchServiceAsync branchService;
	
	InstitutionBean institution;
	
	List<ServiceBean> services;
	List<ServiceBean> promotions;
	List<InsuranceBean> insurances;
	List<GenericBean<DoctorBean>> doctors;
	List<GalleryBean> galleries;
	List<BranchBean> branches;
	
	@Override
	public void bindView() {
		
		initLocationHandler();
		initServiceHandler();
		initDoctorHandler();
		initGalleryHandler();
		initBranchHandler();
		initBtnHandler();
	}
	
	private void initLocationHandler() {
		
		view.setLocationMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.showLocation();
			}
		});
		
	}
	
	private void initServiceHandler() {
		
		view.setServiceMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (services == null) {
					serviceService.getServices(institution.getId(), new AsyncCallback<List<ServiceBean>>() {
						
						@Override
						public void onSuccess(List<ServiceBean> result) {
							services = result;
							view.setServices(services);
						}
						
						@Override
						public void onFailure(Throwable caught) {
						}
					});
				}
				
				if (promotions == null) {
					serviceService.getPromotions(institution.getId(), new AsyncCallback<List<ServiceBean>>() {
						
						@Override
						public void onSuccess(List<ServiceBean> result) {
							promotions = result;
							view.setPromotions(promotions);
						}
						
						@Override
						public void onFailure(Throwable caught) {
						}
					});
				}
				
				if (insurances == null) {
					
					ProgressDlg.show();
					
					insuranceService.getInsurances(institution.getId(), new AsyncCallback<List<InsuranceBean>>() {
						
						@Override
						public void onSuccess(List<InsuranceBean> result) {
							insurances = result;
							view.setInsurances(insurances);
							view.showService();
							ProgressDlg.hide();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							ProgressDlg.failure();
						}
					});
					
				} else {
					view.showService();
				}
			}
		});
	}
	
	private void initDoctorHandler() {
		
		view.setDoctorMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (doctors == null) {
					
					ProgressDlg.show();
					
					doctorService.getDoctors(institution.getId(), new AsyncCallback<List<DoctorBean>>() {
						
						@Override
						public void onSuccess(List<DoctorBean> result) {
							
							doctors = new ArrayList<GenericBean<DoctorBean>>();
							
							for (final DoctorBean doctor : result) {
								
								ClickHandler showHandler = new ClickHandler() {
									
									@Override
									public void onClick(ClickEvent event) {
										eventBus.loadDoctorProfile(doctor);
									}
								};
								
								HandlerManager handlerMgr = new HandlerManager();
								handlerMgr.setShowHandler(showHandler);
								
								GenericBean<DoctorBean> genericDoctor = new GenericBean<DoctorBean>(doctor, handlerMgr);
								doctors.add(genericDoctor);
							} 
							
							view.setDoctors(doctors);
							view.showDoctor();
							ProgressDlg.hide();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							ProgressDlg.failure();
						}
					});
					
				} else {
					view.showDoctor();
				}
				
			}
		});
	}
	
	private void initGalleryHandler() {
		
		view.setGalleryMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (galleries == null) {
					
					ProgressDlg.show();
					
					galleryService.getGalleries(institution.getId(), new AsyncCallback<List<GalleryBean>>() {
						
						@Override
						public void onSuccess(List<GalleryBean> result) {
							
							galleries = result;
							view.setGalleries(galleries);
							view.showGallery();
							ProgressDlg.hide();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							ProgressDlg.failure();
						}
					});
					
				} else {
					view.showGallery();
				}
			}
		});
	}
	
	private void initBranchHandler() {
		
		view.setBranchMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (branches == null) {
					
					ProgressDlg.show();
					
					branchService.getBranches(institution.getId(), new AsyncCallback<List<BranchBean>>() {
						
						@Override
						public void onSuccess(List<BranchBean> result) {
							
							branches = result;
							view.setBranches(branches);
							view.showBranch();
							ProgressDlg.hide();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							ProgressDlg.failure();
						}
					});
					
				} else {
					view.showBranch();
				}
			}
		});
	}
	
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	public void onLoadHospitalProfile(InstitutionBean institution) {
		
		reset();
		
		Window.scrollTo (0,0);
			
		this.institution = institution;
		
		view.setInstitution(institution);
		view.show();
		view.showLocation();
		
		if (!ClientUtil.isAdminUser) {
			increaseViewCount();
		}
	}
	
	private void reset() {
		
		services = null;
		promotions = null;
		insurances = null;
		doctors = null;
		galleries = null;
		branches = null;
	}
	
	private void increaseViewCount() {
		
		institutionService.increaseViewCount(institution.getId(), new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {}
			
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}
