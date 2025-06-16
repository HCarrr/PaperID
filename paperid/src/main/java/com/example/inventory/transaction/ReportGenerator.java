package com.example.inventory.transaction;

import java.util.Date;
import java.util.List;

import com.example.inventory.model.Produk;
import com.example.inventory.model.Stok;

public class ReportGenerator {
    private String reportId;
    private String reportType;

    public ReportGenerator(String reportId, String reportType) {
        this.reportId = reportId;
        this.reportType = reportType;
    }

    public String generateInventorySummary(List<Produk> products, List<Stok> stockList) {
        StringBuilder sb = new StringBuilder("=== INVENTORY SUMMARY ===\n");
        for (int i = 0; i < products.size(); i++) {
            Produk p = products.get(i);
            Stok s = stockList.get(i);
            sb.append("Produk: ").append(p.getNama())
              .append(", Harga: ").append(p.getHargaJual())
              .append(", Stok: ").append(s.getJumlah()).append("\n");
        }
        return sb.toString();
    }

    // Jika list kosong, tampilkan dummy summary
    public String generateTransactionSummary(List<DokumenTransaksi> orders, Date startDate, Date endDate) {
        StringBuilder sb = new StringBuilder("=== TRANSACTION SUMMARY ===\n");
        int count = 0;
        for (DokumenTransaksi d : orders) {
            Date tgl = d.getTanggalTransaksi();
            if (!tgl.before(startDate) && !tgl.after(endDate)) {
                sb.append("ID: ").append(d.getIdTransaksi()).append(", Tanggal: ").append(tgl)
                  .append(", Tipe: ").append(d.getClass().getSimpleName()).append("\n");
                count++;
            }
        }
        if (count == 0) {
            sb.append("Dummy Summary: 3 transaksi dummy dalam 30 hari terakhir.\n");
            sb.append("ID: DUMMY1, Tanggal: ").append(new Date()).append(", Tipe: TransaksiUmum\n");
            sb.append("ID: DUMMY2, Tanggal: ").append(new Date()).append(", Tipe: TransaksiUmum\n");
            sb.append("ID: DUMMY3, Tanggal: ").append(new Date()).append(", Tipe: TransaksiUmum\n");
        }
        return sb.toString();
    }
}