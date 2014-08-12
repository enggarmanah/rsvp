package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminInstitutionInsuranceView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setInsurances(List<InsuranceBean> list);
	
	InsuranceBean getInsurance();
	
	void setInsurance(InsuranceBean insurance);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
