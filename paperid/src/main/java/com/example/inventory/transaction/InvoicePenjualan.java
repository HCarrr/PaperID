// InvoicePenjualan.java
package com.example.inventory.transaction;

import com.example.inventory.payment.IPembayaran;
import java.util.Date;
import java.util.UUID;

public class InvoicePenjualan extends DokumenTransaksi implements IPembayaran {
    private OrderPenjualan orderReferensi;
    private Date jatuhTempo;

    public InvoicePenjualan(String idTransaksi, Date tanggalTransaksi, double total, OrderPenjualan orderReferensi, Date jatuhTempo) {
        super(idTransaksi, tanggalTransaksi, total, "Belum Lunas");
        this.orderReferensi = orderReferensi;
        this.jatuhTempo = jatuhTempo;
    }

    public OrderPenjualan getOrderReferensi() {
        return orderReferensi;
    }

    public Date getJatuhTempo() {
        return jatuhTempo;
    }

    public void generateInvoice() {
        System.out.println("[Invoice Penjualan] Menghasilkan Invoice Penjualan ID: " + idTransaksi);
    }

    @Override
    public double hitungTotal() {
        return this.total;
    }

    @Override
    public boolean prosesPembayaran() {
        System.out.println("[Invoice Penjualan] Memproses pembayaran untuk Invoice Penjualan ID: " + idTransaksi + " sejumlah: " + String.format("%.2f", total));
        this.setStatus("Lunas");
        System.out.println("[Invoice Penjualan] Pembayaran Invoice Penjualan ID: " + idTransaksi + " berhasil. Status: " + this.status);
        return true;
    }

    @Override
    public KuitansiPenjualan generateKuitansi() {
        System.out.println("[Invoice Penjualan] Menghasilkan Kuitansi Penjualan untuk Invoice ID: " + idTransaksi);
        KuitansiPenjualan kuitansi = new KuitansiPenjualan(
            "KUI-" + UUID.randomUUID().toString().substring(0, 8),
            new Date(),
            this.total,
            this,
            "Kas",
            new Date()
        );
        return kuitansi;
    }

    @Override
    public void cetakDokumen() {
        System.out.println("\n--- Invoice Penjualan ID: " + idTransaksi + " ---");
        System.out.println("Tanggal Dibuat: " + tanggalTransaksi);
        System.out.println("Jatuh Tempo: " + jatuhTempo);
        System.out.println("Referensi Order: " + (orderReferensi != null ? orderReferensi.getIdTransaksi() : "N/A"));
        System.out.println("Total: " + String.format("%.2f", total));
        System.out.println("Status: " + status);
        System.out.println("-----------------------------------");
    }

    @Override
    public String getDetailTransaksi() {
        return "Invoice Penjualan ID: " + idTransaksi + ", Total: " + String.format("%.2f", total) + ", Status: " + status + ", Jatuh Tempo: " + jatuhTempo;
    }
}