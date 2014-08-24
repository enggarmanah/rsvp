package com.infoklinik.rsvp.client.admin;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.admin.presenter.AdminInstitutionBranchPresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminInstitutionDoctorPresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminInstitutionDoctorSchedulePresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminInstitutionGalleryPresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminInstitutionInsurancePresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminInstitutionLocationPresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminInstitutionPresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminPresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminServicePresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminServiceTypeListPresenter;
import com.infoklinik.rsvp.client.admin.presenter.AdminServiceTypePresenter;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = AdminServiceTypeListPresenter.class, module = AdminModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface AdminEventBus extends EventBus {
	
	@Event(forwardToParent = true)
	public void setRightPanel(Widget widget);
	
	@Event(handlers = AdminServiceTypeListPresenter.class)
	public void loadServiceReference();
	
	@Event(handlers = AdminServiceTypeListPresenter.class)
	public void reloadServiceReference();
	
	@Event(handlers = AdminServiceTypePresenter.class)
	public void addServiceReference();
	
	@Event(handlers = AdminServiceTypePresenter.class)
	public void updateServiceReference(ServiceTypeBean serviceTypeBean);
	
	@Event(handlers = AdminPresenter.class)
	public void loadAdmin();
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void addInstitution();
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void updateInstitution(InstitutionBean institution);
	
	@Event(handlers = AdminServicePresenter.class)
	public void addService();
	
	@Event(handlers = AdminServicePresenter.class)
	public void updateService(ServiceBean service);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void addInstService(ServiceBean service);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void updateInstService(ServiceBean service);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void deleteInstService(ServiceBean service);
	
	@Event(handlers = AdminInstitutionInsurancePresenter.class)
	public void addInstitutionInsurance();
	
	@Event(handlers = AdminInstitutionInsurancePresenter.class)
	public void updateInstitutionInsurance(InsuranceBean insurance);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void addInstInsurance(InsuranceBean insurance);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void updateInstInsurance(InsuranceBean insurance);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void deleteInstInsurance(InsuranceBean service);
	
	@Event(handlers = AdminInstitutionDoctorPresenter.class)
	public void addInstitutionDoctor();
	
	@Event(handlers = AdminInstitutionDoctorPresenter.class)
	public void updateInstitutionDoctor(DoctorBean doctor);
	
	@Event(handlers = AdminInstitutionDoctorPresenter.class)
	public void setInstitutionExistingDoctors(List<DoctorBean> doctors);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void addInstDoctor(DoctorBean doctor);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void updateInstDoctor(DoctorBean doctor);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void deleteInstDoctor(DoctorBean doctor);
	
	@Event(handlers = AdminInstitutionDoctorSchedulePresenter.class)
	public void addInstitutionDoctorSchedule(DoctorBean doctor);
	
	@Event(handlers = AdminInstitutionDoctorSchedulePresenter.class)
	public void updateInstitutionDoctorSchedule(ScheduleBean schedule);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void addInstDoctorSchedule(ScheduleBean schedule);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void updateInstDoctorSchedule(ScheduleBean schedule);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void deleteInstDoctorSchedule(ScheduleBean schedule);
	
	@Event(handlers = AdminInstitutionBranchPresenter.class)
	public void addInstitutionBranch(InstitutionBean institution);
	
	@Event(handlers = AdminInstitutionBranchPresenter.class)
	public void setInstitutionExistingBranches(List<BranchBean> branches);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void addInstBranch(BranchBean gallery);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void deleteInstBranch(BranchBean gallery);
	
	@Event(handlers = AdminInstitutionGalleryPresenter.class)
	public void addInstitutionGallery(GalleryBean gallery);
	
	@Event(handlers = AdminInstitutionGalleryPresenter.class)
	public void updateInstitutionGallery(GalleryBean gallery);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void addInstGallery(GalleryBean gallery);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void updateInstGallery(GalleryBean gallery);
	
	@Event(handlers = AdminInstitutionPresenter.class)
	public void deleteInstGallery(GalleryBean gallery);
	
	@Event(handlers = AdminInstitutionLocationPresenter.class)
	public void getInstitutionLocation(InstitutionBean institution);
}
