package com.example.service;

import com.example.model.PendudukModel;
import com.example.model.KotaModel;
import com.example.model.KelurahanModel;
import com.example.model.KeluargaModel;

import java.util.List;

import com.example.model.KecamatanModel;

public interface KependudukanService {
	PendudukModel selectPenduduk (String nik);
	KeluargaModel selectKeluarga (int id);
	KelurahanModel selectKelurahan (int id);
	KecamatanModel selectKecamatan (int id);
	KotaModel selectKota (int id);
	KeluargaModel selectKeluargaByNKK (String nkk);
	List<PendudukModel> selectPendudukByIDKeluarga (int idKeluarga);
	List<KecamatanModel> selectAllKecamatan ();
	void addPenduduk(PendudukModel penduduk);
	List<PendudukModel> selectSimilarPenduduk(String tanggal_lahir, int id_kelurahan, int id_kecamatan, int id_kota);
	List<KelurahanModel> selectAllKelurahan ();
	void addKeluarga(KeluargaModel keluarga);
	List<KeluargaModel> selectKeluargaByKecamatan (int id_kecamatan);
	void updatePenduduk (PendudukModel penduduk, String nik_lama);
	void updateKeluarga (KeluargaModel keluarga, String nkk_lama);
	void setMati (String nik);
	int keluargaSize (KeluargaModel keluarga);
	void setKeluargaTidakBerlaku (KeluargaModel keluarga);
}
