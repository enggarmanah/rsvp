package com.infoklinik.rsvp.client.main.presenter.interfaces;

import com.google.gwt.user.client.ui.Widget;

public interface IMainView {

	void setSearchPanel(Widget searchPanel);
	
	void setMenuPanel(Widget menuPanel);
	
	void setLeftPanel(Widget leftPanel);
	
	void setRightPanel(Widget rightPanel);
	
	Widget getViewWidget();
}
