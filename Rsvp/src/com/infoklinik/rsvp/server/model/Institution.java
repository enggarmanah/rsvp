package com.infoklinik.rsvp.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.infoklinik.rsvp.server.ServerUtil;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.InsuranceBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ServiceBean;

@Entity
public class Institution extends Base {

	private String name;
	private String category;
	private String type;
	private String partner_type;
	private String telephone;
	private String email;
	private String website;
	private String facebook;
	private String twitter;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "street_id")
	private Street street;
	
	private String address;
	private Double location_lat;
	private Double location_lng;
	private String profile;
	private String op_info;
	private String op_hour_start;
	private String op_hour_end;
	private String op_24hours;
	private String residential_service;
	private Long view_count;
	private Long like_count;
	private Long comment_count;
	private Long image_id;
	private Date display_date;
	private String status;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name = "inst_insurance", 
	            joinColumns = { @JoinColumn(name = "inst_id")}, 
	            inverseJoinColumns={@JoinColumn(name="insurance_id")})
	private List<Insurance> insurances;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="institution", targetEntity=Schedule.class)
	private List<Schedule> schedules;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="institution", targetEntity=Gallery.class)
	private List<Gallery> galleries;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="institution", targetEntity=Service.class)
	private List<Service> services;
	
	@Transient
	private boolean isLoadInsurances = false;
	
	@Transient
	private boolean isLoadServices = false;
	
	@Transient
	private boolean isLoadGalleries = false;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPartner_type() {
		return partner_type;
	}

	public void setPartner_type(String partner_type) {
		this.partner_type = partner_type;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLocation_lat() {
		return location_lat;
	}

	public void setLocation_lat(Double location_lat) {
		this.location_lat = location_lat;
	}

	public Double getLocation_lng() {
		return location_lng;
	}

	public void setLocation_lng(Double location_lng) {
		this.location_lng = location_lng;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getOp_info() {
		return op_info;
	}

	public void setOp_info(String op_info) {
		this.op_info = op_info;
	}

	public String getOp_hour_start() {
		return op_hour_start;
	}

	public void setOp_hour_start(String op_hour_start) {
		this.op_hour_start = op_hour_start;
	}

	public String getOp_hour_end() {
		return op_hour_end;
	}

	public void setOp_hour_end(String op_hour_end) {
		this.op_hour_end = op_hour_end;
	}

	public boolean isOp_24hours() {
		return Constant.YES.equals(op_24hours);
	}

	public void setOp_24hours(boolean op_24hours) {
		String value = Constant.NO; 
		if (op_24hours) {
			value = Constant.YES;
		}
		this.op_24hours = value;
	}

	public String getResidential_service() {
		return residential_service;
	}

	public void setResidential_service(String residential_service) {
		this.residential_service = residential_service;
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

	public Date getDisplay_date() {
		return display_date;
	}

	public void setDisplay_date(Date display_date) {
		this.display_date = display_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Insurance> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}
	
	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	public void setBean(InstitutionBean instBean, EntityManager em) {
		
		id = instBean.getId();
		name = instBean.getName();
		category = instBean.getCategory();
		type = instBean.getType();
		partner_type = instBean.getPartnerType();
		telephone = instBean.getTelephone();
		email = instBean.getEmail();
		website= instBean.getWebsite();
		facebook = instBean.getFacebook();
		twitter = instBean.getTwitter();
		
		if (instBean.getCity() != null) {
			city = em.find(City.class, instBean.getCity().getId());
		}
		
		if (instBean.getRegion() != null) {
			region = em.find(Region.class, instBean.getRegion().getId());
		}
		
		if (instBean.getStreet() != null) {
			street = em.find(Street.class, instBean.getStreet().getId());
		}
		
		// updating service
		
		if (instBean.getServices() != null) {
			
			HashMap<Long, Service> serviceMap = new HashMap<Long, Service>();
			
			for (Service service : services) {
				serviceMap.put(service.getId(), service);
			}
			
			for (ServiceBean serviceBean : instBean.getServices()) {
				
				Service service = serviceMap.remove(serviceBean.getId());
				
				if (service == null) {
					service = new Service();
					service.setBean(serviceBean, em);
				} else {
					service.setBean(serviceBean, em);
					service.setStatus(Constant.STATUS_ACTIVE);
				}
				
				services.add(service);
			}
			
			for (Long key : serviceMap.keySet()) {
				Service service = serviceMap.get(key);
				services.remove(service);
			}
		}
		
		// updating gallery
		
		if (instBean.getGalleries() != null) {
			
			HashMap<Long, Gallery> galleryMap = new HashMap<Long, Gallery>();
			
			for (Gallery gallery : galleries) {
				galleryMap.put(gallery.getId(), gallery);
			}
			
			for (GalleryBean galleryBean : instBean.getGalleries()) {
				
				Gallery gallery = galleryMap.remove(galleryBean.getId());
				
				if (gallery == null) {
					gallery = new Gallery();
					gallery.setBean(galleryBean, em);
				} else {
					gallery.setBean(galleryBean, em);
				}
				
				galleries.add(gallery);
			}
			
			for (Long key : galleryMap.keySet()) {
				Gallery gallery = galleryMap.get(key);
				galleries.remove(gallery);
				
				Image image = em.find(Image.class, gallery.getImage_id());
				em.remove(image);
			}
		}
		
		// updating insurance
		
		if (instBean.getInsurances() != null) {
			
			HashMap<Long, Insurance> insuranceMap = new HashMap<Long, Insurance>();
			
			for (Insurance insurance : insurances) {
				insuranceMap.put(insurance.getId(), insurance);
			}
			
			for (InsuranceBean insuranceBean : instBean.getInsurances()) {
				
				Insurance insurance = insuranceMap.remove(insuranceBean.getId());
				
				if (insurance == null) {
					insurance = new Insurance();
					insurance.setBean(insuranceBean);
				} else {
					insurance.setBean(insuranceBean);
				}
				
				insurances.add(insurance);
			}
			
			for (Long key : insuranceMap.keySet()) {
				Insurance insurance = insuranceMap.get(key);
				insurances.remove(insurance);
			}
		}
		
		// updating schedule
		
		if (instBean.getSchedules() != null) {
			
			HashMap<Long, Schedule> scheduleMap = new HashMap<Long, Schedule>();
			
			for (Schedule schedule : schedules) {
				scheduleMap.put(schedule.getId(), schedule);
			}
			
			for (ScheduleBean scheduleBean : instBean.getSchedules()) {
				
				Schedule schedule = scheduleMap.remove(scheduleBean.getId());
				
				if (schedule == null) {
					schedule = new Schedule();
					schedule.setBean(scheduleBean, em);
				} else {
					schedule.setBean(scheduleBean, em);
				}
				
				schedules.add(schedule);
			}
			
			for (Long key : scheduleMap.keySet()) {
				Schedule schedule = scheduleMap.get(key);
				schedules.remove(schedule);
			}
		}
		
		address = instBean.getAddress();
		location_lat = instBean.getLocationLat();
		location_lng = instBean.getLocationLng();
		profile = instBean.getProfile();
		op_info = instBean.getOpInfo();
		op_hour_start = instBean.getOpHourStart();
		op_hour_end = instBean.getOpHourEnd();
		setOp_24hours(instBean.isOp24hours());
		residential_service = instBean.getResidentialService();
		view_count = instBean.getViewCount();
		like_count = instBean.getLikeCount();
		comment_count = instBean.getCommentCount();
		
		if (image_id != null && image_id != instBean.getImageId()) {
			Image image = em.find(Image.class, image_id);
			em.remove(image);
		}
		
		image_id = instBean.getImageId();
				
		display_date = instBean.getDisplayDate();
		status = instBean.getStatus();
		
		setAuditBean(instBean.getAuditBean());
	}
	
	public InstitutionBean getBean() {
		
		InstitutionBean instBean = new InstitutionBean();
		
		instBean.setId(id);
		instBean.setName(name);
		instBean.setCategory(category);
		instBean.setType(type);
		instBean.setPartnerType(partner_type);
		instBean.setTelephone(telephone);
		instBean.setEmail(email);
		instBean.setWebsite(website);
		instBean.setFacebook(facebook);
		instBean.setTwitter(twitter);
		
		if (city != null) {
			instBean.setCity(city.getBean());
		}
		
		if (region != null) {
			instBean.setRegion(region.getBean());
		}
		
		if (street != null) {
			instBean.setStreet(street.getBean());
		}
		
		instBean.setAddress(address);
		instBean.setLocationLat(location_lat);
		instBean.setLocationLng(location_lng);
		instBean.setProfile(profile);
		instBean.setOpInfo(op_info);
		instBean.setOpHourStart(op_hour_start);
		instBean.setOpHourEnd(op_hour_end);
		instBean.setOp24hours(isOp_24hours());
		instBean.setResidentialService(residential_service);
		instBean.setViewCount(view_count);
		instBean.setLikeCount(like_count);
		instBean.setCommentCount(comment_count);
		instBean.setImageId(image_id);
		instBean.setDisplayDate(ServerUtil.toDate(display_date));
		instBean.setStatus(status);
		
		if (isLoadServices && services != null) {
			
			List<ServiceBean> list = new ArrayList<ServiceBean>();
			for (Service service : services) {
				list.add(service.getBean());
			}
			
			instBean.setServices(list);
		}
		
		if (isLoadGalleries && galleries != null) {
			
			List<GalleryBean> list = new ArrayList<GalleryBean>();
			for (Gallery gallery : galleries) {
				list.add(gallery.getBean());
			}
			
			instBean.setGalleries(list);
		}
		
		if (isLoadInsurances && insurances != null) {
			
			List<InsuranceBean> list = new ArrayList<InsuranceBean>();
			for (Insurance insurance : insurances) {
				list.add(insurance.getBean());
			}
			
			instBean.setInsurances(list);
		}
		
		instBean.setAuditBean(getAuditBean());
		
		return instBean;
	}
	
	public Institution loadInsurances() {
		
		isLoadInsurances = true;
		return this;
	}
	
	public Institution loadServices() {
		
		isLoadServices = true;
		return this;
	}
	
	public Institution loadGalleries() {
		
		isLoadGalleries = true;
		return this;
	}
}
