package com.infoklinik.rsvp.client;

public class GenericBean<T> {

	T bean;
	HandlerManager handlerMgr;
	
	public GenericBean(T bean, HandlerManager handlerMgr) {
		
		this.bean = bean;
		this.handlerMgr = handlerMgr;
	}
	
	public T getBean() {
		
		return bean;
	}

	public void setBean(T bean) {
		
		this.bean = bean;
	}

	public HandlerManager getHandlerMgr() {
		
		return handlerMgr;
	}

	public void setHandlerMgr(HandlerManager handlerMgr) {
		
		this.handlerMgr = handlerMgr;
	}
}
