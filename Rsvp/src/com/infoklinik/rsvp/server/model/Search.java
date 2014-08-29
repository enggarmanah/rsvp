package com.infoklinik.rsvp.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.infoklinik.rsvp.shared.SearchBean;
import com.infoklinik.rsvp.shared.SharedUtil;

@Entity
public class Search {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String type;
	private String name;
	private Long city_id;
	private String region_name;
	private String street_name;
	private Long distance;
	private Double lat;
	private Double lng;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date req_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Date getReq_time() {
		return req_time;
	}

	public void setReq_time(Date reqTime) {
		this.req_time = reqTime;
	}
	
	
	public void setBean(SearchBean searchBean) {

		id = searchBean.getId();
		type = searchBean.getType();
		city_id = searchBean.getCityId();
		
		if (!SharedUtil.isEmpty(searchBean.getName())) {
			name = searchBean.getName();
		}
		
		if (!SharedUtil.isEmpty(searchBean.getRegionName())) {
			region_name = searchBean.getRegionName();
		}
		
		if (!SharedUtil.isEmpty(searchBean.getStreetName())) {
			street_name = searchBean.getStreetName();
		}
		
		distance = searchBean.getDistance();
		lat = searchBean.getLat();
		lng = searchBean.getLng();
		req_time = searchBean.getReqTime();
	}

	public SearchBean getBean() {

		SearchBean searchBean = new SearchBean();

		searchBean.setId(id);
		searchBean.setName(name);
		searchBean.setCityId(city_id);
		searchBean.setRegionName(region_name);
		searchBean.setStreetName(street_name);
		searchBean.setDistance(distance);
		searchBean.setLat(lat);
		searchBean.setLng(lng);
		searchBean.setReqTime(req_time);
		
		return searchBean;
	}
}
