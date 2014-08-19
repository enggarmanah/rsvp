package com.infoklinik.rsvp.client.admin.view;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.BranchBean;

public class InstitutionBranchView extends BaseView {
	
	@UiField
	FlowPanel branchesPanel;
	
	@UiField
	FocusPanel addBranchBtn;
	
	interface ModuleUiBinder extends UiBinder<Widget, InstitutionBranchView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	HashMap<GenericBean<BranchBean>, InstitutionBranchItemView> branchViewMap = new HashMap<GenericBean<BranchBean>, InstitutionBranchItemView>();
	
	public InstitutionBranchView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
		
	public void setBranches(List<GenericBean<BranchBean>> branches) {
		
		branchesPanel.clear();
		
		for (GenericBean<BranchBean> branch : branches) {
			
			InstitutionBranchItemView branchItemView = new InstitutionBranchItemView();
			branchItemView.setInstitution(branch.getBean().getInstitution());
			
			branchesPanel.add(branchItemView);
			branchViewMap.put(branch, branchItemView);
		}
	}
	
	public void addBranch(GenericBean<BranchBean> branch) {
		
		InstitutionBranchItemView branchItemView = new InstitutionBranchItemView();
		branchItemView.setInstitution(branch.getBean().getInstitution());
		
		branchesPanel.add(branchItemView);
		branchViewMap.put(branch, branchItemView);
	}
	
	public void updateBranch(GenericBean<BranchBean> branch) {
		
		InstitutionBranchItemView branchItemView = branchViewMap.get(branch);
		branchItemView.setInstitution(branch.getBean().getInstitution());
	}
	
	public void deleteBranch(GenericBean<BranchBean> branch) {
		
		InstitutionBranchItemView branchItemView = branchViewMap.get(branch);
		branchesPanel.remove(branchItemView);
	}
	
	public void setAddBranchBtnClickHandler(ClickHandler handler) {
		
		addBranchBtn.addClickHandler(handler);
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
