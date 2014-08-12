package com.infoklinik.rsvp.client.social.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CommentServiceAsync;
import com.infoklinik.rsvp.client.social.SocialEventBus;
import com.infoklinik.rsvp.client.social.presenter.interfaces.ICommentAddView;
import com.infoklinik.rsvp.client.social.view.CommentAddView;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.SocialUser;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = CommentAddView.class)
public class CommentAddPresenter extends LazyPresenter<ICommentAddView, SocialEventBus> {
	
	@Inject
	CommentServiceAsync commentService;
	
	private static String ADD_COMMENT_INSTITUTION = "ADD_COMMENT_INSTITUTION";
	private static String ADD_COMMENT_DOCTOR = "ADD_COMMENT_DOCTOR";
	private static String ADD_COMMENT_SERVICE = "ADD_COMMENT_SERVICE";
	
	String addCommentFlag;
	
	InstitutionBean institution;
	DoctorBean doctor;
	ServiceBean service;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	private void initBtnHandler() {
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ProgressDlg.show();
				
				CommentBean comment = view.getComment();
				
				if (ADD_COMMENT_INSTITUTION.equals(addCommentFlag)) {
					comment.setInstitution(institution);
					
				} else if (ADD_COMMENT_DOCTOR.equals(addCommentFlag)) {
					comment.setDoctor(doctor);
					
				} else if (ADD_COMMENT_SERVICE.equals(addCommentFlag)) {
					comment.setService(service);
				}
				
				commentService.addComment(comment, new AsyncCallback<CommentBean>() {
					
					@Override
					public void onSuccess(CommentBean comment) {
						
						if (ADD_COMMENT_INSTITUTION.equals(addCommentFlag)) {
							eventBus.addInstComment(comment);
							
						} else if (ADD_COMMENT_DOCTOR.equals(addCommentFlag)) {
							eventBus.addDoctorComment(comment);
							
						} else if (ADD_COMMENT_SERVICE.equals(addCommentFlag)) {
							eventBus.addServiceComment(comment);
						}
						
						ProgressDlg.hide();
						view.hide();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						
						ProgressDlg.failure();
					}
				});
			}
		});
		
		view.setCancelBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	public void onAddInstSocialUserComment(SocialUser socialUser) {
		
		eventBus.setInstSocialUser(socialUser);
		
		view.setSocialUser(socialUser);
		view.show();
	}
	
	public void onAddDoctorSocialUserComment(SocialUser socialUser) {
		
		eventBus.setDoctorSocialUser(socialUser);
		
		view.setSocialUser(socialUser);
		view.show();
	}
	
	public void onAddServiceSocialUserComment(SocialUser socialUser) {
		
		eventBus.setServiceSocialUser(socialUser);
		
		view.setSocialUser(socialUser);
		view.show();
	}
	
	public void onSetInstitution(InstitutionBean institution) {
		
		addCommentFlag = ADD_COMMENT_INSTITUTION;
		this.institution = institution;
	}
	
	public void onSetDoctor(DoctorBean doctor) {
		
		addCommentFlag = ADD_COMMENT_DOCTOR;
		this.doctor = doctor;
	}
	
	public void onSetService(ServiceBean service) {
		
		addCommentFlag = ADD_COMMENT_SERVICE;
		this.service = service;
	}
}
