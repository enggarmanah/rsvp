package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class GalleryBean extends BaseBean {

	private InstitutionBean institution;
	private Long image_id;
	private boolean isMain;
	private String description;
	
	public InstitutionBean getInstitution() {
		return institution;
	}

	public void setInstitution(InstitutionBean institution) {
		this.institution = institution;
	}

	public Long getImage_id() {
		return image_id;
	}

	public void setImage_id(Long image_id) {
		this.image_id = image_id;
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
}
