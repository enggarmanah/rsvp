package com.infoklinik.rsvp.shared;

public class Constant {
	
	public static final String ENV_STAGING = "Staging";
	public static final String ENV_PRODUCTION = "Production";
	
	public static final String APP_CALLBACK_URL_PROD = "http://www.infoklinik.com/verifyCredential";
	public static final String APP_CALLBACK_URL_DEV = "http://www.infoklinik.com:8888/verifyCredential";
	
	public static final String SMS_GW_BULKSMS = "SMS_GW_BULKSMS";
	public static final String SMS_GW_WEBSMS = "SMS_GW_WEBSMS";
	
	public static final String SEX_MALE = "L";
	public static final String SEX_FEMALE = "P";
	
	public static final String CATEGORY_DOCTOR = "DOC";
	public static final String CATEGORY_SERVICE = "SRV";
	public static final String CATEGORY_CLINIC = "CLI";
	public static final String CATEGORY_HOSPITAL = "HSP";
	public static final String CATEGORY_LABORATORY = "LAB";
	public static final String CATEGORY_PHARMACY = "PHM";
	public static final String CATEGORY_INSURANCE = "INS";
	
	public static final String IMAGE_URL = "/image?id=";
	
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "I";
	
	public static final String SEARCH_INSTITUTION = "SEARCH_INSTITUTION";
	public static final String SEARCH_CLINIC = "SEARCH_CLINIC";
	public static final String SEARCH_HOSPITAL = "SEARCH_HOSPITAL";
	public static final String SEARCH_LAB = "SEARCH_LAB";
	public static final String SEARCH_PHARMACY = "SEARCH_PHARMACY";
	public static final String SEARCH_DOCTOR = "SEARCH_DOCTOR";
	public static final String SEARCH_SERVICE = "SEARCH_SERVICE";
	
	public static final String MENU_CLINIC = "MENU_CLINIC";
	public static final String MENU_HOSPITAL = "MENU_HOSPITAL";
	public static final String MENU_DOCTOR = "MENU_DOCTOR";
	public static final String MENU_LAB = "MENU_LAB";
	public static final String MENU_PHARMACY = "MENU_PHARMACY";
	public static final String MENU_SERVICE = "MENU_SERVICE";
	
	public static final String TASK_INSTITUTION_COMMENT_ADD = "TASK_INSTITUTION_COMMENT_ADD";
	public static final String TASK_INSTITUTION_LIKE_ADD = "TASK_INSTITUTION_LIKE_ADD";
	public static final String TASK_DOCTOR_COMMENT_ADD = "TASK_DOCTOR_COMMENT_ADD";
	public static final String TASK_DOCTOR_LIKE_ADD = "TASK_DOCTOR_LIKE_ADD";
	public static final String TASK_SERVICE_COMMENT_ADD = "TASK_SERVICE_COMMENT_ADD";
	public static final String TASK_SERVICE_LIKE_ADD = "TASK_SERVICE_LIKE_ADD";
	
	public static final String SYSTEM_ID = "System";
	
	public static final Long DR_SPECIALITY_DENTIST = Long.valueOf(1); 
	
	public static final String OPTION_PLS_SELECT_CODE = "";
	public static final String OPTION_PLS_SELECT_CODE_DESC = "Silahkan Pilih";
	
	public static final String SEARCH_BY_NAME = "SEARCH_BY_NAME";
	public static final String SEARCH_BY_STREET = "SEARCH_BY_STREET";
	public static final String SEARCH_BY_REGION = "SEARCH_BY_REGION";
	public static final String SEARCH_BY_DISTANCE = "SEARCH_BY_DISTANCE";
	
	public static final String STYLE_FADE_IN = "panel-fade-in";
	public static final String STYLE_FADE_OUT = "panel-fade-out";
	
	public static final String STYLE_MENU_SELECTED = "menu-current";
	public static final String STYLE_MENU = "menu";
	
	public static final String EMPTY_STRING = "";
	public static final String ZERO_STRING = "0";
	
	public static final String YES = "Y";
	public static final String NO = "N";
	
	public static final String YES_DESC = "Ya";
	public static final String NO_DESC = "Tidak";
	
	public static final String ACTIVE = "A";
	public static final String INACTIVE = "I";
	
	public static final String ACTIVE_DESC = "Aktif";
	public static final String INACTIVE_DESC = "Tidak Aktif";
	
	public static final String SUPERUSER = "SUPERUSER";
	public static final String OPERATION = "OPERATION";
	
	public static final String SUPERUSER_DESC = "Superuser";
	public static final String OPERATION_DESC = "Operasional";
	
	public static final int DAY_START = 1;
	public static final int DAY_END = 7;
	
	public static final int DAY_ALL = -1;
	public static final String DAY_ALL_DESC = "Semua Hari";
	
	public static final int DAY_MONDAY = 1;
	public static final String DAY_MONDAY_DESC = "Senin";
	
	public static final int DAY_TUESDAY = 2;
	public static final String DAY_TUESDAY_DESC = "Selasa";
	
	public static final int DAY_WEDNESDAY = 3;
	public static final String DAY_WEDNESDAY_DESC = "Rabu";
	
	public static final int DAY_THURSDAY = 4;
	public static final String DAY_THURSDAY_DESC = "Kamis";
	
	public static final int DAY_FRIDAY = 5;
	public static final String DAY_FRIDAY_DESC = "Jumat";
	
	public static final int DAY_SATURDAY = 6;
	public static final String DAY_SATURDAY_DESC = "Sabtu";
	
	public static final int DAY_SUNDAY = 7;
	public static final String DAY_SUNDAY_DESC = "Minggu";
	
	public static final int APPT_INTERVAL_MINUTES = 30;
	
	public static final String TIMEZONE = "GMT+7:00";
	
	public static final int HOUR_SECS = 3600;
	public static final int MIN_SECS = 60;
	public static final int MILISECS = 1000;
	
	public static final int OPENING_TIME_START = 7 * 60 * 60 * 1000;
	public static final int OPENING_TIME_END = 22 * 60 * 60 * 1000;
	public static final int OPENING_PERIOD = 30 * 60 * 1000;
	
	public static final int QUERY_MAX_RESULT = 100;
	
	public static final int DISPLAY_TIME = 2000;
	public static final int FADE_TIME = 700;
	
	public static final int GALLERY_TIME = 6;
	
	public static final int MAP_DEFAULT_SCALE = 15;
	
	public static final double MAP_METER_LENGTH = 0.000011;
	
	public static final double MAP_DEFAULT_LAT = -6.211807421831029;
	public static final double MAP_DEFAULT_LNG = 106.81933879852295;
	
	public static final int SOCIAL_VERIFICAITON_TIMEOUT = 60000;
	
	public static final int PAGE_SIZE = 10;
	
	public static final int POPUP_L1_TOP = 70;
	public static final int POPUP_L2_TOP = 108;
	public static final int POPUP_L2_LEFT = 14;
	
	public static final int FILE_UPLOAD_MAX_SIZE = 500000;
}
