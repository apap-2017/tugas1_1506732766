package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private int id;
	private String nomor_kk;
	private String alamat;
	private String rt;
	private String rw;
	private int id_kelurahan;
	private int is_tidak_berlaku;
}
