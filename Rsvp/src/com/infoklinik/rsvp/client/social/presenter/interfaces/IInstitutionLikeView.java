package com.infoklinik.rsvp.client.social.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.LikeBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.SocialUser;
import com.mvp4g.client.view.LazyView;

public interface IInstitutionLikeView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setInstitution(InstitutionBean institution);
	
	void setSocialUser(SocialUser socialUser);
	
	void setLikes(List<GenericBean<LikeBean>> likes);
	
	void addLike(GenericBean<LikeBean> like);
	
	void deleteLike(GenericBean<LikeBean> like);	
	
	void setLikeBtnClickHandler(ClickHandler handler);
	
	void setWebsiteClickHandler(ClickHandler handler);
	
	void setFacebookClickHandler(ClickHandler handler);
	
	void setTwitterClickHandler(ClickHandler handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setLike(boolean isLike);
}
