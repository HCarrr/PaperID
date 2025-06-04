// Kuitansi.java
package com.example.inventory.transaction;

import com.example.inventory.model.PaperNetwork;
import java.util.Date;

public abstract class Kuitansi extends DokumenTransaksi {
    protected DokumenTransaksi invoiceReferensi;
    protected String metodePembayaran;
    protected Date tanggalBayar;
    protected PaperNetwork paperNetwork;

    public Kuitansi(String idTransaksi, Date tanggalTransaksi, double total,
                    DokumenTransaksi invoiceReferensi, String metodePembayaran, Date tanggalBayar) {
        super(idTransaksi, tanggalTransaksi, total, "Selesai");
        this.invoiceReferensi = invoiceReferensi;
        this.metodePembayaran = metodePembayaran;
        this.tanggalBayar = tanggalBayar;
    }

    public DokumenTransaksi getInvoiceReferensi() {
        return invoiceReferensi;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public Date getTanggalBayar() {
        return tanggalBayar;
    }

    public PaperNetwork getPaperNetwork() {
        return paperNetwork;
    }

    public void setPaperNetwork(PaperNetwork paperNetwork) {
        this.paperNetwork = paperNetwork;
    }

    public abstract void verifikasiPembayaran();

    @Override
    public void cetakDokumen() {
        System.out.println("--- Kuitansi ID: " + idTransaksi + " ---");
        System.out.println("Tanggal Dibuat: " + tanggalTransaksi);
        System.out.println("Total: " + String.format("%.2f", total));
        System.out.println("Metode Pembayaran: " + metodePembayaran);
        System.out.println("Tanggal Bayar: " + tanggalBayar);
        if (invoiceReferensi != null) {
            System.out.println("Referensi Invoice: " + invoiceReferensi.getIdTransaksi());
        }
        System.out.println("Status: " + status);
        System.out.println("-----------------------------------");
    }
}