// OrderPembelian.java
package com.example.inventory.transaction;

import com.example.inventory.model.Produk;
import com.example.inventory.model.Mitra;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderPembelian extends DokumenTransaksi {
    private Map<Produk, Integer> daftarProdukDibeli;
    private Mitra supplier;

    public OrderPembelian(String idTransaksi, Date tanggalTransaksi, Mitra supplier) {
        super(idTransaksi, tanggalTransaksi, "Draft");
        this.supplier = supplier;
        this.daftarProdukDibeli = new HashMap<>();
    }

    public Map<Produk, Integer> getDaftarProdukDibeli() {
        return daftarProdukDibeli;
    }

    public Mitra getSupplier() {
        return supplier;
    }

    public void tambahProduk(Produk produk, int jumlah) {
        if (jumlah <= 0) {
            System.out.println("[Order Pembelian] Jumlah produk harus lebih dari 0.");
            return;
        }
        daftarProdukDibeli.put(produk, daftarProdukDibeli.getOrDefault(produk, 0) + jumlah);
        recalculateTotal();
        System.out.println("[Order Pembelian] Menambahkan " + jumlah + "x " + produk.getNama() + " ke Order " + idTransaksi);
    }

    private void recalculateTotal() {
        double currentTotal = 0;
        for (Map.Entry<Produk, Integer> entry : daftarProdukDibeli.entrySet()) {
            currentTotal += entry.getKey().getHargaBeli() * entry.getValue();
        }
        setTotal(currentTotal);
    }

    public double getTotalOrder() {
        return this.total;
    }

    @Override
    public void cetakDokumen() {
        System.out.println("\n--- Order Pembelian ID: " + idTransaksi + " ---");
        System.out.println("Tanggal: " + tanggalTransaksi);
        System.out.println("Supplier: " + supplier.getNama());
        System.out.println("Status: " + status);
        System.out.println("Daftar Produk:");
        if (daftarProdukDibeli.isEmpty()) {
            System.out.println("  (Belum ada produk)");
        } else {
            daftarProdukDibeli.forEach((produk, jumlah) ->
                System.out.println("  - " + produk.getNama() + " (x" + jumlah + ") @ " + String.format("%.2f", produk.getHargaBeli()) + " = " + String.format("%.2f", produk.getHargaBeli() * jumlah))
            );
        }
        System.out.println("Total Order: " + String.format("%.2f", total));
        System.out.println("-----------------------------------");
    }

    @Override
    public String getDetailTransaksi() {
        return "Order Pembelian ID: " + idTransaksi + ", Total: " + String.format("%.2f", total) + ", Status: " + status + ", Supplier: " + supplier.getNama();
    }
}