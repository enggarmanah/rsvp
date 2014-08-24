package com.infoklinik.rsvp.client.listing.presenter.interfaces;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.view.LazyView;

public interface ISearchResultView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void setInstitutions(List<GenericBean<InstitutionBean>> institutions);	
	
	void setDoctors(List<GenericBean<DoctorBean>> doctors);
	
	void setServices(List<GenericBean<ServiceBean>> services);
	
	void setDoctorInstHandlerMgrs(HashMap<Long, HashMap<Long, HandlerManager>> doctorInstHandlerMgrs);
	
	void setDoctorInstScheduleHandlerMgrs(HashMap<Long, HashMap<Long, HashMap<Long, HandlerManager>>> doctorInstScheduleHandlerMgrs);
	
	void setSearchLocationHandler(ClickHandler handler);
}
