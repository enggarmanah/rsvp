package com.infoklinik.rsvp.client.social;

import com.google.gwt.user.client.ui.IsWidget;
import com.infoklinik.rsvp.client.CustomLogger;
import com.infoklinik.rsvp.client.social.presenter.CommentAddPresenter;
import com.infoklinik.rsvp.client.social.presenter.DoctorCommentPresenter;
import com.infoklinik.rsvp.client.social.presenter.InstitutionCommentPresenter;
import com.infoklinik.rsvp.client.social.presenter.DoctorLikePresenter;
import com.infoklinik.rsvp.client.social.presenter.InstitutionLikePresenter;
import com.infoklinik.rsvp.client.social.presenter.ServiceCommentPresenter;
import com.infoklinik.rsvp.client.social.presenter.ServiceLikePresenter;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.SocialUser;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;

@Events(startPresenter = InstitutionCommentPresenter.class, module = SocialModule.class)
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
public interface SocialEventBus extends EventBus {
	
	@Event(forwardToParent = true)
	public void loadChildModuleView(IsWidget widget);
	
	@Event(forwardToParent = true)
	public void verifySocialUserToExecuteTask(String task);
	
	@Event(handlers = InstitutionCommentPresenter.class)
	public void setInstSocialUser(SocialUser socialUser);
	
	@Event(handlers = DoctorCommentPresenter.class)
	public void setDoctorSocialUser(SocialUser socialUser);
	
	@Event(handlers = ServiceCommentPresenter.class)
	public void setServiceSocialUser(SocialUser socialUser);
	
	@Event(handlers = InstitutionCommentPresenter.class)
	public void loadInstitutionComment(InstitutionBean institution);
	
	@Event(handlers = DoctorCommentPresenter.class)
	public void loadDoctorComment(DoctorBean institution);
	
	@Event(handlers = ServiceCommentPresenter.class)
	public void loadServiceComment(ServiceBean service);
	
	@Event(handlers = InstitutionCommentPresenter.class)
	public void addInstComment(CommentBean comment);
	
	@Event(handlers = DoctorCommentPresenter.class)
	public void addDoctorComment(CommentBean comment);
	
	@Event(handlers = ServiceCommentPresenter.class)
	public void addServiceComment(CommentBean comment);
	
	@Event(handlers = CommentAddPresenter.class)
	public void addInstSocialUserComment(SocialUser socialUser);
	
	@Event(handlers = CommentAddPresenter.class)
	public void addDoctorSocialUserComment(SocialUser socialUser);
	
	@Event(handlers = CommentAddPresenter.class)
	public void addServiceSocialUserComment(SocialUser socialUser);
	
	@Event(handlers = InstitutionLikePresenter.class)
	public void loadInstitutionLike(InstitutionBean institution);
	
	@Event(handlers = InstitutionLikePresenter.class)
	public void addInstitutionSocialUserLike(SocialUser socialUser);
	
	@Event(handlers = DoctorLikePresenter.class)
	public void loadDoctorLike(DoctorBean doctor);
	
	@Event(handlers = DoctorLikePresenter.class)
	public void addDoctorSocialUserLike(SocialUser socialUser);
	
	@Event(handlers = ServiceLikePresenter.class)
	public void loadServiceLike(ServiceBean service);
	
	@Event(handlers = ServiceLikePresenter.class)
	public void addServiceSocialUserLike(SocialUser socialUser);
	
	@Event(handlers = CommentAddPresenter.class)
	public void setInstitution(InstitutionBean institution);
	
	@Event(handlers = CommentAddPresenter.class)
	public void setDoctor(DoctorBean doctor);
	
	@Event(handlers = CommentAddPresenter.class)
	public void setService(ServiceBean doctor);
}
