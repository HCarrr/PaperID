// KuitansiPembelian.java
package com.example.inventory.transaction;

import java.util.Date;

public class KuitansiPembelian extends Kuitansi {
    public KuitansiPembelian(String idTransaksi, Date tanggalTransaksi, double total,
                             InvoicePembelian invoiceReferensi, String metodePembayaran, Date tanggalBayar) {
        super(idTransaksi, tanggalTransaksi, total, invoiceReferensi, metodePembayaran, tanggalBayar);
    }

    @Override
    public void verifikasiPembayaran() {
        System.out.println("[Kuitansi Pembelian] Verifikasi pembayaran Kuitansi Pembelian ID: " + idTransaksi);
        if (paperNetwork != null) {
            paperNetwork.validasiPaper();
        } else {
            System.out.println("[Kuitansi Pembelian] Tidak ada PaperNetwork terkait untuk validasi.");
        }
        System.out.println("[Kuitansi Pembelian] Pembayaran untuk Kuitansi Pembelian ID: " + idTransaksi + " diverifikasi.");
    }

    @Override
    public String getDetailTransaksi() {
        return "Kuitansi Pembelian ID: " + idTransaksi + ", Total: " + String.format("%.2f", total) + ", Metode: " + metodePembayaran + ", Tanggal Bayar: " + tanggalBayar;
    }
}