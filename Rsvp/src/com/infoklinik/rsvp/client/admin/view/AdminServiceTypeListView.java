package com.infoklinik.rsvp.client.admin.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminServiceTypeListView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.infoklinik.rsvp.shared.ServiceTypeSearchBean;

public class AdminServiceTypeListView extends BaseView implements IAdminServiceTypeListView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminServiceTypeListView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	TextBox nameTb;
	
	@UiField 
	FlexTable searchResultFt;
	
	@UiField
	FlowPanel pagePanel;
	
	@UiField
	Button searchBtn;
	
	@UiField
	Button addBtn;
	
	@UiField
	Button okBtn;
	
	DialogBox dialogBox;
	
	List<GenericBean<ServiceTypeBean>> list;
	
	ServiceTypeSearchBean serviceTypeSearchBean = new ServiceTypeSearchBean();
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return searchResultFt;
	}
	
	public void show() {
		
		dlgFadeOut();
		
		dialogBox.setText("Referensi Layanan");
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft(), 70);
		dialogBox.show();
		
		this.list = new ArrayList<GenericBean<ServiceTypeBean>>();
		this.size = list.size();
		
		clear();
		
		loadHeader();
		loadEmptyContent(searchResultFt);
		loadNavigation();
		
		dlgFadeIn();
	}
	
	public void hide() {
		
		dlgFadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				dialogBox.hide();
			}
		};
		
		timer.schedule(Constant.FADE_TIME);
	}

	public void setList(List<GenericBean<ServiceTypeBean>> list) {
		
		this.list = list;
		this.size = list.size();
		
		loadNavigation();
		
		setPage(1);
	}
	
	public void refresh() {
		
		setPage(page);
	}
	
	@Override
	protected void clear() {
		
		searchResultFt.removeAllRows();
	}
	
	@Override
	protected FlowPanel getPagePanel() {
		
		return pagePanel;
	}
	
	@Override
	protected void loadHeader() {
		
		Label noLb = new Label(LABEL_NO);
		noLb.setWidth("20px");
		Label nameLb = new Label(LABEL_NAME);
		nameLb.setWidth("205px");
		Label categoryLb = new Label(LABEL_CATEGORY);
		categoryLb.setWidth("117px");
		Label menuLb = new Label(LABEL_MENU);
		menuLb.setWidth("40px");
		
		searchResultFt.setWidget(0, 0, noLb);
		searchResultFt.setWidget(0, 1, nameLb);
		searchResultFt.setWidget(0, 2, categoryLb);
		searchResultFt.setWidget(0, 3, menuLb);
		
		setStyleName(searchResultFt);
	}
	
	@Override
	public void loadContent() {
		
		int fromIndex = getFromIndex();
		int toIndex = getToIndex();
		
		List<GenericBean<ServiceTypeBean>> sublist = list.subList(fromIndex, toIndex);
		
		int i = fromIndex + 1;
		
		for (GenericBean<ServiceTypeBean> genericBean : sublist) {
			
			ServiceTypeBean serviceTypeBean = genericBean.getBean();
			HandlerManager handlerMgr = genericBean.getHandlerMgr();
			
			Label noLb = new Label(i + ".");
			Label nameLb = new Label(serviceTypeBean.getName());
			Label categoryLb = new Label(serviceTypeBean.getCategory());
			
			Label updateLbl = createUpdateLabel();
			updateLbl.addClickHandler(handlerMgr.getUpdateHandler());
			
			Label deleteLbl = createDeleteLabel();
			deleteLbl.addClickHandler(handlerMgr.getDeleteHandler());
			
			FlowPanel panel = createMenuPanel();
			panel.add(updateLbl);
			panel.add(deleteLbl);
			
			int index = searchResultFt.getRowCount();
			
			searchResultFt.setWidget(index, 0, noLb);
			searchResultFt.setWidget(index, 1, nameLb);
			searchResultFt.setWidget(index, 2, categoryLb);
			searchResultFt.setWidget(index, 3, panel);
			
			setStyleName(searchResultFt);
			i++;
		}
		
		loadEmptyResult(searchResultFt);
	}
	
	public ServiceTypeSearchBean getServiceTypeSearchBean() {
		
		serviceTypeSearchBean.setName(nameTb.getText());
		return serviceTypeSearchBean;
	}
	
	public void remove(GenericBean<ServiceTypeBean> genericBean) {
		
		int curPage = this.page;
		
		list.remove(genericBean);
		setList(list);
		
		setPage(curPage);
	}
	
	public void setSearchBtnClickHandler(ClickHandler handler) {
		
		searchBtn.addClickHandler(handler);
	}
	
	public void setAddBtnClickHandler(ClickHandler handler) {
		
		addBtn.addClickHandler(handler);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
}
