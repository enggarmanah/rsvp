package com.infoklinik.rsvp.client.social.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CommentServiceAsync;
import com.infoklinik.rsvp.client.social.SocialEventBus;
import com.infoklinik.rsvp.client.social.presenter.interfaces.IDoctorCommentView;
import com.infoklinik.rsvp.client.social.view.DoctorCommentView;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.SocialUser;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = DoctorCommentView.class)
public class DoctorCommentPresenter extends LazyPresenter<IDoctorCommentView, SocialEventBus> {
	
	@Inject
	CommentServiceAsync commentService;
	
	DoctorBean doctor;
	SocialUser socialUser;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	private void initBtnHandler() {
		
		view.setAddBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.setDoctor(doctor);
				
				if (socialUser != null) {
					eventBus.addDoctorSocialUserComment(socialUser);
				} else {
					eventBus.verifySocialUserToExecuteTask(Constant.TASK_DOCTOR_COMMENT_ADD);
				}
			}
		});
		
		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	public void onLoadDoctorComment(final DoctorBean doctor) {
		
		this.doctor = doctor;
		
		ProgressDlg.show();
		
		commentService.getDoctorComments(doctor.getId(), new AsyncCallback<List<CommentBean>>() {
			
			@Override
			public void onSuccess(List<CommentBean> result) {
				
				ProgressDlg.hidePrompt();
				
				List<GenericBean<CommentBean>> comments = new ArrayList<GenericBean<CommentBean>>();
				
				for (CommentBean comment : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					
					GenericBean<CommentBean> genComment = new GenericBean<CommentBean>(comment, handlerMgr);
					
					handlerMgr.setDeleteHandler(getDeleteHandler(genComment));
					
					comments.add(genComment);
				}
				
				view.setDoctor(doctor);
				view.setComments(comments);
				
				Window.scrollTo (0,0);
				
				view.show();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure();
			}
		});
	}
	
	public void onSetDoctorSocialUser(SocialUser socialUser) {
		
		this.socialUser = socialUser;
		view.setSocialUser(socialUser);
	}
	
	public void onAddDoctorComment(CommentBean comment) {
		
		HandlerManager handlerMgr = new HandlerManager();
		
		GenericBean<CommentBean> genComment = new GenericBean<CommentBean>(comment, handlerMgr);
		
		handlerMgr.setDeleteHandler(getDeleteHandler(genComment));
		
		view.addComment(genComment);
	}
	
	public ClickHandler getDeleteHandler(final GenericBean<CommentBean> comment) {
		
		return new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ProgressDlg.show();
				
				commentService.deleteComment(comment.getBean(), new AsyncCallback<CommentBean>() {
					
					@Override
					public void onSuccess(CommentBean result) {
						
						view.deleteComment(comment);
						ProgressDlg.hide();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						
						ProgressDlg.failure();
					}
				});
			}
		};
	}
}
