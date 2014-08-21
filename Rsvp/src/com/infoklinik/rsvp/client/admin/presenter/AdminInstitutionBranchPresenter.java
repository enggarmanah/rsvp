package com.infoklinik.rsvp.client.admin.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.client.admin.AdminEventBus;
import com.infoklinik.rsvp.client.admin.presenter.interfaces.IAdminInstitutionBranchView;
import com.infoklinik.rsvp.client.admin.view.AdminInstitutionBranchView;
import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.BranchServiceAsync;
import com.infoklinik.rsvp.client.rpc.InstitutionServiceAsync;
import com.infoklinik.rsvp.shared.BranchBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.SearchSuggestion;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = AdminInstitutionBranchView.class)
public class AdminInstitutionBranchPresenter extends LazyPresenter<IAdminInstitutionBranchView, AdminEventBus> {
	
	@Inject
	InstitutionServiceAsync institutionService;
	
	@Inject
	BranchServiceAsync branchService;
	
	BranchBean branch;
	boolean hasGroupId;
	Long mainInstId;
	
	List<BranchBean> existingBranches;
	List<String> errorMessages;
	
	@Override
	public void bindView() {
		
		initBranchSelectionHandler();
		initOkBtnClickHandler();
		initCancelBtnClickHandler();
	}
	
	public void onAddInstitutionBranch(InstitutionBean institution) {
		
		hasGroupId = false;
		mainInstId = institution.getId();
		
		branch = new BranchBean();
		branch.setInstitution(institution);
		
		view.setBranch(branch);
		view.show();
	}
	
	public void onSetInstitutionExistingBranches(List<BranchBean> branches) {
		
		existingBranches = branches;
	}
	
	private void initBranchSelectionHandler() {
		
		view.setBranchSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				
				ProgressDlg.show();
				
				branch = view.getBranch();
				
				SearchSuggestion suggestion = (SearchSuggestion) event.getSelectedItem();
				
				InstitutionBean institution = branch.getInstitution();
				institution.setId(Long.valueOf(suggestion.getValue()));
				institution.setName(suggestion.getReplacementString());
				
				branchService.getGroupId(branch.getInstitution().getId(), new AsyncCallback<Long>() {
					
					@Override
					public void onSuccess(Long result) {
						
						branch.setGroupId(result);
						ProgressDlg.hidePrompt();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						
						ProgressDlg.failure();
					}
				});
			}
		});
	}
	
	private void initOkBtnClickHandler() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				branch = view.getBranch();
				
				if (isValidated()) {
					
					ProgressDlg.show();
					
					institutionService.getInstitution(branch.getInstitution().getId(), new AsyncCallback<InstitutionBean>() {
						
						@Override
						public void onSuccess(InstitutionBean result) {
							
							InstitutionBean institution = result;
							
							branch.setInstitution(institution);
							eventBus.addInstBranch(branch);
							
							if (hasGroupId) {
								
								view.hide();
								ProgressDlg.hide();
								
							} else {
								
								branchService.getBranches(institution.getId(), new AsyncCallback<List<BranchBean>>() {
									
									@Override
									public void onSuccess(List<BranchBean> result) {
										
										List<BranchBean> branches = result;
										for (BranchBean branch : branches) {
											eventBus.addInstBranch(branch);											
										}
										
										view.hide();
										ProgressDlg.hide();
									}
									
									@Override
									public void onFailure(Throwable caught) {
										ProgressDlg.failure();
									}
								});
							} 
						}
						
						@Override
						public void onFailure(Throwable caught) {
							
							ProgressDlg.failure();
						}
					});
					
				} else {
					NotificationDlg.error(errorMessages);
				}
			}
		});
		
	}
	
	private void initCancelBtnClickHandler() {
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	private boolean isValidated() {
		
		boolean isValidated = true;
		errorMessages = new ArrayList<String>();
		
		InstitutionBean inst = view.getBranch().getInstitution();
		
		if (ClientUtil.isEmpty(inst.getId())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_EMPTY);
		
		} else if (mainInstId == inst.getId()) { 
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_BRANCH_CIRCULAR);
			
		} else if (!ClientUtil.isEmpty(inst.getName()) &&
				!inst.getName().equals(branch.getInstitution().getName())) {
			
			isValidated = false;
			errorMessages.add(Message.ERR_INST_EMPTY);
			
		} else {
			
			for (BranchBean existingBranch : existingBranches) {
				
				if (existingBranch.getInstitution().getId().equals(branch.getInstitution().getId())) {
					
					isValidated = false;
					errorMessages.add(Message.ERR_INST_EXIST);
					break;
				
				} else if (existingBranch.getGroupId() != null && branch.getGroupId() != null &&
					!existingBranch.getGroupId().equals(branch.getGroupId())) {
					
					isValidated = false;
					errorMessages.add(Message.ERR_INST_BRANCH_GRP_DIFF);
					break;
				}
				
				if (!hasGroupId && existingBranch.getGroupId() != null) {
					hasGroupId = true;
				}
			}
		}
		
		return isValidated;
	}
}
