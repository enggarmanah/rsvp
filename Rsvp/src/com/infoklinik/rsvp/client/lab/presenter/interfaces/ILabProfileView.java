package com.infoklinik.rsvp.client.lab.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.view.LazyView;

public interface ILabProfileView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void showLocation();
	
	void showService();
	
	void showGallery();	
	
	void showBranch();	
	
	void setInstitution(InstitutionBean institution);
	
	void setServices(List<ServiceBean> services);
	
	void setPromotions(List<ServiceBean> services);
	
	void setInsurances(List<InsuranceBean> insurances);
	
	void setGalleries(List<GalleryBean> galleries);
	
	void setBranches(List<BranchBean> branches);
	
	void setLocationMenuClickHandler(ClickHandler handler);

	void setServiceMenuClickHandler(ClickHandler handler);
	
	void setGalleryMenuClickHandler(ClickHandler handler);
	
	void setBranchMenuClickHandler(ClickHandler handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
}
