// MainApp.java
package com.dpbo.paperid;

import com.example.inventory.model.*;
import com.example.inventory.transaction.*;
import com.example.inventory.payment.IPembayaran;
import com.example.inventory.auth.User;

import java.util.Date;
import java.util.Calendar; // Untuk manipulasi tanggal jatuh tempo

public class MainApp {
    public static void main(String[] args) {
        System.out.println("===== Simulasi Sistem Inventori & Transaksi =====");

        // --- Simulasi User Login/Register ---
        System.out.println("\n--- Manajemen Pengguna ---");

        // Daftarkan pengguna baru
        User userAlice = User.register("alice.smith", "alicePass123", "alice@example.com");
        User userBob = User.register("bob.johnson", "bobPass123", "bob@example.com");
        User.register("alice.smith", "anotherPass", "test@example.com"); // Akan gagal: username sudah ada

        // Coba login
        User loggedInUser = User.authenticate("alice.smith", "alicePass123");
        if (loggedInUser != null) {
            System.out.println("Halo, " + loggedInUser.getUsername() + "!");
            loggedInUser.changePassword("alicePass123", "newAlicePass456"); // Ubah password
            loggedInUser.logout();
        }

        User failedLogin = User.authenticate("bob.johnson", "wrongPass"); // Gagal login
        User loggedInBob = User.authenticate("bob.johnson", "bobPass123");
        if (loggedInBob != null) {
            System.out.println("Halo, " + loggedInBob.getUsername() + "!");
        }

        System.out.println("\n--- Manajemen Inventori & Transaksi ---");

        // 1. Inisialisasi Produk dan Stok
        Produk laptop = new Produk("PRD001", "Laptop Gaming", 15000000.0, 12000000.0, "Laptop canggih untuk gaming.");
        Stok stokLaptop = new Stok("STK001", 10, "Gudang Utama");
        System.out.println(laptop.getDetailProduk());
        System.out.println("Stok Laptop saat ini: " + stokLaptop.getJumlah());
        stokLaptop.tambahStok(5); // Stok menjadi 15

        Produk keyboard = new Produk("PRD002", "Keyboard Mekanik", 1200000.0, 900000.0, "Keyboard gaming RGB.");
        Stok stokKeyboard = new Stok("STK002", 20, "Gudang Cabang A");
        System.out.println(keyboard.getDetailProduk());
        System.out.println("Stok Keyboard saat ini: " + stokKeyboard.getJumlah());
        stokKeyboard.kurangStok(5); // Stok menjadi 15

        // 2. Inisialisasi Mitra (Supplier & Pelanggan)
        Mitra supplierA = new Mitra("MIT001", "PT. Teknologi Jaya", "Jl. Merdeka No. 10", "0812-3456-7890");
        Mitra pelangganB = new Mitra("MIT002", "Budi Santoso", "Jl. Maju Mundur No. 5", "0876-5432-1098");
        System.out.println("\n" + supplierA.getDetailMitra());
        System.out.println(pelangganB.getDetailMitra());

        // 3. Membuat Order Pembelian
        System.out.println("\n--- Proses Order Pembelian ---");
        OrderPembelian orderBeli1 = new OrderPembelian("OP001", new Date(), supplierA);
        orderBeli1.tambahProduk(laptop, 3); // Beli 3 laptop
        orderBeli1.tambahProduk(keyboard, 2); // Beli 2 keyboard
        orderBeli1.cetakDokumen();
        System.out.println("Detail Order Pembelian: " + orderBeli1.getDetailTransaksi());

        // 4. Membuat Invoice Pembelian dari Order Pembelian
        System.out.println("\n--- Proses Invoice Pembelian ---");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7); // Jatuh tempo 7 hari kemudian
        Date jatuhTempoInvBeli = cal.getTime();

        InvoicePembelian invBeli1 = new InvoicePembelian(
                "INVBP001",
                new Date(),
                orderBeli1.getTotalOrder(), // Menggunakan total dari order pembelian
                orderBeli1,
                jatuhTempoInvBeli
        );
        invBeli1.generateInvoice(); // Menghasilkan invoice
        invBeli1.cetakDokumen();

        // 5. Memproses Pembayaran Invoice Pembelian
        System.out.println("\n--- Proses Pembayaran Invoice Pembelian ---");
        if (invBeli1.prosesPembayaran()) {
            KuitansiPembelian kuitansiBeli1 = (KuitansiPembelian) invBeli1.generateKuitansi();
            PaperNetwork pnKuitansiBeli = new PaperNetwork("PNKB001", "Kuitansi Pembelian", false);
            kuitansiBeli1.setPaperNetwork(pnKuitansiBeli); // Asosiasikan kuitansi dengan PaperNetwork
            kuitansiBeli1.verifikasiPembayaran(); // Memvalidasi kuitansi di PaperNetwork
            kuitansiBeli1.cetakDokumen();
            System.out.println("Detail Kuitansi Pembelian: " + kuitansiBeli1.getDetailTransaksi());
        }

        // --- Contoh Order Penjualan ---
        System.out.println("\n--- Proses Order Penjualan ---");
        OrderPenjualan orderJual1 = new OrderPenjualan("OJ001", new Date(), pelangganB);
        orderJual1.tambahProduk(laptop, 1); // Jual 1 laptop
        orderJual1.tambahProduk(keyboard, 2); // Jual 2 keyboard
        orderJual1.cetakDokumen();
        System.out.println("Detail Order Penjualan: " + orderJual1.getDetailTransaksi());

        // Update stok setelah order penjualan (simulasi pengiriman barang)
        stokLaptop.kurangStok(1);
        stokKeyboard.kurangStok(2);

        // 6. Membuat Invoice Penjualan dari Order Penjualan
        System.out.println("\n--- Proses Invoice Penjualan ---");
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3); // Jatuh tempo 3 hari kemudian
        Date jatuhTempoInvJual = cal.getTime();

        InvoicePenjualan invJual1 = new InvoicePenjualan(
                "INVJP001",
                new Date(),
                orderJual1.getTotalOrder(),
                orderJual1,
                jatuhTempoInvJual
        );
        invJual1.generateInvoice();
        invJual1.cetakDokumen();

        // 7. Memproses Pembayaran Invoice Penjualan
        System.out.println("\n--- Proses Pembayaran Invoice Penjualan ---");
        if (invJual1.prosesPembayaran()) {
            KuitansiPenjualan kuitansiJual1 = (KuitansiPenjualan) invJual1.generateKuitansi();
            PaperNetwork pnKuitansiJual = new PaperNetwork("PNKJ001", "Kuitansi Penjualan", false);
            kuitansiJual1.setPaperNetwork(pnKuitansiJual);
            kuitansiJual1.verifikasiPembayaran();
            kuitansiJual1.cetakDokumen();
            System.out.println("Detail Kuitansi Penjualan: " + kuitansiJual1.getDetailTransaksi());
        }

        System.out.println("\n===== Simulasi Selesai =====");
    }
}