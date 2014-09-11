package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionView;
import com.infoklinik.rsvp.client.admin.view.AdminInstitutionView;
import com.infoklinik.rsvp.client.main.view.ConfirmDlg;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.BranchServiceAsync;
import com.infoklinik.rsvp.client.rpc.DoctorServiceAsync;
import com.infoklinik.rsvp.client.rpc.GalleryServiceAsync;
import com.infoklinik.rsvp.client.rpc.InstitutionServiceAsync;
import com.infoklinik.rsvp.client.rpc.InsuranceServiceAsync;
import com.infoklinik.rsvp.client.rpc.MasterCodeServiceAsync;
import com.infoklinik.rsvp.client.rpc.ServiceServiceAsync;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.CityBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.RegionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.SearchSuggestion;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.StreetBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminInstitutionView.class)
public class AdminInstitutionPresenter extends LazyPresenter<IAdminInstitutionView, AdminEventBus> {
	
	@Inject
	MasterCodeServiceAsync masterCodeService;
	
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
	List<GenericBean<ServiceBean>> genServices;
	List<InsuranceBean> insurances;
	List<GenericBean<InsuranceBean>> genInsurances;
	List<DoctorBean> doctors;
	List<GenericBean<DoctorBean>> genDoctors;
	List<GalleryBean> galleries;
	List<GenericBean<GalleryBean>> genGalleries;
	List<BranchBean> branches;
	List<GenericBean<BranchBean>> genBranches;
	
	private boolean isInitCategoryCompleted = false;
	private boolean isInitTypesCompleted = false;
	private boolean isInitPartnerTypeCompleted = false;
	
	HashMap<DoctorBean, HashMap<ScheduleBean, HandlerManager>> doctorScheduleHandlerMgrs;
	
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initCategories();
		initPartnerTypes();
		
		initMainMenuClickHandler();
		initServiceMenuClickHandler();
		initDoctorMenuClickHandler();
		initGalleryMenuClickHandler();
		initBranchMenuClickHandler();
		
		initCategoryChangeHandler();
		initCitySelectionHandler();
		initRegionSelectionHandler();
		initStreetSelectionHandler();
		initOp24HoursChangeHandler();
		
		initLocationClickHandler();
		
		initAddServiceBtnClickHandler();
		initAddInsuranceBtnClickHandler();
		initAddDoctorBtnClickHandler();
		initAddGalleryBtnClickHandler();
		initAddBranchBtnClickHandler();
		
