package com.infoklinik.rsvp.client.admin.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class InstitutionBranchView extends BaseView {
	
	@UiField
	FlowPanel branchesPanel;
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionBranchView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	InstitutionBean institutionBean;
	
	public InstitutionBranchView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setBranches(List<BranchBean> branches) {
		
		branchesPanel.clear();
		
		for (BranchBean branch : branches) {
			
			InstitutionBranchItemView institutionView = new InstitutionBranchItemView();
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
