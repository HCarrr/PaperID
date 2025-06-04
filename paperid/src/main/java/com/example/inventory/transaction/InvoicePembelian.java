// InvoicePembelian.java
package com.example.inventory.transaction;

import com.example.inventory.payment.IPembayaran;
import java.util.Date;
import java.util.UUID; // Untuk generate ID kuitansi

public class InvoicePembelian extends DokumenTransaksi implements IPembayaran {
    private OrderPembelian orderReferensi;
    private Date jatuhTempo;

    public InvoicePembelian(String idTransaksi, Date tanggalTransaksi, double total, OrderPembelian orderReferensi, Date jatuhTempo) {
        super(idTransaksi, tanggalTransaksi, total, "Belum Lunas");
        this.orderReferensi = orderReferensi;
        this.jatuhTempo = jatuhTempo;
    }

    public OrderPembelian getOrderReferensi() {
        return orderReferensi;
    }

    public Date getJatuhTempo() {
        return jatuhTempo;
    }

    public void generateInvoice() {
        System.out.println("[Invoice Pembelian] Menghasilkan Invoice Pembelian ID: " + idTransaksi);
    }

    @Override
    public double hitungTotal() {
        return this.total;
    }

    @Override
    public boolean prosesPembayaran() {
        System.out.println("[Invoice Pembelian] Memproses pembayaran untuk Invoice Pembelian ID: " + idTransaksi + " sejumlah: " + String.format("%.2f", total));
        this.setStatus("Lunas");
        System.out.println("[Invoice Pembelian] Pembayaran Invoice Pembelian ID: " + idTransaksi + " berhasil. Status: " + this.status);
        return true;
    }

    @Override
    public KuitansiPembelian generateKuitansi() {
        System.out.println("[Invoice Pembelian] Menghasilkan Kuitansi Pembelian untuk Invoice ID: " + idTransaksi);
        KuitansiPembelian kuitansi = new KuitansiPembelian(
            "KUI-" + UUID.randomUUID().toString().substring(0, 8),
            new Date(),
            this.total,
            this,
            "Transfer Bank",
            new Date()
        );
        return kuitansi;
    }

    @Override
    public void cetakDokumen() {
        System.out.println("\n--- Invoice Pembelian ID: " + idTransaksi + " ---");
        System.out.println("Tanggal Dibuat: " + tanggalTransaksi);
        System.out.println("Jatuh Tempo: " + jatuhTempo);
        System.out.println("Referensi Order: " + (orderReferensi != null ? orderReferensi.getIdTransaksi() : "N/A"));
        System.out.println("Total: " + String.format("%.2f", total));
        System.out.println("Status: " + status);
        System.out.println("-----------------------------------");
    }

    @Override
    public String getDetailTransaksi() {
        return "Invoice Pembelian ID: " + idTransaksi + ", Total: " + String.format("%.2f", total) + ", Status: " + status + ", Jatuh Tempo: " + jatuhTempo;
    }
}