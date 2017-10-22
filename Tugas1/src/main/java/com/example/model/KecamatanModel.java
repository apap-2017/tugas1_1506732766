package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	private int id;
	private String kode_kecamatan;
	private int id_kota;
	private String nama_kecamatan;
}
