package com.infoklinik.rsvp.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class InstitutionSearchBean implements IsSerializable, Serializable {

	private String name;
	private String streetName;
	private String regionName;
	private Long cityId;
	private String category;
	private String type;
	private Long insuranceId;
	private Long specialityId;
	private Boolean isOpen24Hours;
	private Boolean isResidentialService;
	private LocationBean location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
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

	public Long getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(Long insuranceId) {
		this.insuranceId = insuranceId;
	}

	public Long getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Long specialityId) {
		this.specialityId = specialityId;
	}

	public Boolean isOpen24Hours() {
		return isOpen24Hours;
	}

	public void setOpen24Hours(Boolean isOpen24Hours) {
		this.isOpen24Hours = isOpen24Hours;
	}
	
	public String getOpen24Hours() {
		
		if (isOpen24Hours == null) {
			return "";
		} else if (isOpen24Hours) {
			return Constant.YES;
		} else {
			return Constant.NO;
		}
	}

	public Boolean isResidentialService() {
		return isResidentialService;
	}

	public void setResidentialService(Boolean isResidentialService) {
		this.isResidentialService = isResidentialService;
	}
	
	public String getResidentialService() {
		
		if (isOpen24Hours == null) {
			return "";
		} else if (isOpen24Hours) {
			return Constant.YES;
		} else {
			return Constant.NO;
		}
	}

	public LocationBean getLocation() {
		return location;
	}

	public void setLocation(LocationBean location) {
		this.location = location;
	}
}
