package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.SuggestionOracle;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionBranchView;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceTypeBean;
import com.infoklinik.rsvp.shared.SuggestParameter;

public class AdminInstitutionBranchView extends BaseView implements IAdminInstitutionBranchView {
	
	interface ModuleUiBinder extends UiBinder<Widget, AdminInstitutionBranchView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	SuggestParameter suggestParameter = new SuggestParameter(SuggestionOracle.SEARCH_INSTITUTION);
	
	@UiField(provided = true)
	SuggestBox branchSb = new SuggestBox(new SuggestionOracle(suggestParameter));
	
	@UiField
	Button okBtn;
	
	@UiField
	Button cancelBtn;
	
	DialogBox dialogBox;
	
	List<ServiceTypeBean> list;
	
	BranchBean branch;
	
	public void createView() {	
		
		dialogBox = new DialogBox();
		dialogBox.setStyleName("formDialog");
		dialogBox.setWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return dialogBox;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
	
	public void show() {
		
		dlgFadeOut();
		
		dialogBox.setText("Tambah Dokter Baru");
		dialogBox.center();
		dialogBox.setPopupPosition(dialogBox.getPopupLeft() + Constant.POPUP_L2_LEFT, Constant.POPUP_L2_TOP);
		dialogBox.show();
		
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

	public BranchBean getBranch() {
		
		return branch;
	}
	
	public void setBranch(BranchBean branch) {
		
		this.branch = branch;
		InstitutionBean institution = branch.getInstitution();
		
		suggestParameter.setCategory(institution.getCategory());
		
		branchSb.setText(Constant.EMPTY_STRING);
		
		if (!ClientUtil.isEmpty(institution.getName())) {
			branchSb.setText(institution.getName() + ", " + institution.getAddress());
		}
	}
	
	public void setBranchSelectionHandler(SelectionHandler<SuggestOracle.Suggestion> handler) {
		
		branchSb.addSelectionHandler(handler);
	}
	
	public void setOkBtnClickHandler(ClickHandler handler) {
		
		okBtn.addClickHandler(handler);
	}
	
	public void setCancelBtnClickHandler(ClickHandler handler) {
		
		cancelBtn.addClickHandler(handler);
	}
}
