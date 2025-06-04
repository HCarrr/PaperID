// OrderPenjualan.java
package com.example.inventory.transaction;

import com.example.inventory.model.Produk;
import com.example.inventory.model.Mitra;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderPenjualan extends DokumenTransaksi {
    private Map<Produk, Integer> daftarProdukDijual;
    private Mitra pelanggan;

    public OrderPenjualan(String idTransaksi, Date tanggalTransaksi, Mitra pelanggan) {
        super(idTransaksi, tanggalTransaksi, "Draft");
        this.pelanggan = pelanggan;
        this.daftarProdukDijual = new HashMap<>();
    }

    public Map<Produk, Integer> getDaftarProdukDijual() {
        return daftarProdukDijual;
    }

    public Mitra getPelanggan() {
        return pelanggan;
    }

    public void tambahProduk(Produk produk, int jumlah) {
        if (jumlah <= 0) {
            System.out.println("[Order Penjualan] Jumlah produk harus lebih dari 0.");
            return;
        }
        daftarProdukDijual.put(produk, daftarProdukDijual.getOrDefault(produk, 0) + jumlah);
        recalculateTotal();
        System.out.println("[Order Penjualan] Menambahkan " + jumlah + "x " + produk.getNama() + " ke Order " + idTransaksi);
    }

    private void recalculateTotal() {
        double currentTotal = 0;
        for (Map.Entry<Produk, Integer> entry : daftarProdukDijual.entrySet()) {
            currentTotal += entry.getKey().getHargaJual() * entry.getValue();
        }
        setTotal(currentTotal);
    }

    public double getTotalOrder() {
        return this.total;
    }

    @Override
    public void cetakDokumen() {
        System.out.println("\n--- Order Penjualan ID: " + idTransaksi + " ---");
        System.out.println("Tanggal: " + tanggalTransaksi);
        System.out.println("Pelanggan: " + pelanggan.getNama());
        System.out.println("Status: " + status);
        System.out.println("Daftar Produk:");
        if (daftarProdukDijual.isEmpty()) {
            System.out.println("  (Belum ada produk)");
        } else {
            daftarProdukDijual.forEach((produk, jumlah) ->
                System.out.println("  - " + produk.getNama() + " (x" + jumlah + ") @ " + String.format("%.2f", produk.getHargaJual()) + " = " + String.format("%.2f", produk.getHargaJual() * jumlah))
            );
        }
        System.out.println("Total Order: " + String.format("%.2f", total));
        System.out.println("-----------------------------------");
    }

    @Override
    public String getDetailTransaksi() {
        return "Order Penjualan ID: " + idTransaksi + ", Total: " + String.format("%.2f", total) + ", Status: " + status + ", Pelanggan: " + pelanggan.getNama();
    }
}