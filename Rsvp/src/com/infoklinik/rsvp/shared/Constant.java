package com.infoklinik.rsvp.shared;

public class Constant {
	
	public static String ENV_STAGING = "Staging";
	public static String ENV_PRODUCTION = "Production";
		
	public static String CATEGORY_DOCTOR = "DOC";
	public static String CATEGORY_SERVICE = "SRV";
	public static String CATEGORY_CLINIC = "CLI";
	public static String CATEGORY_HOSPITAL = "HSP";
	public static String CATEGORY_LABORATORY = "LAB";
	public static String CATEGORY_PHARMACY = "PHM";
	public static String CATEGORY_INSURANCE = "INS";
	
	public static String IMAGE_URL = "/image?id=";
	
	public static String STATUS_ACTIVE = "A";
	public static String STATUS_INACTIVE = "I";
	
	public static String SEARCH_INSTITUTION = "SEARCH_INSTITUTION";
	public static String SEARCH_CLINIC = "SEARCH_CLINIC";
	public static String SEARCH_HOSPITAL = "SEARCH_HOSPITAL";
	public static String SEARCH_LAB = "SEARCH_LAB";
	public static String SEARCH_PHARMACY = "SEARCH_PHARMACY";
	public static String SEARCH_DOCTOR = "SEARCH_DOCTOR";
	public static String SEARCH_SERVICE = "SEARCH_SERVICE";
	
	public static String MENU_CLINIC = "MENU_CLINIC";
	public static String MENU_HOSPITAL = "MENU_HOSPITAL";
	public static String MENU_DOCTOR = "MENU_DOCTOR";
	public static String MENU_SERVICE = "MENU_SERVICE";
	
	public static String TASK_INSTITUTION_COMMENT_ADD = "TASK_INSTITUTION_COMMENT_ADD";
	public static String TASK_INSTITUTION_LIKE_ADD = "TASK_INSTITUTION_LIKE_ADD";
	public static String TASK_DOCTOR_COMMENT_ADD = "TASK_DOCTOR_COMMENT_ADD";
	public static String TASK_DOCTOR_LIKE_ADD = "TASK_DOCTOR_LIKE_ADD";
	public static String TASK_SERVICE_COMMENT_ADD = "TASK_SERVICE_COMMENT_ADD";
	public static String TASK_SERVICE_LIKE_ADD = "TASK_SERVICE_LIKE_ADD";
	
	public static String SYSTEM_ID = "System";
	
	public static Long DR_SPECIALITY_DENTIST = Long.valueOf(1); 
	
	public static String OPTION_PLS_SELECT_CODE = "";
	public static String OPTION_PLS_SELECT_CODE_DESC = "Silahkan Pilih";
	
	public static String SEARCH_BY_NAME = "SEARCH_BY_NAME";
	public static String SEARCH_BY_STREET = "SEARCH_BY_STREET";
	public static String SEARCH_BY_REGION = "SEARCH_BY_REGION";
	public static String SEARCH_BY_DISTANCE = "SEARCH_BY_DISTANCE";
	
	public static String STYLE_FADE_IN = "panel-fade-in";
	public static String STYLE_FADE_OUT = "panel-fade-out";
	
	public static String STYLE_MENU_SELECTED = "menu-current";
	public static String STYLE_MENU = "menu";
	
	public static String EMPTY_STRING = "";
	public static String ZERO_STRING = "0";
	
	public static String YES = "Y";
	public static String NO = "N";
	
	public static String YES_DESC = "Ya";
	public static String NO_DESC = "Tidak";
	
	public static int DAY_START = 1;
	public static int DAY_END = 7;
	
	public static int DAY_ALL = -1;
	public static String DAY_ALL_DESC = "Semua Hari";
	
	public static int DAY_MONDAY = 1;
	public static String DAY_MONDAY_DESC = "Senin";
	
	public static int DAY_TUESDAY = 2;
	public static String DAY_TUESDAY_DESC = "Selasa";
	
	public static int DAY_WEDNESDAY = 3;
	public static String DAY_WEDNESDAY_DESC = "Rabu";
	
	public static int DAY_THURSDAY = 4;
	public static String DAY_THURSDAY_DESC = "Kamis";
	
	public static int DAY_FRIDAY = 5;
	public static String DAY_FRIDAY_DESC = "Jumat";
	
	public static int DAY_SATURDAY = 6;
	public static String DAY_SATURDAY_DESC = "Sabtu";
	
	public static int DAY_SUNDAY = 7;
	public static String DAY_SUNDAY_DESC = "Minggu";
	
	public static int APPT_INTERVAL_MINUTES = 30;
	
	public static int HOUR_SECS = 3600;
	public static int MIN_SECS = 60;
	public static int MILISECS = 1000;
	
	public static int OPENING_TIME_START = 7 * 60 * 60 * 1000;
	public static int OPENING_TIME_END = 22 * 60 * 60 * 1000;
	public static int OPENING_PERIOD = 30 * 60 * 1000;
	
	public static int QUERY_MAX_RESULT = 100;
	
	public static int DISPLAY_TIME = 2000;
	public static int FADE_TIME = 700;
	
	public static int GALLERY_TIME = 6;
	
	public static int MAP_DEFAULT_SCALE = 15;
	
	public static double MAP_METER_LENGTH = 0.000011;
	
	public static double MAP_DEFAULT_LAT = -6.211807421831029;
	public static double MAP_DEFAULT_LNG = 106.81933879852295;
	
	public static int SOCIAL_VERIFICAITON_TIMEOUT = 60000;
	
	public static int PAGE_SIZE = 10;
	
	public static int POPUP_L1_TOP = 70;
	public static int POPUP_L2_TOP = 108;
	public static int POPUP_L2_LEFT = 14;
	
	public static int FILE_UPLOAD_MAX_SIZE = 500000;
}
