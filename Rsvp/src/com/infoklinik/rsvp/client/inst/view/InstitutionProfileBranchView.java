package com.infoklinik.rsvp.client.inst.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class InstitutionProfileBranchView extends BaseView {
	
	@UiField
	FlowPanel branchesPanel;
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionProfileBranchView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institutionBean;
	
	public InstitutionProfileBranchView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setBranches(List<BranchBean> branches) {
		
		branchesPanel.clear();
		
		for (BranchBean branch : branches) {
			
			BranchView institutionView = new BranchView();
			institutionView.setInstitutionBean(branch.getInstitution());
			
			branchesPanel.add(institutionView);
		}
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
