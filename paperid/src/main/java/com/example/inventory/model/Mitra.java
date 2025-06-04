// Mitra.java
package com.example.inventory.model;

public class Mitra {
    private String idMitra;
    private String nama;
    private String alamat;
    private String kontak;

    public Mitra(String idMitra, String nama, String alamat, String kontak) {
        this.idMitra = idMitra;
        this.nama = nama;
        this.alamat = alamat;
        this.kontak = kontak;
    }

    public String getIdMitra() {
        return idMitra;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKontak() {
        return kontak;
    }

    public String getDetailMitra() {
        return "ID: " + idMitra + ", Nama: " + nama + ", Alamat: " + alamat + ", Kontak: " + kontak;
    }
}