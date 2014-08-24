package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class GalleryBean extends BaseBean {

	private InstitutionBean institution;
	private Long imageId;
	private boolean isMain;
	private String description;
	
	public InstitutionBean getInstitution() {
		return institution;
	}

	public void setInstitution(InstitutionBean institution) {
		this.institution = institution;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setBean(GalleryBean galleryBean) {
		
		id = galleryBean.getId();
		institution = galleryBean.getInstitution();
		imageId = galleryBean.getImageId();
		isMain = galleryBean.isMain();
		description = galleryBean.getDescription();
		
		setAuditBean(galleryBean.getAuditBean());
	}
}
