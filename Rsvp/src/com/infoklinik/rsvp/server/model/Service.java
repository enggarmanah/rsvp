package com.infoklinik.rsvp.server.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ServiceBean;

@Entity
public class Service extends Base {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inst_id")
	private Institution institution;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_type_id")
	private ServiceType serviceType;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="service", targetEntity=Like.class)
	private List<Like> likes;
	
	private String name;
	private String price;
	private String description;
	private String is_promo;
	private String promo_price;
	
	@Temporal(TemporalType.DATE)
	private Date promo_start_date;
	
	@Temporal(TemporalType.DATE)
	private Date promo_end_date;
	
	private Long view_count;
	private Long like_count;
	private Long comment_count;
	
	private Long image_id;
	private String status;
	
	@Transient
	private boolean isLoadInstitution = false;
	
	@Transient
	private boolean isLoadServiceType = false;
	
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
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

	public boolean getIs_promo() {
		return Constant.YES.equals(is_promo);
	}

	public void setIs_promo(boolean isPromo) {
		String value = Constant.NO; 
		if (isPromo) {
			value = Constant.YES;
		}
		this.is_promo = value;
	}

	public String getPromo_price() {
		return promo_price;
	}

	public void setPromo_price(String promo_price) {
		this.promo_price = promo_price;
	}

	public Date getPromo_start_date() {
		return promo_start_date;
	}

	public void setPromo_start_date(Date promo_start_date) {
		this.promo_start_date = promo_start_date;
	}

	public Date getPromo_end_date() {
		return promo_end_date;
	}

	public void setPromo_end_date(Date promo_end_date) {
		this.promo_end_date = promo_end_date;
	}
	
	public Long getView_count() {
		return view_count;
	}

	public void setView_count(Long view_count) {
		this.view_count = view_count;
	}

	public Long getLike_count() {
		return like_count;
	}

	public void setLike_count(Long like_count) {
		this.like_count = like_count;
	}

	public Long getComment_count() {
		return comment_count;
	}

	public void setComment_count(Long comment_count) {
		this.comment_count = comment_count;
	}

	public Long getImage_id() {
		return image_id;
	}

	public void setImage_id(Long image_id) {
		this.image_id = image_id;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isLoadInstitution() {
		return isLoadInstitution;
	}

	public void setLoadInstitution(boolean isLoadInstitution) {
		this.isLoadInstitution = isLoadInstitution;
	}

	public boolean isLoadServiceType() {
		return isLoadServiceType;
	}

	public void setLoadServiceType(boolean isLoadServiceType) {
		this.isLoadServiceType = isLoadServiceType;
	}
	
	public Service loadInstitution() {
		this.isLoadInstitution = true;
		return this;
	}
	
	public Service loadServiceType() {
		this.isLoadServiceType = true;
		return this;
	}
	
	public ServiceBean getBean() {
		
		ServiceBean serviceBean = new ServiceBean();
		
		serviceBean.setId(id);
		
		if (isLoadInstitution) {
			serviceBean.setInstitution(institution.getBean());
		}
		
		if (isLoadServiceType) {
			serviceBean.setServiceType(serviceType.getBean());
		}
		
		serviceBean.setName(name);
		serviceBean.setPrice(price);
		serviceBean.setDescription(description);
		serviceBean.setPromo(Constant.YES.equals(is_promo));
		serviceBean.setPromoPrice(promo_price);
		serviceBean.setPromoStartDate(ServerUtil.toDate(promo_start_date));
		serviceBean.setPromoEndDate(ServerUtil.toDate(promo_end_date));
		serviceBean.setViewCount(view_count);
		serviceBean.setLikeCount(like_count);
		serviceBean.setCommentCount(comment_count);
		serviceBean.setImageId(image_id);
		serviceBean.setStatus(status);
		
		return serviceBean;
	}
	
	public void setBean(ServiceBean serviceBean, EntityManager em) {
		
		id = serviceBean.getId();
		
		if (serviceBean.getInstitution() != null) {
			institution = em.find(Institution.class, serviceBean.getInstitution().getId());
		}
		
		if (serviceBean.getServiceType() != null) {
			serviceType = em.find(ServiceType.class, serviceBean.getServiceType().getId());
		}
		
		name = serviceBean.getName();
		price = serviceBean.getPrice();
		description = serviceBean.getDescription();
		is_promo = serviceBean.isPromo() ? Constant.YES : Constant.NO;
		promo_price = serviceBean.getPromoPrice();
		promo_start_date = serviceBean.getPromoStartDate();
		promo_end_date = serviceBean.getPromoEndDate();
		view_count = serviceBean.getViewCount();
		like_count = serviceBean.getLikeCount();
		comment_count = serviceBean.getCommentCount();
		image_id = serviceBean.getImageId();
		status = serviceBean.getStatus();
		
		setAuditBean(serviceBean.getAuditBean());
	}
}
