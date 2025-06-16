// Produk.java
package com.example.inventory.model;

import java.util.HashSet;
import java.util.Set;

public class Produk {
    private static final Set<String> namaProdukSet = new HashSet<>();
    private String idProduk;
    private String nama;
    private double hargaJual;
    private double hargaBeli;
    private String deskripsi;

    public Produk(String idProduk, String nama, double hargaJual, double hargaBeli, String deskripsi) {
        // Validasi nama tidak boleh duplikat
        if (namaProdukSet.contains(nama)) {
            throw new IllegalArgumentException("Nama produk sudah digunakan.");
        }
        // Validasi nama tidak boleh mengandung karakter spesial
        if (!nama.matches("[a-zA-Z0-9 ]+")) {
            throw new IllegalArgumentException("Nama produk tidak boleh mengandung karakter spesial.");
        }
        // Validasi harga jual tidak boleh lebih murah dari harga beli
        if (hargaJual < hargaBeli) {
            throw new IllegalArgumentException("Harga jual tidak boleh lebih murah dari harga beli.");
        }
        this.idProduk = idProduk;
        this.nama = nama;
        this.hargaJual = hargaJual;
        this.hargaBeli = hargaBeli;
        this.deskripsi = deskripsi;
        namaProdukSet.add(nama);
    }

    public String getIdProduk() {
        return idProduk;
    }

    public String getNama() {
        return nama;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getDetailProduk() {
        return "ID: " + idProduk + ", Nama: " + nama + ", Harga Jual: " + String.format("%.2f", hargaJual) + ", Harga Beli: " + String.format("%.2f", hargaBeli);
    }
}