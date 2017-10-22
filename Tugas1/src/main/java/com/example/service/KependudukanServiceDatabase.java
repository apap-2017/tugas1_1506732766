package com.example.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KependudukanMapper;
import com.example.model.PendudukModel;
import com.example.model.KotaModel;
import com.example.model.KelurahanModel;
import com.example.model.KeluargaModel;
import com.example.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KependudukanServiceDatabase implements KependudukanService {
	
	@Autowired
	private KependudukanMapper kependudukanMapper;
	
	@Override
	public PendudukModel selectPenduduk (String nik)
    {
        log.info ("select penduduk with nik {}", nik);
        return kependudukanMapper.selectPenduduk (nik);
    }
	
	@Override
	public KeluargaModel selectKeluarga (int id)
    {
        log.info ("select keluarga with id {}", id);
        return kependudukanMapper.selectKeluarga (id);
    }
	
	@Override
	public KelurahanModel selectKelurahan (int id)
    {
        log.info ("select kelurahan with id {}", id);
        return kependudukanMapper.selectKelurahan (id);
    }
	
	@Override
	public KecamatanModel selectKecamatan (int id)
    {
        log.info ("select kecamatan with id {}", id);
        return kependudukanMapper.selectKecamatan (id);
    }
	
	@Override
	public KotaModel selectKota (int id)
    {
        log.info ("select kota with id {}", id);
        return kependudukanMapper.selectKota (id);
    }
	
	@Override
	public KeluargaModel selectKeluargaByNKK (String nkk)
    {
        log.info ("select keluarga with id {}", nkk);
        return kependudukanMapper.selectKeluargaByNKK (nkk);
    }
	
	@Override
    public List<PendudukModel> selectPendudukByIDKeluarga (int idKeluarga)
    {
        log.info ("select penduduk with id_keluarga {}", idKeluarga);
        return kependudukanMapper.selectPendudukByIDKeluarga (idKeluarga);
    }
	
	@Override
    public List<KecamatanModel> selectAllKecamatan ()
    {
        log.info ("select all kecamatan");
        return kependudukanMapper.selectAllKecamatan ();
    }
	
	@Override
    public void addPenduduk (PendudukModel penduduk)
    {
        log.info ("insert penduduk");
        kependudukanMapper.addPenduduk (penduduk);
    }
	
	@Override
    public List<PendudukModel> selectSimilarPenduduk (String tanggal_lahir, int id_kelurahan, int id_kecamatan, int id_kota)
    {
        log.info ("select penduduk with tanggal_lahir {}", tanggal_lahir);
        return kependudukanMapper.selectSimilarPenduduk (tanggal_lahir, id_kelurahan, id_kecamatan, id_kota);
    }
	
	@Override
    public List<KelurahanModel> selectAllKelurahan ()
    {
        log.info ("select all kelurahan");
        return kependudukanMapper.selectAllKelurahan ();
    }
	
	@Override
    public void addKeluarga (KeluargaModel keluarga)
    {
        log.info ("insert keluarga");
        kependudukanMapper.addKeluarga (keluarga);
    }
	
	
	@Override
    public List<KeluargaModel> selectKeluargaByKecamatan (int id_kecamatan)
    {
        log.info ("select all kelurahan");
        return kependudukanMapper.selectKeluargaByKecamatan (id_kecamatan);
    }
	
	@Override
    public void updatePenduduk (PendudukModel penduduk, String nik_lama)
    {
        log.info ("change penduduk with nik {}", nik_lama);
        kependudukanMapper.updatePenduduk (penduduk, nik_lama);
    }
	
	@Override
    public void updateKeluarga (KeluargaModel keluarga, String nkk_lama)
    {
        log.info ("change keluarga with nkk {}", nkk_lama);
        kependudukanMapper.updateKeluarga (keluarga, nkk_lama);
    }
}
