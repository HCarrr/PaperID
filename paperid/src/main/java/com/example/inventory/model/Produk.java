// Produk.java
package com.example.inventory.model;

public class Produk {
    private String idProduk;
    private String nama;
    private double hargaJual;
    private double hargaBeli;
    private String deskripsi;

    public Produk(String idProduk, String nama, double hargaJual, double hargaBeli, String deskripsi) {
        this.idProduk = idProduk;
        this.nama = nama;
        this.hargaJual = hargaJual;
        this.hargaBeli = hargaBeli;
        this.deskripsi = deskripsi;
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