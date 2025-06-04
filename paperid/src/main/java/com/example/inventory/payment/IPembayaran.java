// IPembayaran.java
package com.example.inventory.payment;

import com.example.inventory.transaction.Kuitansi; // Import Kuitansi dari paket transaction

public interface IPembayaran {
    double hitungTotal();
    boolean prosesPembayaran();
    Kuitansi generateKuitansi();
}