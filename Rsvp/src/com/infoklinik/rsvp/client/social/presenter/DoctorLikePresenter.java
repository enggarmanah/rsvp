package com.infoklinik.rsvp.client.social.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.LikeServiceAsync;
import com.infoklinik.rsvp.client.social.SocialEventBus;
import com.infoklinik.rsvp.client.social.presenter.interfaces.IDoctorLikeView;
import com.infoklinik.rsvp.client.social.view.DoctorLikeView;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.LikeBean;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.SocialUser;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = DoctorLikeView.class)
public class DoctorLikePresenter extends LazyPresenter<IDoctorLikeView, SocialEventBus> {
	
	@Inject
	LikeServiceAsync likeService;
	
	DoctorBean doctor;
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
						eventBus.addDoctorSocialUserLike(socialUser);
					} else {
						eventBus.verifySocialUserToExecuteTask(Constant.TASK_DOCTOR_LIKE_ADD);
					}
					
				} else {
					
					LikeBean like = new LikeBean();
					like.setDoctor(doctor);
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
	
	public void onLoadDoctorLike(final DoctorBean doctor) {
		
		this.doctor = doctor;
		this.isLike = true;
		
		view.setLike(isLike);
		
		ProgressDlg.show();
		
		likeService.getDoctorLikes(doctor.getId(), new AsyncCallback<List<LikeBean>>() {
			
			@Override
			public void onSuccess(List<LikeBean> result) {
				
				ProgressDlg.hidePrompt();
				
				List<GenericBean<LikeBean>> likes = new ArrayList<GenericBean<LikeBean>>();
				
				for (LikeBean like : result) {
					
					HandlerManager handlerMgr = new HandlerManager();
					
					GenericBean<LikeBean> genLike = new GenericBean<LikeBean>(like, handlerMgr);
					
					likes.add(genLike);
				}
				
				view.setDoctor(doctor);
				view.setLikes(likes);
				
				view.show();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure();
			}
		});
	}
	
	public void onAddDoctorSocialUserLike(SocialUser socialUser) {
		
		this.socialUser = socialUser;
		
		view.setSocialUser(socialUser);
		
		LikeBean like = new LikeBean();
		
		like.setDoctor(doctor);
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
				
				eventBus.loadDoctorLike(doctor);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure();
			}
		});
	}
}
