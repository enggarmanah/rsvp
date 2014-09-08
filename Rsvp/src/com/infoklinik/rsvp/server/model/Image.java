package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.infoklinik.rsvp.shared.ImageBean;

@Entity
@Table(name="image")
public class Image extends Base {
	
	private byte[] bytes;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setBean(ImageBean imageBean) {
		
		id = imageBean.getId();
		bytes = imageBean.getBytes();
		
		setAuditBean(imageBean.getAuditBean());
	}
	
	public ImageBean getBean() {
		
		ImageBean imageBean = new ImageBean();
		
		imageBean.setId(id);
		imageBean.setBytes(bytes);
		
		imageBean.setAuditBean(getAuditBean());
		
		return imageBean;
	}
}
