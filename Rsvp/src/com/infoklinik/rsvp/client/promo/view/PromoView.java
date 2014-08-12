package com.infoklinik.rsvp.client.promo.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.client.BaseView;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.promo.presenter.interfaces.IPromoView;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ServiceBean;

public class PromoView extends BaseView implements IPromoView {
	
	interface ModuleUiBinder extends UiBinder<Widget, PromoView> {}
	
	@UiField
	VerticalPanel promoPanel;
	
	private static ModuleUiBinder uiBinder = GWT.create(ModuleUiBinder.class);
	
	public void createView() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		
		return this;
	}
	
	public Widget getRootWidget() {
		
		return promoPanel;
	}
	
	public void addPromo(GenericBean<ServiceBean> service) {
		
		ServiceView serviceView = new ServiceView();
		serviceView.setService(service);
		
		promoPanel.add(serviceView);
	}
	
	public void fadeIn() {
		
		promoPanel.setStyleName(Constant.STYLE_FADE_IN);
	}
	
	public void fadeOut() {
		
		promoPanel.setStyleName(Constant.STYLE_FADE_OUT);
	}
	
	public void clear() {
		
		promoPanel.clear();
	}
}
