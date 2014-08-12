package com.infoklinik.rsvp.client.social.presenter.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.SocialUser;
import com.mvp4g.client.view.LazyView;

public interface IServiceCommentView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setService(ServiceBean service);
	
	void setSocialUser(SocialUser socialUser);
	
	void setComments(List<GenericBean<CommentBean>> comments);
	
	void addComment(GenericBean<CommentBean> comment);
	
	void deleteComment(GenericBean<CommentBean> comment);	
	
	void setAddBtnClickHandler(ClickHandler handler);
	
	void setOkBtnClickHandler(ClickHandler handler);
}
