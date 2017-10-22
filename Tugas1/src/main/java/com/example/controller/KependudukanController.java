package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.PendudukModel;
import com.example.model.KotaModel;
import com.example.model.KelurahanModel;
import com.example.model.KeluargaModel;
import com.example.model.KecamatanModel;
import com.example.service.KependudukanService;

@Controller
public class KependudukanController {

	@Autowired
	KependudukanService kependudukanDAO;
	
    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }
    
    @RequestMapping("/penduduk")
	public String viewPenduduk(@RequestParam(value = "nik", required = true) String nik, Model model) {
		PendudukModel penduduk = kependudukanDAO.selectPenduduk(nik);
		KeluargaModel keluarga = kependudukanDAO.selectKeluarga(penduduk.getId_keluarga());
		KelurahanModel kelurahan = kependudukanDAO.selectKelurahan(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kependudukanDAO.selectKecamatan(kelurahan.getId_kecamatan());
		KotaModel kota = kependudukanDAO.selectKota(kecamatan.getId_kota());
		
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kelurahan", kelurahan);
		model.addAttribute("kota", kota);
		model.addAttribute("keluarga", keluarga);
		
		return "viewPenduduk";
	}
    
    @RequestMapping("/keluarga")
	public String viewKeluarga(@RequestParam(value = "nkk", required = true) String nkk, Model model) {
		KeluargaModel keluarga = kependudukanDAO.selectKeluargaByNKK(nkk);
		List<PendudukModel> penduduk = kependudukanDAO.selectPendudukByIDKeluarga(keluarga.getId());
		KelurahanModel kelurahan = kependudukanDAO.selectKelurahan(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kependudukanDAO.selectKecamatan(kelurahan.getId_kecamatan());
		KotaModel kota = kependudukanDAO.selectKota(kecamatan.getId_kota());
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("penduduks", penduduk);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kelurahan", kelurahan);
		model.addAttribute("kota", kota);
		
		return "viewKeluarga";
	}
    
    @RequestMapping(value = "/penduduk/tambah")
    public String tambahPenduduk(Model model) {
		PendudukModel penduduk = new PendudukModel();
		List<KecamatanModel> kecamatans = kependudukanDAO.selectAllKecamatan();
		
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("kecamatans", kecamatans);
		
    	return "tambahPenduduk";
    }
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
    public String tambahPendudukSubmit(Model model, @Valid @ModelAttribute("penduduk") PendudukModel penduduk, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<KecamatanModel> kecamatans = kependudukanDAO.selectAllKecamatan();
			
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("kecamatans", kecamatans);
			
	    	return "tambahPenduduk";
		}
		 String nik = "";

			KeluargaModel keluarga = kependudukanDAO.selectKeluarga(penduduk.getId_keluarga());
			KelurahanModel kelurahan = kependudukanDAO.selectKelurahan(keluarga.getId_kelurahan());
			KecamatanModel kecamatan = kependudukanDAO.selectKecamatan(kelurahan.getId_kecamatan());
			KotaModel kota = kependudukanDAO.selectKota(kecamatan.getId_kota());

			nik += kecamatan.getKode_kecamatan().substring(0,6);

	        if(penduduk.getJenis_kelamin() == 0){

	        	String[] tanggal_lahir = penduduk.getTanggal_lahir().split("-");
	            nik += tanggal_lahir[2];
	            nik += tanggal_lahir[1];
	            nik += tanggal_lahir[0].substring(2);

	        } else {
	            
	            String[] tanggal_lahir = penduduk.getTanggal_lahir().split("-");
	            nik += Integer.toString(Integer.parseInt(tanggal_lahir[2]) + 40);
	            nik += tanggal_lahir[1];
	            nik += tanggal_lahir[0].substring(2);
	        }

	        List<PendudukModel> pendudukLain = kependudukanDAO.selectSimilarPenduduk(penduduk.getTanggal_lahir(), kelurahan.getId(), kecamatan.getId(), kota.getId());

	        String nomor_urut = Integer.toString(pendudukLain.size() + 1);

	        if(nomor_urut.length() == 1){
	            nik += "000";
	            nik += nomor_urut;
	        } else if(nomor_urut.length() == 2){
	            nik += "00";
	            nik += nomor_urut;
	        } else if(nomor_urut.length() == 3){
	            nik += "0";
	            nik += nomor_urut;
	        } else if(nomor_urut.length() == 4){
	            nik += nomor_urut;
	        }

	        penduduk.setNik(nik);

	        kependudukanDAO.addPenduduk(penduduk);
	        model.addAttribute("penduduk", penduduk);
		
    		return "berhasilTambahPenduduk";
    }
	
	@RequestMapping("/keluarga/tambah")
	public String tambahKeluarga(Model model) {
		KeluargaModel keluarga = new KeluargaModel();
		List<KelurahanModel> kelurahans = kependudukanDAO.selectAllKelurahan();
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("kelurahans", kelurahans);
		
		return "tambahKeluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
    public String formTambahKeluarga(Model model, @Valid @ModelAttribute("keluarga") KeluargaModel keluarga, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			List<KelurahanModel> kelurahans = kependudukanDAO.selectAllKelurahan();
			
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahans", kelurahans);
			
			return "tambahKeluarga";
		}
		  String nkk = "";

	    	KelurahanModel kelurahan = kependudukanDAO.selectKelurahan(keluarga.getId_kelurahan());
			KecamatanModel kecamatan = kependudukanDAO.selectKecamatan(kelurahan.getId_kecamatan());

	        nkk += kecamatan.getKode_kecamatan().substring(0,6);

	        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
	        Date date = new Date();
	        nkk += dateFormat.format(date);
	        System.out.println("nkk dengan tanggal " + nkk);

	        List<KeluargaModel> listKeluarga = kependudukanDAO.selectKeluargaByKecamatan(kecamatan.getId());

	        int counter = 1;
	        for (KeluargaModel klrg : listKeluarga){
	            String kel_nkk = klrg.getNomor_kk();
	            if(kel_nkk.substring(0,12).equals(nkk)){
	                counter++;
	            }
	        }

	        String nomor_urut = Integer.toString(counter);

	        if(nomor_urut.length() == 1){
	            nkk += "000";
	            nkk += nomor_urut;
	        } else if(nomor_urut.length() == 2){
	            nkk += "00";
	            nkk += nomor_urut;
	        } else if(nomor_urut.length() == 3){
	            nkk += "0";
	            nkk += nomor_urut;
	        } else if(nomor_urut.length() == 4){
	            nkk += nomor_urut;
	        }

	        keluarga.setNomor_kk(nkk);
		
    		kependudukanDAO.addKeluarga(keluarga);
    		model.addAttribute("keluarga", keluarga);
    		
		
    		return "berhasilTambahKeluarga";
    }
	
	@RequestMapping("/penduduk/ubah")
    public String ubahPenduduk (Model model) {
		model.addAttribute("title", "Mengubah Data Penduduk");
		return "ubahPenduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah", method = RequestMethod.POST)
    public String redirectUubahPenduduk (Model model, @RequestParam(value="nik", required=true) String nik) {
		return "redirect:/penduduk/ubah/" + nik;
	}
	
	@RequestMapping("/penduduk/ubah/{nik}")
    public String formUbahPenduduk (Model model, @PathVariable(value = "nik") String nik) {
		PendudukModel penduduk = kependudukanDAO.selectPenduduk(nik);
		List<KecamatanModel> kecamatans = kependudukanDAO.selectAllKecamatan();
		
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("kecamatans", kecamatans);
		
		return "formUbahPenduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String berhasilUbahPenduduk(Model model, @Valid @ModelAttribute("penduduk") PendudukModel penduduk, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<KecamatanModel> kecamatans = kependudukanDAO.selectAllKecamatan();
			
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("kecamatans", kecamatans);
			
			return "formUbahPenduduk";
		}
		
		 String nik_lama = penduduk.getNik();

	        String nik_baru = "";

	        KeluargaModel keluarga = kependudukanDAO.selectKeluarga(penduduk.getId_keluarga());
			KelurahanModel kelurahan = kependudukanDAO.selectKelurahan(keluarga.getId_kelurahan());
			KecamatanModel kecamatan = kependudukanDAO.selectKecamatan(kelurahan.getId_kecamatan());
			KotaModel kota = kependudukanDAO.selectKota(kecamatan.getId_kota());

	        nik_baru += kecamatan.getKode_kecamatan().substring(0,6);

	        if(penduduk.getJenis_kelamin() == 0){

	            String[] tanggal_lahir = penduduk.getTanggal_lahir().split("-");
	            nik_baru += tanggal_lahir[2];
	            nik_baru += tanggal_lahir[1];
	            nik_baru += tanggal_lahir[0].substring(2);

	        } else {

	            String[] tanggal_lahir = penduduk.getTanggal_lahir().split("-");
	            nik_baru += Integer.toString(Integer.parseInt(tanggal_lahir[2]) + 40);
	            nik_baru += tanggal_lahir[1];
	            nik_baru += tanggal_lahir[0].substring(2);
	        }

	        List<PendudukModel> pendudukLain = kependudukanDAO.selectSimilarPenduduk(penduduk.getTanggal_lahir(), kelurahan.getId(), kecamatan.getId(), kota.getId());

	        String nomor_urut = Integer.toString(pendudukLain.size());

	        if(nomor_urut.length() == 1){
	            nik_baru += "000";
	            nik_baru += nomor_urut;
	        } else if(nomor_urut.length() == 2){
	            nik_baru += "00";
	            nik_baru += nomor_urut;
	        } else if(nomor_urut.length() == 3){
	            nik_baru += "0";
	            nik_baru += nomor_urut;
	        } else if(nomor_urut.length() == 4){
	            nik_baru += nomor_urut;
	        }

	        penduduk.setNik(nik_baru);

	        kependudukanDAO.updatePenduduk(penduduk, nik_lama);	
		
    		
    		model.addAttribute("nik_lama", nik_lama);
		
    		return "berhasilUbahPenduduk";
    }
	
	@RequestMapping("/keluarga/ubah")
    public String ubahKeluarga (Model model) {
		model.addAttribute("title", "Mengubah Data Keluarga");
		return "ubahKeluarga";
	}
	
	@RequestMapping(value = "/keluarga/ubah", method = RequestMethod.POST)
    public String redirectUbahKeluarga (Model model, @RequestParam(value="nkk", required=true) String nkk) {
		return "redirect:/keluarga/ubah/" + nkk;
	}
	
	@RequestMapping("/keluarga/ubah/{nkk}")
	public String formUbahKeluarga(Model model, @PathVariable(value = "nkk") String nkk) {
		KeluargaModel keluarga = kependudukanDAO.selectKeluargaByNKK(nkk);
		List<KelurahanModel> kelurahans = kependudukanDAO.selectAllKelurahan();
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("kelurahans", kelurahans);

		
		return "formUbahKeluarga";
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String berhasilUbahKeluarga(Model model, @Valid @ModelAttribute("keluarga") KeluargaModel keluarga, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			List<KelurahanModel> kelurahans = kependudukanDAO.selectAllKelurahan();
			
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahans", kelurahans);
			
			return "formUbahKeluarga";
		}
		 String nkk_lama = keluarga.getNomor_kk();

	        String nkk_baru = "";

	    	KelurahanModel kelurahan = kependudukanDAO.selectKelurahan(keluarga.getId_kelurahan());
			KecamatanModel kecamatan = kependudukanDAO.selectKecamatan(kelurahan.getId_kecamatan());

	        nkk_baru += kecamatan.getKode_kecamatan().substring(0,6);

	        nkk_baru += nkk_lama.substring(6,12);

	        List<KeluargaModel> listKeluarga = kependudukanDAO.selectKeluargaByKecamatan(kecamatan.getId());

	        int counter = 0;
	        for (KeluargaModel klrg : listKeluarga){
	            String kel_nkk = klrg.getNomor_kk();
	            if(kel_nkk.substring(0,12).equals(nkk_baru)){
	                counter++;
	            }
	        }

	        String nomor_urut = Integer.toString(counter);

	        if(nomor_urut.length() == 1){
	            nkk_baru += "000";
	            nkk_baru += nomor_urut;
	        } else if(nomor_urut.length() == 2){
	            nkk_baru += "00";
	            nkk_baru += nomor_urut;
	        } else if(nomor_urut.length() == 3){
	            nkk_baru += "0";
	            nkk_baru += nomor_urut;
	        } else if(nomor_urut.length() == 4){
	            nkk_baru += nomor_urut;
	        }

	        keluarga.setNomor_kk(nkk_baru);

	        kependudukanDAO.updateKeluarga(keluarga, nkk_lama);
	        model.addAttribute("nkk_lama", nkk_lama);
		
    		return "berhasilUbahKeluarga";
    }
}
