package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class StreetBean extends BaseBean {

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
	
	public void setBean(StreetBean streetBean) {
		
		id = streetBean.getId();
		name = streetBean.getName();
		
		city = new CityBean();
		city.setBean(streetBean.getCity());
		
		setAuditBean(streetBean.getAuditBean());
	}
}
