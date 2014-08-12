package com.infoklinik.rsvp.shared;

import java.util.Date;

@SuppressWarnings("serial")
public class ServiceBean extends BaseBean {

	private InstitutionBean institutionBean;
	private ServiceTypeBean serviceTypeBean;
	private String name;
	private String price;
	private String description;
	private boolean isPromo;
	private String promoPrice;
	private Date promoStartDate;
	private Date promoEndDate;
	private Long viewCount = Long.valueOf(0);
	private Long likeCount = Long.valueOf(0);
	private Long commentCount = Long.valueOf(0);
	private Long imageId;
	private String status = Constant.STATUS_ACTIVE;
	private Long distance;

	public InstitutionBean getInstitution() {
		return institutionBean;
	}

	public void setInstitution(InstitutionBean institutionBean) {
		this.institutionBean = institutionBean;
	}

	public ServiceTypeBean getServiceType() {
		return serviceTypeBean;
	}

	public void setServiceType(ServiceTypeBean serviceTypeBean) {
		this.serviceTypeBean = serviceTypeBean;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPromo() {
		return isPromo;
	}

	public void setPromo(boolean isPromo) {
		this.isPromo = isPromo;
	}

	public String getPromoPrice() {
		return promoPrice;
	}

	public void setPromoPrice(String promoPrice) {
		this.promoPrice = promoPrice;
	}

	public Date getPromoStartDate() {
		return promoStartDate;
	}

	public void setPromoStartDate(Date promoStartDate) {
		this.promoStartDate = promoStartDate;
	}

	public Date getPromoEndDate() {
		return promoEndDate;
	}

	public void setPromoEndDate(Date promoEndDate) {
		this.promoEndDate = promoEndDate;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	
	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}
}
