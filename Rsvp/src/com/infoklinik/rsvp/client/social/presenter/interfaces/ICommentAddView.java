package com.infoklinik.rsvp.client.social.presenter.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.CommentBean;
import com.infoklinik.rsvp.shared.SocialUser;
import com.mvp4g.client.view.LazyView;

public interface ICommentAddView extends LazyView, IsWidget {
	
	Widget asWidget();
	
	void show();
	
	void hide();
	
	void setSocialUser(SocialUser socialUser);
	
	CommentBean getComment();
	
	void setOkBtnClickHandler(ClickHandler handler);
	
	void setCancelBtnClickHandler(ClickHandler handler);
}
