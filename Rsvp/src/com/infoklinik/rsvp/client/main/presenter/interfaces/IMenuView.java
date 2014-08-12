package com.infoklinik.rsvp.client.main.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IMenuView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void setClinicSelected();
	
	void setHospitalSelected();
	
	void setDoctorSelected();
	
	void setServiceSelected();
	
	void setLabSelected();
	
	void setPharmacySelected();
	
	void setClinicLbHandler(ClickHandler handler);
	
	void setHospitalLbHandler(ClickHandler handler);
	
	void setDoctorLbHandler(ClickHandler handler);
	
	void setServiceLbHandler(ClickHandler handler);
	
	void setLabLbHandler(ClickHandler handler);
	
	void setPharmacyLbHandler(ClickHandler handler);
}
