package com.example.dao;

import com.example.model.PendudukModel;
import com.example.model.KotaModel;
import com.example.model.KelurahanModel;
import com.example.model.KeluargaModel;
import com.example.model.KecamatanModel;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface KependudukanMapper {
    
	@Select("SELECT * FROM penduduk WHERE nik = #{nik}")
    PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("SELECT * FROM keluarga WHERE id = #{id}")
	KeluargaModel selectKeluarga (@Param("id") int id);
	
	@Select("SELECT * FROM kelurahan WHERE id = #{id}")
    KelurahanModel selectKelurahan (@Param("id") int id);
	
	@Select("SELECT * FROM kecamatan WHERE id = #{id}")
	KecamatanModel selectKecamatan (@Param("id") int id);
	
	@Select("SELECT * FROM kota WHERE id = #{id}")
    KotaModel selectKota (@Param("id") int id);
	
	@Select("SELECT * FROM keluarga WHERE nomor_kk = #{nkk}")
    KeluargaModel selectKeluargaByNKK (@Param("nkk") String nkk);
	
	@Select("SELECT * FROM penduduk WHERE id_keluarga = #{idKeluarga}")
    List<PendudukModel> selectPendudukByIDKeluarga (@Param("idKeluarga") int idKeluarga);
	
	@Select("SELECT * FROM kecamatan")
    List<KecamatanModel> selectAllKecamatan();
	
	@Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, "
			+ "is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, "
			+ "golongan_darah, is_wafat) VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir}, "
			+ "#{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, "
			+ "#{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk (PendudukModel penduduk);
	
	@Select("SELECT DISTINCT p.nama FROM penduduk p, keluarga k, kelurahan kel, kecamatan kec, "
			+ "kota ko WHERE p.tanggal_lahir = #{tanggal_lahir} AND p.id_keluarga = k.id AND k.id_kelurahan = "
			+ "#{id_kelurahan} AND kel.id_kecamatan = #{id_kecamatan} AND kec.id_kota = #{id_kota}")
	List<PendudukModel> selectSimilarPenduduk (@Param("tanggal_lahir") String tanggal_lahir, @Param("id_kelurahan")
	int id_kelurahan, @Param("id_kecamatan") int id_kecamatan, @Param("id_kota") int id_kota);
	
	@Select("SELECT * FROM kelurahan")
    List<KelurahanModel> selectAllKelurahan();
	
	@Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) "
			+ "VALUES (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
    void addKeluarga (KeluargaModel keluarga);
	
	@Select("SELECT * FROM keluarga k, kelurahan kel WHERE k.id_kelurahan = kel.id AND kel.id_kecamatan = "
			+ "#{id_kecamatan}")
    List<KeluargaModel> selectKeluargaByKecamatan (@Param("id_kecamatan") int id_kecamatan);
	
	@Update("UPDATE penduduk SET nik = #{penduduk.nik}, nama = #{penduduk.nama}, jenis_kelamin = "
			+ "#{penduduk.jenis_kelamin}, golongan_darah = #{penduduk.golongan_darah}, agama = #{penduduk.agama}, "
			+ "status_perkawinan = #{penduduk.status_perkawinan}, pekerjaan = #{penduduk.pekerjaan}, is_wni = "
			+ "#{penduduk.is_wni}, id_keluarga = #{penduduk.id_keluarga} WHERE nik = #{nik_lama}")
	void updatePenduduk (@Param ("penduduk") PendudukModel penduduk, @Param("nik_lama") String nik_lama);
	
	@Insert("UPDATE keluarga SET nomor_kk = #{keluarga.nomor_kk}, alamat = #{keluarga.alamat}, rt = #{keluarga.rt}, "
			+ "rw = #{keluarga.rw}, id_kelurahan = #{keluarga.id_kelurahan} WHERE nomor_kk = #{nkk_lama}")
	void updateKeluarga (@Param("keluarga") KeluargaModel keluarga, @Param("nkk_lama") String nkk_lama);
	
	@Update("UPDATE penduduk SET is_wafat = '1' WHERE nik = #{nik}")
    void setMati (@Param("nik") String nik);
	
	@Select("SELECT DISTINCT COUNT(p.nama) FROM penduduk p, keluarga k WHERE p.id_keluarga = k.id AND "
			+ "p.is_wafat = '0' AND k.nomor_kk = #{nomor_kk}")
    int keluargaSize (KeluargaModel keluarga);

    @Update("UPDATE keluarga SET is_tidak_berlaku = '1' WHERE nomor_kk = #{nomor_kk}")
    void setKeluargaTidakBerlaku (KeluargaModel keluarga);

}
