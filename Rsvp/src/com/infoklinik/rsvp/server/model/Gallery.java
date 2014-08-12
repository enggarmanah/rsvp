package com.infoklinik.rsvp.server.model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.GalleryBean;

@Entity
public class Gallery extends Base {
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inst_id")
	private Institution institution;
	
	private Long image_id;
	
	private String is_main;
	private String description;	

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Long getImage_id() {
		return image_id;
	}

	public void setImage_id(Long image_id) {
		this.image_id = image_id;
	}

	public String getIs_main() {
		return is_main;
	}

	public void setIs_main(String isMain) {
		this.is_main = isMain;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBean(GalleryBean galleryBean, EntityManager em) {
		
		id = galleryBean.getId();
		
		if (galleryBean.getInstitution() != null) {
			institution = em.find(Institution.class, galleryBean.getInstitution().getId());
		}
		
		image_id = galleryBean.getImage_id();
		is_main = galleryBean.isMain() ? Constant.YES : Constant.NO;
		description = galleryBean.getDescription();
		
		setAuditBean(galleryBean.getAuditBean());
	}
	
	public GalleryBean getBean() {
		
		GalleryBean galleryBean = new GalleryBean();
		
		galleryBean.setId(id);
		
		if (institution != null) {
			galleryBean.setInstitution(institution.getBean());
		}
		
		galleryBean.setImage_id(image_id);
		galleryBean.setMain(Constant.YES.equals(is_main));
		galleryBean.setDescription(description);
		
		galleryBean.setAuditBean(getAuditBean());
		
		return galleryBean;
	}
}
