package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.MasterCodeBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminInstitutionView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void showLocation();
	
	void showService();
	
	void showDoctor();
	
	void showGallery();	
	
	void showBranch();
	
	InstitutionBean getInstitution();
	
	void setInstitution(InstitutionBean institution);
	
	void setCategories(List<MasterCodeBean> masterCodeBeans);
	
	void setTypes(List<MasterCodeBean> masterCodeBeans);
	
	void setPartnerTypes(List<MasterCodeBean> masterCodeBeans);
	
	void setServices(List<GenericBean<ServiceBean>> services);
	
	void setInsurances(List<GenericBean<InsuranceBean>> insurances);
	
	void setDoctors(List<GenericBean<DoctorBean>> doctors);
	
	void setDoctorScheduleHandlerMgrs(HashMap<DoctorBean, HashMap<ScheduleBean, HandlerManager>> doctorScheduleHandlerMgrs);
	
	void addDoctor(GenericBean<DoctorBean> doctor);
	
	void updateDoctor(GenericBean<DoctorBean> doctor);
	
	void deleteDoctor(GenericBean<DoctorBean> doctor);
	
	void setGalleries(List<GalleryBean> galleries);
	
	void setBranches(List<GenericBean<BranchBean>> branches);
	
	void addBranch(GenericBean<BranchBean> branch);
	
	void deleteBranch(GenericBean<BranchBean> branch);
	
	void setLocationMenuClickHandler(ClickHandler handler);

	void setServiceMenuClickHandler(ClickHandler handler);
	
	void setDoctorMenuClickHandler(ClickHandler handler);
	
	void setGalleryMenuClickHandler(ClickHandler handler);
	
	void setBranchMenuClickHandler(ClickHandler handler);
	
	void setAddServiceBtnClickHandler(ClickHandler handler);
	
	void setAddInsuranceBtnClickHandler(ClickHandler handler);
	
	void setAddDoctorBtnClickHandler(ClickHandler handler);
	
	void setAddBranchBtnClickHandler(ClickHandler handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
	
	void setCategoryChangeHandler(ChangeHandler handler);
	
	void setOp24HoursChangeHandler(ChangeHandler handler);
	
	void setCitySelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler);
	
	void setRegionSelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler);
	
	void setStreetSelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler);
}
