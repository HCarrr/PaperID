// KuitansiPenjualan.java
package com.example.inventory.transaction;

import java.util.Date;

public class KuitansiPenjualan extends Kuitansi {
    public KuitansiPenjualan(String idTransaksi, Date tanggalTransaksi, double total,
                             InvoicePenjualan invoiceReferensi, String metodePembayaran, Date tanggalBayar) {
        super(idTransaksi, tanggalTransaksi, total, invoiceReferensi, metodePembayaran, tanggalBayar);
    }

    @Override
    public void verifikasiPembayaran() {
        System.out.println("[Kuitansi Penjualan] Verifikasi pembayaran Kuitansi Penjualan ID: " + idTransaksi);
        if (paperNetwork != null) {
            paperNetwork.validasiPaper();
        } else {
            System.out.println("[Kuitansi Penjualan] Tidak ada PaperNetwork terkait untuk validasi.");
        }
        System.out.println("[Kuitansi Penjualan] Pembayaran untuk Kuitansi Penjualan ID: " + idTransaksi + " diverifikasi.");
    }

    @Override
    public String getDetailTransaksi() {
        return "Kuitansi Penjualan ID: " + idTransaksi + ", Total: " + String.format("%.2f", total) + ", Metode: " + metodePembayaran + ", Tanggal Bayar: " + tanggalBayar;
    }
}