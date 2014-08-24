
package com.infoklinik.rsvp.client.listing.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.SharedUtil;

public class ResultInstitutionView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, ResultInstitutionView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	HTMLPanel mainPanel;
	
	@UiField
	Label indexLb;
	
	@UiField
	Label nameLb;
	
	@UiField
	Image updateImg;
	
	@UiField
	Image deleteImg;
	
	@UiField
	HTMLPanel distancePanel;
	
	@UiField
	Label distanceLb;
	
	@UiField
	Label addressLb;
	
	@UiField
	Label telephoneLb;
	
	@UiField
	Label emailLb;
	
	@UiField
	Label typeDescriptionLb;
	
	@UiField
	HTML opInfoLb;
	
	@UiField
	Label viewCountLb;
	
	@UiField
	Label likeCountLb;
	
	@UiField
	Label commentCountLb;
	
	@UiField
	Image viewCountImg;
	
	@UiField
	Image likeCountImg;
	
	@UiField
	Image commentCountImg;
	
	public ResultInstitutionView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setInstitution(int index, InstitutionBean institution, HandlerManager handlerMgr) {
		
		if (ClientUtil.isAdminUser) {
			mainPanel.addStyleName("admin");
			updateImg.addClickHandler(handlerMgr.getUpdateHandler());
		} else {
			updateImg.setVisible(false);
			deleteImg.setVisible(false);
		}
		
		indexLb.setText(index + ".");
		nameLb.setText(institution.getName());
		
		if (institution.getDistance() != null) {
			distancePanel.setVisible(true);
			distanceLb.setText(SharedUtil.getDistanceInfo(institution.getDistance()));
		} else {
			distancePanel.setVisible(false);
		}
		
		addressLb.setText(institution.getAddress());
		telephoneLb.setText(institution.getTelephone());
		emailLb.setText(institution.getEmail());
		typeDescriptionLb.setText(institution.getTypeDescription());
		
		opInfoLb.setHTML(new SafeHtmlBuilder().appendEscapedLines(institution.getOpInfo()).toSafeHtml());
		
		viewCountLb.setText(String.valueOf(institution.getViewCount()));
		likeCountLb.setText(String.valueOf(institution.getLikeCount()));
		commentCountLb.setText(String.valueOf(institution.getCommentCount()));
		
		nameLb.addClickHandler(handlerMgr.getShowHandler());
		
		commentCountLb.addClickHandler(handlerMgr.getCommentHandler());
		commentCountImg.addClickHandler(handlerMgr.getCommentHandler());
		
		likeCountLb.addClickHandler(handlerMgr.getLikeHandler());
		likeCountImg.addClickHandler(handlerMgr.getLikeHandler());
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return this;
	}
}
