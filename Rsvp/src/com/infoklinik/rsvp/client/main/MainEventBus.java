package com.infoklinik.rsvp.client.main;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.module.AfterLoadChildModule;
import com.mvp4g.client.annotation.module.BeforeLoadChildModule;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.annotation.module.DisplayChildModuleView;
import com.mvp4g.client.annotation.module.LoadChildModuleError;
import com.mvp4g.client.event.EventBusWithLookup;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.admin.AdminModule;
import com.infoklinik.rsvp.client.admin.AdminStatisticModule;
import com.infoklinik.rsvp.client.doctor.DoctorModule;
import com.infoklinik.rsvp.client.inst.InstitutionModule;
import com.infoklinik.rsvp.client.listing.SearchResultModule;
import com.infoklinik.rsvp.client.main.presenter.MainPresenter;
import com.infoklinik.rsvp.client.partner.PartnerModule;
import com.infoklinik.rsvp.client.promo.PromoModule;
import com.infoklinik.rsvp.client.search.SearchModule;
import com.infoklinik.rsvp.client.service.ServiceModule;
import com.infoklinik.rsvp.client.social.SocialModule;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.SocialUser;

@Events(startPresenter = MainPresenter.class)
@ChildModules({
	@ChildModule(moduleClass = MenuModule.class),
	@ChildModule(moduleClass = InstitutionModule.class),
	@ChildModule(moduleClass = DoctorModule.class), 
	@ChildModule(moduleClass = ServiceModule.class), 
	@ChildModule(moduleClass = AdminModule.class),
	@ChildModule(moduleClass = AdminStatisticModule.class),
	@ChildModule(moduleClass = PartnerModule.class),
	@ChildModule(moduleClass = PromoModule.class),
	@ChildModule(moduleClass = SearchModule.class),
	@ChildModule(moduleClass = SearchResultModule.class),
	@ChildModule(moduleClass = SocialModule.class)})
@Debug(logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface MainEventBus extends EventBusWithLookup {
	
	@LoadChildModuleError
	@Event(handlers = MainPresenter.class)
	void errorOnLoad(Throwable reason);

	@BeforeLoadChildModule
	@Event(handlers = MainPresenter.class)
	void beforeLoad();

	@AfterLoadChildModule
	@Event(handlers = MainPresenter.class)
	void afterLoad();
	
	@DisplayChildModuleView(
			{MenuModule.class})
	@Event(handlers = MainPresenter.class)
	public void setMenuPanel(IsWidget widget);
	
	@Event(handlers = MainPresenter.class)
	public void setSearchPanel(IsWidget widget);
	
	@DisplayChildModuleView(
			{AdminModule.class,
			 SearchModule.class,
			 InstitutionModule.class,
			 DoctorModule.class,
			 ServiceModule.class,
			 SocialModule.class})
	@Event(handlers = MainPresenter.class)
	public void loadChildModuleView(IsWidget widget);
	
	@DisplayChildModuleView(
			{AdminStatisticModule.class,
			 PartnerModule.class,
			 SearchResultModule.class})
	@Event(handlers = MainPresenter.class)
	public void setLeftPanel(IsWidget widget);
	
	@DisplayChildModuleView(
			{PromoModule.class})
	@Event(handlers = MainPresenter.class)
	public void setRightPanel(IsWidget widget);
	
	@Start
	@Event(handlers = MainPresenter.class)
	public void start();
	
	@Event(handlers = MainPresenter.class)
	public void verifySocialUserToExecuteTask(String task);
	
	@Event(forwardToModules=MenuModule.class) 
	public void loadMenu();
	
	@Event(forwardToModules=SearchModule.class) 
	public void loadClinicSearch();
	
	@Event(forwardToModules=SearchModule.class) 
	public void removeClinicSearch();
	
	@Event(forwardToModules=SearchModule.class) 
	public void loadHospitalSearch();
	
	@Event(forwardToModules=SearchModule.class) 
	public void removeHospitalSearch();
	
	@Event(forwardToModules=SearchModule.class) 
	public void loadLabSearch();
	
	@Event(forwardToModules=SearchModule.class) 
	public void removeLabSearch();
	
	@Event(forwardToModules=SearchModule.class) 
	public void loadDoctorSearch();
	
	@Event(forwardToModules=SearchModule.class) 
	public void removeDoctorSearch();
	
	@Event(forwardToModules=SearchModule.class) 
	public void loadServiceSearch();
	
	@Event(forwardToModules=SearchModule.class) 
	public void removeServiceSearch();
	
	@Event(forwardToModules=SearchModule.class)
	public void loadSearchLocations(List<InstitutionBean> institutions);
	
	@Event(forwardToModules=AdminModule.class)
	public void addInstitution();
	
	@Event(forwardToModules=AdminModule.class)
	public void updateInstitution(InstitutionBean institution);
	
	@Event(forwardToModules=AdminModule.class)
	public void addDoctor();
	
	@Event(forwardToModules=AdminModule.class)
	public void updateDoctor(DoctorBean doctor);
	
	@Event(forwardToModules=InstitutionModule.class) 
	public void loadInstitutionProfile(InstitutionBean institution);
	
	@Event(forwardToModules=DoctorModule.class) 
	public void loadDoctorProfile(DoctorBean doctor);
	
	@Event(forwardToModules=ServiceModule.class) 
	public void loadServiceInfo(ServiceBean service);
	
	@Event(forwardToModules=AdminModule.class) 
	public void loadServiceReference();
	
	@Event(forwardToModules=SocialModule.class) 
	public void loadInstitutionComment(InstitutionBean institution);
	
	@Event(forwardToModules=SocialModule.class) 
	public void loadDoctorComment(DoctorBean doctor);
	
	@Event(forwardToModules=SocialModule.class) 
	public void loadServiceComment(ServiceBean service);
	
	@Event(forwardToModules=SocialModule.class) 
	public void loadInstitutionLike(InstitutionBean institution);
	
	@Event(forwardToModules=SocialModule.class) 
	public void loadDoctorLike(DoctorBean doctor);
	
	@Event(forwardToModules=SocialModule.class) 
	public void loadServiceLike(ServiceBean service);
	
	@Event(forwardToModules=SocialModule.class) 
	public void addInstSocialUserComment(SocialUser socialUser);
	
	@Event(forwardToModules=SocialModule.class) 
	public void addDoctorSocialUserComment(SocialUser socialUser);
	
	@Event(forwardToModules=SocialModule.class) 
	public void addServiceSocialUserComment(SocialUser socialUser);
	
	@Event(forwardToModules=SocialModule.class) 
	public void addInstitutionSocialUserLike(SocialUser socialUser);
	
	@Event(forwardToModules=SocialModule.class) 
	public void addDoctorSocialUserLike(SocialUser socialUser);
	
	@Event(forwardToModules=SocialModule.class) 
	public void addServiceSocialUserLike(SocialUser socialUser);
	
	@Event(forwardToModules=AdminModule.class) 
	public void loadAdmin();

	@Event(forwardToModules=PartnerModule.class)
	public void loadPartner();
	
	@Event(forwardToModules=AdminStatisticModule.class) 
	public void loadStatistic();

	@Event(forwardToModules=PromoModule.class) 
	public void loadPromotion();
	
	@Event(forwardToModules=SearchResultModule.class)
	public void loadInstitutionSearchResult(List<InstitutionBean> institutions);
	
	@Event(forwardToModules=SearchResultModule.class)
	public void loadDoctorSearchResult(List<DoctorBean> doctors);
	
	@Event(forwardToModules=SearchResultModule.class)
	public void loadServiceSearchResult(List<ServiceBean> services);
}
