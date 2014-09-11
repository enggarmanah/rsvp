
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
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.infoklinik.rsvp.shared.SharedUtil;

public class ResultServiceView extends BaseView {
	
	interface ModuleUiBinder extends UiBinder<Widget, ResultServiceView> {}
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	@UiField
	Label serviceNameLb;
	
	@UiField
	Label descriptionLb;
	
	@UiField
	Label priceLb;
	
	@UiField
	Label institutionLb;
	
	@UiField
	Label indexLb;
	
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
	
	public ResultServiceView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setService(int index, GenericBean<ServiceBean> genService) {
		
		ServiceBean service = genService.getBean();
		HandlerManager handlerMgr = genService.getHandlerMgr();
		
		serviceNameLb.setText(service.getServiceType().getName() + " - " + service.getName());
		//serviceNameLb.addClickHandler(handlerMgr.getShowHandler());
		
		InstitutionBean institution = service.getInstitution();
		
		indexLb.setText(index + ".");
		
		descriptionLb.setText(service.getDescription());
		priceLb.setText("Harga Promo : " + service.getPrice() + ". Harga Normal : " + service.getPromoPrice());
		institutionLb.setText(service.getInstitution().getName());
		
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
		
		viewCountLb.setText(String.valueOf(service.getViewCount()));
		likeCountLb.setText(String.valueOf(service.getLikeCount()));
		commentCountLb.setText(String.valueOf(service.getCommentCount()));
		
		serviceNameLb.addClickHandler(handlerMgr.getShowHandler());
		institutionLb.addClickHandler(handlerMgr.getShowMoreHandler());
		
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