		initOkBtnClickHandler();
		initCancelBtnClickHandler();
	}
	
	private void initCategories() {
		
		masterCodeService.getMasterCodes(MasterCodeBean.INST_CATEGORY, new AsyncCallback<List<MasterCodeBean>>() {
			
			@Override
			public void onSuccess(List<MasterCodeBean> masterCodeBeans) {
				
				view.setCategories(masterCodeBeans);
				isInitCategoryCompleted = true;
				initInstitution();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
		
	}
	
	private void initTypes() {
		
		String codeType = Constant.EMPTY_STRING;
		String category = institution.getCategory();
		
		if (Constant.CATEGORY_CLINIC.equals(category)) {
			codeType = MasterCodeBean.CLINIC_TYPE;
		} else if (Constant.CATEGORY_HOSPITAL.equals(category)) {
			codeType = MasterCodeBean.HOSPITAL_TYPE;
		} else if (Constant.CATEGORY_LABORATORY.equals(category)) {
			codeType = MasterCodeBean.LAB_TYPE;
		}
		
		masterCodeService.getMasterCodes(codeType, new AsyncCallback<List<MasterCodeBean>>() {
			
			@Override
			public void onSuccess(List<MasterCodeBean> masterCodeBeans) {
				view.setTypes(masterCodeBeans);
				isInitTypesCompleted = true;
				initInstitution();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
		
	}
	
	private void initPartnerTypes() {
		
		masterCodeService.getMasterCodes(MasterCodeBean.PARTNER_TYPE, new AsyncCallback<List<MasterCodeBean>>() {
			
			@Override
			public void onSuccess(List<MasterCodeBean> masterCodeBeans) {
				view.setPartnerTypes(masterCodeBeans);
				isInitPartnerTypeCompleted = true;
				initInstitution();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});	
	}
	
	private void initInstitution() {
		
		if (isInitCategoryCompleted && isInitTypesCompleted && isInitPartnerTypeCompleted) {
			if (institution != null) {
				view.setInstitution(institution);
			}
		}
	}
	
	private void initMainMenuClickHandler() {
		
		view.setLocationMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				view.showLocation();
			}
		});
		
	}
	
	private void initServiceMenuClickHandler() {
		
		view.setServiceMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (institution.getId() != null && genServices == null) {
					serviceService.getServices(institution.getId(), new AsyncCallback<List<ServiceBean>>() {
						
						@Override
						public void onSuccess(List<ServiceBean> result) {
							
							genServices = new ArrayList<GenericBean<ServiceBean>>();
							services = result;
							
							for (ServiceBean service : result) {
								
								HandlerManager handlerMgr = new HandlerManager();
								
								handlerMgr.setUpdateHandler(getServiceUpdateHandler(service));
								handlerMgr.setDeleteHandler(getServiceDeleteHandler(service));
								
								GenericBean<ServiceBean> genService = new GenericBean<ServiceBean>(service, handlerMgr);
								genServices.add(genService);
							}
							
							view.setServices(genServices);
						}
						
						@Override
						public void onFailure(Throwable caught) {
						}
					});
				
				} else if (genServices == null) {
					
					genServices = new ArrayList<GenericBean<ServiceBean>>();
					services = new ArrayList<ServiceBean>();
					view.setServices(genServices);
				}
				
				if (institution.getId() != null && genInsurances == null) {
					
					ProgressDlg.show();
					
					insuranceService.getInsurances(institution.getId(), new AsyncCallback<List<InsuranceBean>>() {
						
						@Override
						public void onSuccess(List<InsuranceBean> result) {
							
							genInsurances = new ArrayList<GenericBean<InsuranceBean>>();
							insurances = result;
							
							for (InsuranceBean insurance : result) {
								
								HandlerManager handlerMgr = new HandlerManager();
								
								handlerMgr.setUpdateHandler(getInsuranceUpdateHandler(insurance));
								handlerMgr.setDeleteHandler(getInsuranceDeleteHandler(insurance));
								
								GenericBean<InsuranceBean> genInsurance = new GenericBean<InsuranceBean>(insurance, handlerMgr);
								genInsurances.add(genInsurance);
							}
							
							view.setInsurances(genInsurances);
							view.showService();
							
							ProgressDlg.hide();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							
							ProgressDlg.failure();
						}
					});
					
				} else {
					view.setInsurances(new ArrayList<GenericBean<InsuranceBean>>());
					view.showService();
				}
			}
		});
	}
	
	private void initDoctorMenuClickHandler() {
		
		view.setDoctorMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (doctors == null) {
					
					ProgressDlg.show();
					
					doctorService.getDoctors(institution.getId(), new AsyncCallback<List<DoctorBean>>() {
						
						@Override
						public void onSuccess(List<DoctorBean> result) {
							
							genDoctors = new ArrayList<GenericBean<DoctorBean>>();
							doctors = result;
							
							doctorScheduleHandlerMgrs = new HashMap<DoctorBean, HashMap<ScheduleBean,HandlerManager>>();
							
							for (final DoctorBean doctor : result) {
								
								HandlerManager handlerMgr = new HandlerManager();
								handlerMgr.setUpdateHandler(getDoctorUpdateHandler(doctor));
								handlerMgr.setDeleteHandler(getDoctorDeleteHandler(doctor));
								handlerMgr.setAddHandler(getDoctorScheduleAddHandler(doctor));
								
								HashMap<ScheduleBean, HandlerManager> scheduleHandleMgrs = new HashMap<ScheduleBean, HandlerManager>();
								
								if (doctor.getSchedules() != null) {
									
									for (ScheduleBean schedule : doctor.getSchedules()) {
										
										schedule.setDoctor(doctor);
										
										ClickHandler schUpdateHandler = getDoctorScheduleUpdateHandler(schedule);
										ClickHandler schDeleteHandler = getDoctorScheduleDeleteHandler(schedule);
										
										HandlerManager schHandlerMgr = new HandlerManager();
										schHandlerMgr.setUpdateHandler(schUpdateHandler);
										schHandlerMgr.setDeleteHandler(schDeleteHandler);
										
										scheduleHandleMgrs.put(schedule, schHandlerMgr);
									}
								}
								
								GenericBean<DoctorBean> genericDoctor = new GenericBean<DoctorBean>(doctor, handlerMgr);
								genDoctors.add(genericDoctor);
								
								doctorScheduleHandlerMgrs.put(doctor, scheduleHandleMgrs);
							} 
							
							view.setDoctorScheduleHandlerMgrs(doctorScheduleHandlerMgrs);
							view.setDoctors(genDoctors);
							
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
	
	private void initGalleryMenuClickHandler() {
		
		view.setGalleryMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (galleries == null) {
					
					ProgressDlg.show();
					
					galleryService.getGalleries(institution.getId(), new AsyncCallback<List<GalleryBean>>() {
						
						@Override
						public void onSuccess(List<GalleryBean> result) {
							
							galleries = result;
							genGalleries = new ArrayList<GenericBean<GalleryBean>>();
							
							for (GalleryBean gallery : result) {
								
								HandlerManager handlerMgr = new HandlerManager();
								handlerMgr.setUpdateHandler(getGalleryUpdateHandler(gallery));
								handlerMgr.setDeleteHandler(getGalleryDeleteHandler(gallery));
								
								GenericBean<GalleryBean> genGallery = new GenericBean<GalleryBean>(gallery, handlerMgr);
								
								genGalleries.add(genGallery);
							}
							
							view.setGalleries(genGalleries);
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
	
	private void initBranchMenuClickHandler() {
		
		view.setBranchMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (branches == null) {
					
					ProgressDlg.show();
					
					branchService.getBranches(institution.getId(), new AsyncCallback<List<BranchBean>>() {
						
						@Override
						public void onSuccess(List<BranchBean> result) {
							
							genBranches = new ArrayList<GenericBean<BranchBean>>();
							branches = result;
							
							for (BranchBean branch : branches) {
								
								HandlerManager handlerMgr = new HandlerManager();
								handlerMgr.setDeleteHandler(getBranchDeleteHandler(branch));
								
								GenericBean<BranchBean> genericBranch = new GenericBean<BranchBean>(branch, handlerMgr);
								genBranches.add(genericBranch);
							}
							
							view.setBranches(genBranches);
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
	
	private void initLocationClickHandler() {
		
		view.setLocationClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.getInstitutionLocation(institution);
			}
		});
	}
	
	private void initAddServiceBtnClickHandler() {
		
		view.setAddServiceBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addService();
			}
		});
	}
	
	private void initAddInsuranceBtnClickHandler() {
		
		view.setAddInsuranceBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addInstitutionInsurance();
			}
		});
	}
	
	private void initAddDoctorBtnClickHandler() {
		
		view.setAddDoctorBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addInstitutionDoctor();
				eventBus.setInstitutionExistingDoctors(doctors);
			}
		});
	}
	
	private void initAddGalleryBtnClickHandler() {
			
		view.setAddGalleryBtnClickHandler(new ClickHandler() {
				
			@Override
			public void onClick(ClickEvent event) {
				
				GalleryBean gallery = new GalleryBean();
				gallery.setInstitution(institution);
				eventBus.addInstitutionGallery(gallery);
			}
		});
	}
	
	private void initAddBranchBtnClickHandler() {
		
		view.setAddBranchBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				InstitutionBean inst = new InstitutionBean();
				inst.setId(institution.getId());
				inst.setCategory(institution.getCategory());
				
				eventBus.addInstitutionBranch(inst);
				eventBus.setInstitutionExistingBranches(branches);
			}
		});
	}
	
	private void initOkBtnClickHandler() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				institution = view.getInstitution();
				
				institution.setUpdateBy(ClientUtil.getUser().getName());
				
				if (isValidated(institution)) {
					
					if (institution.getId() == null) {					
						addInstitution(institution);
					} else {
						updateInstitution(institution);
					}
				} else {
					
					NotificationDlg.error(errorMessages);
				}
			}
		});
	}
	
	private void initCancelBtnClickHandler() {
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	private void initCategoryChangeHandler() {
		
		view.setCategoryChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				institution = view.getInstitution();
				view.setInstitution(institution);
				initTypes();
			}
		});
	}
	
	private void initCitySelectionHandler() {
		
		view.setCitySelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				
				institution = view.getInstitution();
				
				SearchSuggestion suggestion = (SearchSuggestion) event.getSelectedItem();
				
				CityBean city = new CityBean();
				city.setId(ClientUtil.strToLong(suggestion.getValue()));
				city.setName(suggestion.getReplacementString());
				
				institution.setCity(city);
				
				RegionBean region = new RegionBean();
				institution.setRegion(region);
				
				StreetBean street = new StreetBean();
				institution.setStreet(street);
				
				view.setInstitution(institution);
			}
		});
	}
	
	private void initRegionSelectionHandler() {
		
		view.setRegionSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				
				institution = view.getInstitution();
				
				SearchSuggestion suggestion = (SearchSuggestion) event.getSelectedItem();
				
				RegionBean region = new RegionBean();
				region.setId(ClientUtil.strToLong(suggestion.getValue()));
				region.setName(suggestion.getReplacementString());
				
				institution.setRegion(region);
			}
		});
	}
	
	private void initStreetSelectionHandler() {
		
		view.setStreetSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				
				institution = view.getInstitution();
				
				SearchSuggestion suggestion = (SearchSuggestion) event.getSelectedItem();
				
				StreetBean street = new StreetBean();
				street.setId(ClientUtil.strToLong(suggestion.getValue()));
				street.setName(suggestion.getReplacementString());
				
				institution.setStreet(street);
			}
		});
	}
	
	private void initOp24HoursChangeHandler() {
		
		view.setOp24HoursChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				institution = view.getInstitution();
				view.setInstitution(institution);
			}
		});
	}
	
	public void onAddInstService(ServiceBean service) {
		
		HandlerManager handlerMgr = new HandlerManager();
		
		handlerMgr.setUpdateHandler(getServiceUpdateHandler(service));
		handlerMgr.setDeleteHandler(getServiceDeleteHandler(service));
		
		GenericBean<ServiceBean> genService = new GenericBean<ServiceBean>(service, handlerMgr);
		
		services.add(service);
		genServices.add(genService);
		view.setServices(genServices);
	}
	
	public void onUpdateInstService(ServiceBean service) {
		
		view.setServices(genServices);
	}
	
	public void onDeleteInstService(ServiceBean service) {
		
		int index = services.indexOf(service);
		
		services.remove(index);
		genServices.remove(index);
		
		view.setServices(genServices);
	}
	
	public void onAddInstInsurance(InsuranceBean insurance) {
		
		HandlerManager handlerMgr = new HandlerManager();
		
		handlerMgr.setUpdateHandler(getInsuranceUpdateHandler(insurance));
		handlerMgr.setDeleteHandler(getInsuranceDeleteHandler(insurance));
		
		GenericBean<InsuranceBean> genInsurance = new GenericBean<InsuranceBean>(insurance, handlerMgr);
		
		insurances.add(insurance);
		genInsurances.add(genInsurance);
		view.setInsurances(genInsurances);
	}
	
	public void onUpdateInstInsurance(InsuranceBean insurance) {
		
		view.setInsurances(genInsurances);
	}
	
	public void onDeleteInstInsurance(InsuranceBean insurance) {
		
		int index = insurances.indexOf(insurance);
		
		insurances.remove(index);
		genInsurances.remove(index);
		
		view.setInsurances(genInsurances);
	}
	
	public void onAddInstDoctor(DoctorBean doctor) {
		
		HandlerManager handlerMgr = new HandlerManager();
		
		handlerMgr.setUpdateHandler(getDoctorUpdateHandler(doctor));
		handlerMgr.setDeleteHandler(getDoctorDeleteHandler(doctor));
		handlerMgr.setAddHandler(getDoctorScheduleAddHandler(doctor));
		
		GenericBean<DoctorBean> genDoctor = new GenericBean<DoctorBean>(doctor, handlerMgr);
		
		HashMap<ScheduleBean, HandlerManager> scheduleHandleMgrs = new HashMap<ScheduleBean, HandlerManager>();
		doctorScheduleHandlerMgrs.put(doctor, scheduleHandleMgrs);
		
		doctors.add(doctor);
		genDoctors.add(genDoctor);
		
		view.addDoctor(genDoctor);
	}
	
	public void onUpdateInstDoctor(DoctorBean doctor) {
		
		int index = doctors.indexOf(doctor); 
		GenericBean<DoctorBean> genDoctor = genDoctors.get(index);
		
		view.updateDoctor(genDoctor);
	}
	
	public void onDeleteInstDoctor(DoctorBean doctor) {
		
		int index = doctors.indexOf(doctor);
		
		doctors.remove(index);
		GenericBean<DoctorBean> genDoctor = genDoctors.remove(index);
		
		view.deleteDoctor(genDoctor);
	}
	
	public void onAddInstDoctorSchedule(ScheduleBean doctorSchedule) {
		
		int index = doctors.indexOf(doctorSchedule.getDoctor()); 
		GenericBean<DoctorBean> genDoctor = genDoctors.get(index);
		DoctorBean doctor = genDoctor.getBean();
		
		HashMap<ScheduleBean, HandlerManager> scheduleHandlerMgrs = doctorScheduleHandlerMgrs.get(doctor);
		
		ClickHandler schUpdateHandler = getDoctorScheduleUpdateHandler(doctorSchedule);
		ClickHandler schDeleteHandler = getDoctorScheduleDeleteHandler(doctorSchedule);
		
		HandlerManager schHandlerMgr = new HandlerManager();
		schHandlerMgr.setUpdateHandler(schUpdateHandler);
		schHandlerMgr.setDeleteHandler(schDeleteHandler);
		
		scheduleHandlerMgrs.put(doctorSchedule, schHandlerMgr);

		doctor.getSchedules().add(doctorSchedule);
		
		view.updateDoctor(genDoctor);
	}
	
	public void onUpdateInstDoctorSchedule(ScheduleBean doctorSchedule) {
		
		int index = doctors.indexOf(doctorSchedule.getDoctor()); 
		GenericBean<DoctorBean> genDoctor = genDoctors.get(index);
		
		view.updateDoctor(genDoctor);
	}
	
	public void onDeleteInstDoctorSchedule(ScheduleBean doctorSchedule) {
		
		int index = doctors.indexOf(doctorSchedule.getDoctor()); 
		GenericBean<DoctorBean> genDoctor = genDoctors.get(index);
		DoctorBean doctor = genDoctor.getBean();
		
		for (ScheduleBean schedule : doctor.getSchedules()) {
			if (schedule == doctorSchedule) {
				doctor.getSchedules().remove(schedule);
				break;
			}
		}
		
		HashMap<ScheduleBean, HandlerManager> scheduleHandlerMgrs = doctorScheduleHandlerMgrs.get(doctor);
		scheduleHandlerMgrs.remove(doctorSchedule);
		
		view.updateDoctor(genDoctor);
	}
	
	public void onAddInstitution() {
		
		reset();
		
		this.institution = new InstitutionBean();
		
		view.setInstitution(institution);
		view.show();
		view.showLocation();
	}
	
	public void onUpdateInstitution(InstitutionBean institution) {
		
		reset();
		
		this.institution = institution;
		
		initTypes();
		
		view.setInstitution(institution);
		view.show();
		view.showLocation();
	}
	
	private void reset() {
		
		services = null;
		genServices = null;
		insurances = null;
		genInsurances = null;
		doctors = null;
		genDoctors = null;
		galleries = null;
		branches = null;
	}
	
	private boolean isValidated(InstitutionBean institution) {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		if (ClientUtil.isEmpty(institution.getName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_NAME_EMPTY);
		
		}
		if (ClientUtil.isEmpty(institution.getCategory())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_CATEGORY_EMPTY);
		
		} else {
			
			if (!Constant.CATEGORY_PHARMACY.equals(institution.getType()) && ClientUtil.isEmpty(institution.getType())) {
				
				isValidated = false;
				errorMessages.add(Message.ERR_INST_TYPE_EMPTY);
			}
		}
		if (ClientUtil.isEmpty(institution.getCity())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_CITY_EMPTY);
		}
		if (ClientUtil.isEmpty(institution.getPartnerType())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_PARTNER_TYPE_EMPTY);
		}
		
		return isValidated;
	}
	
	private ClickHandler getServiceUpdateHandler(final ServiceBean service) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.updateService(service);
			}
		};
	}
	
	private ClickHandler getServiceDeleteHandler(final ServiceBean service) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ConfirmDlg.confirm("Hapus layanan \"" + service.getName() + "\" ?", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						eventBus.deleteInstService(service);
					}
				});
			}
		};
	}
	
	private ClickHandler getInsuranceUpdateHandler(final InsuranceBean insurance) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.updateInstitutionInsurance(insurance);
			}
		};
	}
	
	private ClickHandler getInsuranceDeleteHandler(final InsuranceBean insurance) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ConfirmDlg.confirm("Hapus layanan \"" + insurance.getName() + "\" ?", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						eventBus.deleteInstInsurance(insurance);
					}
				});
			}
		};
	}
	
	private ClickHandler getDoctorUpdateHandler(final DoctorBean doctor) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.updateInstitutionDoctor(doctor);
				eventBus.setInstitutionExistingDoctors(doctors);
			}
		};
	}
	
	private ClickHandler getDoctorDeleteHandler(final DoctorBean doctor) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ConfirmDlg.confirm("Hapus dokter \"" + doctor.getName() + "\" ?", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						eventBus.deleteInstDoctor(doctor);
					}
				});
			}
		};
	}
	
	private ClickHandler getDoctorScheduleAddHandler(final DoctorBean doctor) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.addInstitutionDoctorSchedule(doctor);
			}
		};
	}
	
	private ClickHandler getDoctorScheduleUpdateHandler(final ScheduleBean schedule) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.updateInstitutionDoctorSchedule(schedule);
			}
		};
	}
	
	private ClickHandler getDoctorScheduleDeleteHandler(final ScheduleBean schedule) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				String day = ClientUtil.dayToStr(schedule.getDay());
				String time = ClientUtil.timeToStr(schedule.getOpStart()) + " - " + ClientUtil.timeToStr(schedule.getOpEnd());
				
				ConfirmDlg.confirm("Hapus jadwal praktek \"" + schedule.getDoctor().getNameWithTitle() + "\"\n" + 
						day +", " + time + "  ?", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						eventBus.deleteInstDoctorSchedule(schedule);
					}
				});
			}
		};
	}
	
	private ClickHandler getBranchDeleteHandler(final BranchBean branch) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ConfirmDlg.confirm("Hapus cabang \"" + branch.getInstitution().getName() + "\" ?", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						eventBus.deleteInstBranch(branch);
					}
				});
			}
		};
	}
	
	private ClickHandler getGalleryUpdateHandler(final GalleryBean gallery) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.updateInstitutionGallery(gallery);
			}
		};
	}
	
	private ClickHandler getGalleryDeleteHandler(final GalleryBean gallery) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ConfirmDlg.confirm("Hapus gallery \"" + gallery.getDescription() + "\" ?", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						eventBus.deleteInstGallery(gallery);
					}
				});
			}
		};
	}
	
	private void addInstitution(InstitutionBean inst) {
		
		ProgressDlg.show();
		
		inst.setSchedules(getSchedules());
		inst.setGalleries(galleries);
		
		institutionService.addInstitution(inst, new AsyncCallback<InstitutionBean>() {
			
			@Override
			public void onSuccess(InstitutionBean result) {
				institution = result;
				updateBranches(institution, branches);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
	
	private void updateInstitution(InstitutionBean inst) {
		
		ProgressDlg.show();
		
		inst.setSchedules(getSchedules());
		inst.setGalleries(galleries);
		
		institutionService.updateInstitution(inst, new AsyncCallback<InstitutionBean>() {
			
			@Override
			public void onSuccess(InstitutionBean result) {
				institution = result;
				updateBranches(institution, branches);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
	
	private void updateBranches(InstitutionBean institution, List<BranchBean> branches) {
		
		if (branches == null) {
			
			ProgressDlg.hide();
			view.hide();
			return;
		}
		
		for (BranchBean branch : branches) {
			branch.setUpdateBy(ClientUtil.getUser().getName());
		}
		
		branchService.updateBranches(institution, branches, new AsyncCallback<List<BranchBean>>() {
			
			@Override
			public void onSuccess(List<BranchBean> result) {
				
				ProgressDlg.success();
				view.hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure();
			}
		});
	}
	
	private List<ScheduleBean> getSchedules() {
		
		List<ScheduleBean> schedules = null;
		
		if (doctors != null) {
			
			schedules = new ArrayList<ScheduleBean>();
			
			for (DoctorBean doctor : doctors) {
				schedules.addAll(doctor.getSchedules());
			}
			
			for (ScheduleBean schedule : schedules) {
				schedule.setUpdateBy(ClientUtil.getUser().getName());
			}
		}
		
		return schedules;
	}
	
	public void onAddInstBranch(BranchBean branch) {
		
		HandlerManager handlerMgr = new HandlerManager();
		
		handlerMgr.setDeleteHandler(getBranchDeleteHandler(branch));
		
		GenericBean<BranchBean> genBranch = new GenericBean<BranchBean>(branch, handlerMgr);
		
		branches.add(branch);
		genBranches.add(genBranch);
		
		view.addBranch(genBranch);
	}
	
	public void onDeleteInstBranch(BranchBean branch) {
		
		int index = branches.indexOf(branch);
		
		branches.remove(index);
		GenericBean<BranchBean> genBranch = genBranches.remove(index);
		
		view.deleteBranch(genBranch);
	}
	
	public void onAddInstGallery(GalleryBean gallery) {
		
		HandlerManager handlerMgr = new HandlerManager();
		handlerMgr.setUpdateHandler(getGalleryUpdateHandler(gallery));
		handlerMgr.setDeleteHandler(getGalleryDeleteHandler(gallery));
		
		GenericBean<GalleryBean> genGallery = new GenericBean<GalleryBean>(gallery, handlerMgr);
		
		if (gallery.isMain()) {
			
			institution.setProfileId(gallery.getImageId());
			
			for (GalleryBean g : galleries) {
				g.setMain(false);
			}
		}
		
		galleries.add(gallery);
		genGalleries.add(genGallery);
		
		view.addGallery(genGallery);
	}
	
	public void onUpdateInstGallery(GalleryBean gallery) {
		
		if (gallery.isMain()) {
			
			institution.setProfileId(gallery.getImageId());
			
			for (GalleryBean g : galleries) {
				g.setMain(false);
			}
		}
		
		int index = galleries.indexOf(gallery); 
		GenericBean<GalleryBean> genGallery = genGalleries.get(index);
		
		view.updateGallery(genGallery);
	}
	
	public void onDeleteInstGallery(GalleryBean gallery) {
		
		int index = galleries.indexOf(gallery);
		
		galleries.remove(index);
		GenericBean<GalleryBean> genGallery = genGalleries.remove(index);
		
		view.deleteGallery(genGallery);
	}
}
