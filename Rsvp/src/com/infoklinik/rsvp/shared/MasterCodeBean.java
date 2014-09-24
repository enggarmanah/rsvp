package com.infoklinik.rsvp.shared;

@SuppressWarnings("serial")
public class MasterCodeBean extends BaseBean {
	
	public static String INST_CATEGORY = "INST_CATEGORY";
	public static String PARTNER_TYPE = "PARTNER_TYPE";
	
	public static String CLINIC_TYPE = "CLINIC_TYPE";
	public static String HOSPITAL_TYPE = "HOSPITAL_TYPE";
	public static String LAB_TYPE = "LAB_TYPE";
	public static String PHARMACY_TYPE = "LAB_PHARMACY";
	
	public static String SERVICE_CATEGORY = "SERVICE_CATEGORY"; 
	
	public static String DAY = "DAY";
	
	private String code;
	private String value;
	private String type;
	private Integer displayOrder;
	private String status;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
