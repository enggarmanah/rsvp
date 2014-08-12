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
import com.infoklinik.rsvp.client.rpc.LikeServiceAsync;
import com.infoklinik.rsvp.client.social.SocialEventBus;
import com.infoklinik.rsvp.client.social.presenter.interfaces.IInstitutionLikeView;
import com.infoklinik.rsvp.client.social.view.InstitutionLikeView;
import com.infoklinik.rsvp.shared.LikeBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.SocialUser;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = InstitutionLikeView.class)
public class InstitutionLikePresenter extends LazyPresenter<IInstitutionLikeView, SocialEventBus> {
	
	@Inject
	LikeServiceAsync likeService;
	
	InstitutionBean institution;
	SocialUser socialUser;
	
	boolean isLike = true;
	
	@Override
	public void bindView() {
		
		initBtnHandler();
	}
	
	private void initBtnHandler() {
		
		view.setLikeBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (isLike) {
				
					if (socialUser != null) {
						eventBus.addInstitutionSocialUserLike(socialUser);
					} else {
						eventBus.verifySocialUserToExecuteTask(Constant.TASK_INSTITUTION_LIKE_ADD);
					}
					
				} else {
					
					LikeBean like = new LikeBean();
					like.setInstitution(institution);
					like.setFbId(socialUser.getId());
					deleteLike(like);
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
	
	public void onLoadInstitutionLike(final InstitutionBean institution) {
		
		this.institution = institution;
		this.isLike = true;
		
		view.setLike(isLike);
		
		ProgressDlg.show();
		
		likeService.getInstLikes(institution.getId(), new AsyncCallback<List<LikeBean>>() {
			
			@Override
			public void onSuccess(List<LikeBean> result) {
				
				ProgressDlg.hidePrompt();
				
				List<GenericBean<LikeBean>> likes = new ArrayList<GenericBean<LikeBean>>();
				
				for (LikeBean like : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					
					GenericBean<LikeBean> genLike = new GenericBean<LikeBean>(like, handlerMgr);
					
					likes.add(genLike);
				}
				
				view.setInstitution(institution);
				view.setLikes(likes);
				
				Window.scrollTo (0,0);
				
				view.show();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure();
			}
		});
	}
	
	public void onAddInstitutionSocialUserLike(SocialUser socialUser) {
		
		this.socialUser = socialUser;
		
		view.setSocialUser(socialUser);
		
		LikeBean like = new LikeBean();
		
		like.setInstitution(institution);
		like.setFbId(socialUser.getId());
		like.setFbName(socialUser.getName());
		
		ProgressDlg.show();
		
		likeService.addLike(like, new AsyncCallback<LikeBean>() {
			
			@Override
			public void onSuccess(LikeBean like) {
				
				ProgressDlg.hide();
				addLike(like);
				isLike = false;
				view.setLike(isLike);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure();
			}
		});
	}
	
	private void addLike(LikeBean like) {
		
		HandlerManager handlerMgr = new HandlerManager();
		
		GenericBean<LikeBean> genLike = new GenericBean<LikeBean>(like, handlerMgr);
		
		view.addLike(genLike);
	}
	
	private void deleteLike(LikeBean like) {
		
		ProgressDlg.show();
		
		likeService.deleteLike(like, new AsyncCallback<LikeBean>() {
			
			@Override
			public void onSuccess(LikeBean result) {
				
				isLike = true;
				view.setLike(isLike);
				ProgressDlg.hide();
				
				eventBus.loadInstitutionLike(institution);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure();
			}
		});
	}
}
