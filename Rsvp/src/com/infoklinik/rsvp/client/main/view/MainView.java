package com.infoklinik.rsvp.client.main.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.main.presenter.interfaces.IMainView;

public class MainView extends Composite implements IMainView {
	
	@UiField
	SimplePanel menuPanel;
	
	@UiField
	SimplePanel searchPanel;
	
	@UiField
	SimplePanel leftPanel;
	
	@UiField
	SimplePanel rightPanel;
	
	interface MainViewUiBinder extends UiBinder<Widget, MainView> {}
	
	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);
	
	public MainView() {	
		
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	private void init() {
	}
		
	public void setMenuPanel(Widget menuPanel) {
		
		this.menuPanel.setWidget(menuPanel);
	}
	
	public void setSearchPanel(Widget searchPanel) {
		
		this.searchPanel.setWidget(searchPanel);
	}
	
	public void setLeftPanel(Widget leftPanel) {
		
		this.leftPanel.setWidget(leftPanel);
	}
	
	public void setRightPanel(Widget rightPanel) {
		
		this.rightPanel.setWidget(rightPanel);
	}

	public Widget getViewWidget() {
		
		return this;
	}
}
