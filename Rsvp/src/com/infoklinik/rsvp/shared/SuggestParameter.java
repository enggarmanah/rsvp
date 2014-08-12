package com.infoklinik.rsvp.shared;

import java.util.ArrayList;

public class SuggestParameter {
	
	private String type;
	private String name;
	private String cityId;
	
	private static int nameIndex = 5;
	private static int cityIdIndex = 7;
	
	public SuggestParameter() {}
	
	public SuggestParameter(String type) {
		this.type = type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	public String getSuggestQuery() {
		
		StringBuffer query = new StringBuffer();
		ArrayList<String> params = new ArrayList<String>();
		
		if (!SharedUtil.isEmpty(name)) {
			params.add("name=" + name);
		}
		
		if (!SharedUtil.isEmpty(name)) {
			params.add("cityId=" + cityId);
		}
		
		int i = 0;
		
		for (String param : params) {
			if (i > 0) {
				query.append(":");
			}
			query.append(param);
			i++;
		}
		
		return query.toString(); 
	}
	
	public void setSuggestQuery(String input) {
		
		String[] params = input.split(":");
		for (String param : params) {
			if (param.indexOf("name") != -1) {
				name = param.substring(nameIndex);
			} else if (param.indexOf("cityId") != -1) {
				cityId = param.substring(cityIdIndex);
			} 
		}
	}
}
