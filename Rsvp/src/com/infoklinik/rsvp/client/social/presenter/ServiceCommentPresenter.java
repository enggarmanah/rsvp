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
import com.infoklinik.rsvp.client.social.presenter.interfaces.IServiceCommentView;
import com.infoklinik.rsvp.client.social.view.ServiceCommentView;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.SocialUser;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = ServiceCommentView.class)
public class ServiceCommentPresenter extends LazyPresenter<IServiceCommentView, SocialEventBus> {
	
	@Inject
	CommentServiceAsync commentService;
	
	ServiceBean service;
	InstitutionBean institution;
	SocialUser socialUser;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	private void initLinksHandler() {
		
		view.setWebsiteClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.open(Constant.LINK_WEBSITE + institution.getWebsite(), "_blank", null);
			}
		});
		
		view.setFacebookClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.open(Constant.LINK_FACEBOOK + institution.getFacebook(), "_blank", null);
			}
		});
		
		view.setTwitterClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.open(Constant.LINK_TWITTER + institution.getTwitter(), "_blank", null);
			}
		});
	}
	
	private void initBtnHandler() {
		
		view.setAddBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.setService(service);
				
				if (socialUser != null) {
					eventBus.addServiceSocialUserComment(socialUser);
				} else {
					eventBus.verifySocialUserToExecuteTask(Constant.TASK_SERVICE_COMMENT_ADD);
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
	
	public void onLoadServiceComment(final ServiceBean service) {
		
		this.service = service;
		this.institution = service.getInstitution();
		
		initLinksHandler();
		
		ProgressDlg.show();
		
		commentService.getServiceComments(service.getId(), new AsyncCallback<List<CommentBean>>() {
			
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
				
				view.setService(service);
				view.setComments(comments);
				
				view.show();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure();
			}
		});
	}
	
	public void onSetServiceSocialUser(SocialUser socialUser) {
		
		this.socialUser = socialUser;
		view.setSocialUser(socialUser);
	}
	
	public void onAddServiceComment(CommentBean comment) {
		
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
