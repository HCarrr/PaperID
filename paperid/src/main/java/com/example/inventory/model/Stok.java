// Stok.java
package com.example.inventory.model;

public class Stok {
    private String idStok;
    private int jumlah;
    private String lokasi;

    public Stok(String idStok, int jumlah, String lokasi) {
        this.idStok = idStok;
        this.jumlah = jumlah;
        this.lokasi = lokasi;
    }

    public String getIdStok() {
        return idStok;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void tambahStok(int jumlah) {
        if (jumlah < 0) {
            System.out.println("[Stok] Tidak bisa menambah stok dengan nilai negatif.");
            return;
        }
        this.jumlah += jumlah;
        System.out.println("[Stok] Stok " + idStok + " bertambah menjadi: " + this.jumlah + " di " + this.lokasi);
    }

    public void kurangStok(int jumlah) {
        if (jumlah < 0) {
            System.out.println("[Stok] Tidak bisa mengurangi stok dengan nilai negatif.");
            return;
        }
        if (this.jumlah >= jumlah) {
            this.jumlah -= jumlah;
            System.out.println("[Stok] Stok " + idStok + " berkurang menjadi: " + this.jumlah + " di " + this.lokasi);
        } else {
            System.out.println("[Stok] Gagal mengurangi stok " + idStok + ": Jumlah tidak cukup (" + this.jumlah + " tersedia).");
        }
    }
}