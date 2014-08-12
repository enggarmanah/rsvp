package com.infoklinik.rsvp.client.admin.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.mvp4g.client.view.LazyView;

public interface IAdminInstitutionDoctorScheduleView  extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	ScheduleBean getSchedule();
	
	void setSchedule(ScheduleBean schedule);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
