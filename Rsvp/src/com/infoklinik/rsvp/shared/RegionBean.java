package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class RegionBean extends BaseBean {

	private String name;
	private CityBean city;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CityBean getCity() {
		return city;
	}

	public void setCity(CityBean city) {
		this.city = city;
	}
	
	public void setBean(RegionBean regionBean) {
		
		id = regionBean.getId();
		name = regionBean.getName();
		
		city = new CityBean();
		city.setBean(regionBean.getCity());
		
		setAuditBean(regionBean.getAuditBean());
	}
}
