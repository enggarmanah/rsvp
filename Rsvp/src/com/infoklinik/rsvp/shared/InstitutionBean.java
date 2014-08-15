package com.infoklinik.rsvp.shared;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class InstitutionBean extends BaseBean {
	
	public static String CATEGORY_CLINIC = "CLI";
	public static String CATEGORY_HOSPITAL = "HSP";
	public static String CATEGORY_LABORATORY = "LAB";
	public static String CATEGORY_PHARMANCY = "PHM";

	private String name;
	private String category;
	private String type;
	private String typeDescription;
	private String partnerType;
	private String telephone;
	private String email;
	private String website;
	private String facebook;
	private String twitter;
	private CityBean city;
	private RegionBean region;
	private StreetBean street;
	private String address;
	private Long distance;
	private Double locationLat;
	private Double locationLng;
	private String profile;
	private String opInfo;
	private String opHourStart;
	private String opHourEnd;
	private boolean isOp24hours;
	private String residentialService;
	private Long viewCount;
	private Long likeCount;
	private Long commentCount;
	private Long imageId;
	private Date displayDate;
	private String status;
	
	private List<ServiceBean> services;
	private List<InsuranceBean> insurances;
	private List<ScheduleBean> schedules;
	private List<GalleryBean> galleries;
	
	public String getHtmlInfo() {
		
		StringBuffer html = new StringBuffer();
		
		html.append("<div id='map'>");
		html.append("<div class='profile-inst'>");
		html.append("	<div class='profile-inst-name'>");
		html.append("		" + name);
		html.append("	</div>");
		html.append("	<div class='profile-inst-contact'>");
		html.append("		<div class='inst-subinfo-icon'>");
		html.append("			<img src='images/building.png' class='contact-img'/>");
		html.append("		</div>");
		html.append("		<div class='inst-subinfo-content'>");
		html.append("			" + address);
		html.append("		</div>");
		
		if (distance != null) {
			html.append("		<div class='inst-subinfo-icon'>");
			html.append("			<img src='images/sign.png' class='contact-img'/>");
			html.append("		</div>");
			html.append("		<div class='inst-subinfo-content'>");
			html.append("			" + SharedUtil.getDistanceInfo(distance));
			html.append("		</div>");
		}
		
		html.append("		<div class='inst-subinfo-icon'>");
		html.append("			<img src='images/phone.png' class='contact-img'/>");
		html.append("		</div>");
		html.append("		<div class='inst-subinfo-content'>");
		html.append("			" + telephone);
		html.append("		</div>");
		html.append("		<div class='inst-subinfo-icon'>");
		html.append("			<img src='images/mail.png' class='contact-img'/>");
		html.append("		</div>");
		html.append("		<div class='inst-subinfo-content'>");
		html.append("			" + email);
		html.append("		</div>");
		html.append("	</div>");
		html.append("</div>");
		html.append("</div>");
		
		return html.toString();
	}
	
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

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
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

	public CityBean getCity() {
		return city;
	}

	public void setCity(CityBean city) {
		this.city = city;
	}

	public RegionBean getRegion() {
		return region;
	}

	public void setRegion(RegionBean region) {
		this.region = region;
	}

	public StreetBean getStreet() {
		return street;
	}

	public void setStreet(StreetBean street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Double getLocationLat() {
		return locationLat;
	}
	
	public void setLocation(String location) {
		
		if (!SharedUtil.isEmpty(location)) {
			String[] coordinates = location.split(";");
			if (coordinates.length == 2) {
				locationLat = Double.valueOf(coordinates[0]);
				locationLng = Double.valueOf(coordinates[1]);
			}
		}
	}
	
	public String getLocation() {
		
		String location = Constant.EMPTY_STRING;
		if (locationLat != null && locationLng != null) {
			location = locationLat.toString() + ";" + locationLng.toString();
		}
		
		return location;
	}

	public void setLocationLat(Double locationLat) {
		this.locationLat = locationLat;
	}

	public Double getLocationLng() {
		return locationLng;
	}

	public void setLocationLng(Double locationLng) {
		this.locationLng = locationLng;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getOpInfo() {
		return opInfo;
	}

	public void setOpInfo(String opInfo) {
		this.opInfo = opInfo;
	}

	public String getOpHourStart() {
		return opHourStart;
	}

	public void setOpHourStart(String opHourStart) {
		this.opHourStart = opHourStart;
	}

	public String getOpHourEnd() {
		return opHourEnd;
	}

	public void setOpHourEnd(String opHourEnd) {
		this.opHourEnd = opHourEnd;
	}

	public boolean isOp24hours() {
		return isOp24hours;
	}

	public void setOp24hours(boolean op24hours) {
		this.isOp24hours = op24hours;
	}

	public String getResidentialService() {
		return residentialService;
	}

	public void setResidentialService(String residentialService) {
		this.residentialService = residentialService;
	}

	public List<ServiceBean> getServices() {
		return services;
	}

	public void setServices(List<ServiceBean> services) {
		this.services = services;
	}
	
	public List<GalleryBean> getGalleries() {
		return galleries;
	}

	public void setGalleries(List<GalleryBean> galleries) {
		this.galleries = galleries;
	}

	public List<InsuranceBean> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<InsuranceBean> insurances) {
		this.insurances = insurances;
	}

	public List<ScheduleBean> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ScheduleBean> schedules) {
		this.schedules = schedules;
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

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Date getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
