package com.infoklinik.rsvp.client;

import com.infoklinik.rsvp.shared.Constant;

public class Message {
	
	public static String NOTIFY_STREET_SELECTED = "Pastikan kota anda telah terpilih dengan benar.\nNama jalan akan ditampilkan dari daftar nama jalan di kota tersebut.";
	public static String NOTIFY_REGION_SELECTED = "Pastikan kota anda telah terpilih dengan benar.\nLokasi akan ditampilkan dari daftar lokasi di kota tersebut.";
	
	public static String SEARCH_RESULT_EMPTY = "Tidak ditemukan hasil dari pencarian berdasarkan kriteria anda. \nSilahkan ubah kriteria pencarian.";
	public static String SEARCH_EXCEED_QUERY_MAX_RESULT = "Sistem hanya akan menampilkan maksimal \n" + Constant.QUERY_MAX_RESULT + " record dari hasil pencarian.";
	
	public static String ERR_INST_NAME_EMPTY = "Nama institusi tidak boleh kosong.";
	public static String ERR_INST_PARTNER_TYPE_EMPTY = "Status partnership harus dipilih.";
	public static String ERR_INST_CATEGORY_EMPTY = "Kategori institusi harus dipilih.";
	public static String ERR_INST_TYPE_EMPTY = "Tipe institusi harus dipilih.";
	public static String ERR_INST_CITY_EMPTY = "Kota harus dipilih dari daftar kota yang tersedia.";
	public static String ERR_INST_INSURANCE_EMPTY = "Asuransi harus dipilih.";
	public static String ERR_INST_DOCTOR_EMPTY = "Dokter harus dipilih dari daftar dokter yang tersedia.";
	public static String ERR_INST_DOCTOR_EXIST = "Dokter telah terdaftar.";
	public static String ERR_INST_DOC_SCHEDULE_DAY_EMPTY = "Silahkan pilih hari praktek.";
	public static String ERR_INST_DOC_SCHEDULE_TIME_START_EMPTY = "Silahkan pilih jam praktek dimulai.";
	public static String ERR_INST_DOC_SCHEDULE_TIME_END_EMPTY = "Silahkan pilih jam praktek berakhir.";
	
	public static String ERR_SERVICE_NAME_EMPTY = "Nama layanan tidak boleh kosong.";
	public static String ERR_SERVICE_TYPE_EMPTY = "Kategori layanan harus dipilih.";
	public static String ERR_SERVICE_PRICE_EMPTY = "Harga layanan tidak boleh kosong.";
	public static String ERR_SERVICE_DESC_EMPTY = "Deskripsi layanan tidak boleh kosong.";
	
	public static String ERR_FILE_UPLOAD_EXCEED_MAX_SIZE = "Ukuran file melebihi besar maksimum (500 KB)";
	public static String ERR_FILE_UPLOAD = "Gagal melaksanakan upload file ke server";
}
