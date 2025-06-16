// MainApp.java
package com.dpbo.paperid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.example.inventory.auth.User;
import com.example.inventory.model.Mitra;
import com.example.inventory.model.PaperNetwork;
import com.example.inventory.model.Produk;
import com.example.inventory.model.Stok;
import com.example.inventory.transaction.InvoicePembelian;
import com.example.inventory.transaction.InvoicePenjualan;
import com.example.inventory.transaction.KuitansiPembelian;
import com.example.inventory.transaction.KuitansiPenjualan;
import com.example.inventory.transaction.OrderPembelian;
import com.example.inventory.transaction.OrderPenjualan;
import com.example.inventory.transaction.ReportGenerator;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("===== Sistem Inventori & Transaksi =====");

        // === DATA AWAL (DUMMY DATA) ===
        ArrayList<Produk> daftarProduk = new ArrayList<>();
        ArrayList<Stok> daftarStok = new ArrayList<>();
        ArrayList<Mitra> daftarMitra = new ArrayList<>();
        ArrayList<User> daftarUser = new ArrayList<>();
        ArrayList<com.example.inventory.transaction.OrderPenjualan> daftarOrderPenjualan = new ArrayList<>();
        ArrayList<com.example.inventory.transaction.InvoicePembelian> daftarInvoicePembelian = new ArrayList<>();
        ArrayList<com.example.inventory.transaction.DokumenTransaksi> daftarDokumenTransaksi = new ArrayList<>();

        // Mitra awal
        Mitra supplierA = new Mitra("MIT001", "PT. Teknologi Jaya", "Jl. Merdeka No. 10", "0812-3456-7890");
        Mitra pelangganB = new Mitra("MIT002", "Budi Santoso", "Jl. Maju Mundur No. 5", "0876-5432-1098");
        daftarMitra.add(supplierA);
        daftarMitra.add(pelangganB);

        // User awal
        User userAlice = User.register("alice.smith", "alicePass123", "alice@example.com");
        User userBob = User.register("bob.johnson", "bobPass123", "bob@example.com");
        if (userAlice != null) daftarUser.add(userAlice);
        if (userBob != null) daftarUser.add(userBob);

        // === TAMPILKAN DATA AWAL ===
        System.out.println("\n--- Data Produk Awal ---");
        for (Produk p : daftarProduk) {
            System.out.println(p.getDetailProduk());
        }
        System.out.println("\n--- Data Stok Awal ---");
        for (Stok s : daftarStok) {
            System.out.println("ID: " + s.getIdStok() + ", Jumlah: " + s.getJumlah() + ", Lokasi: " + s.getLokasi());
        }
        System.out.println("\n--- Data Mitra Awal ---");
        for (Mitra m : daftarMitra) {
            System.out.println(m.getDetailMitra());
        }
        System.out.println("\n--- Data User Awal ---");
        for (User u : daftarUser) {
            System.out.println("Username: " + u.getUsername() + ", Email: " + u.getEmail());
        }

        // --- Manajemen Pengguna ---
        System.out.println("\n--- Manajemen Pengguna ---");

        // Registrasi pengguna baru
        User.register("alice.smith", "alicePass123", "alice@example.com");
        User.register("bob.johnson", "bobPass123", "bob@example.com");
        User.register("alice.smith", "anotherPass", "test@example.com"); // Akan gagal: username sudah ada

        // Login pengguna
        User loggedInUser = User.authenticate("alice.smith", "alicePass123");
        if (loggedInUser != null) {
            System.out.println("Halo, " + loggedInUser.getUsername() + "!");
            loggedInUser.changePassword("alicePass123", "newAlicePass456");
            loggedInUser.logout();
        }

        User failedLogin = User.authenticate("bob.johnson", "wrongPass");
        User loggedInBob = User.authenticate("bob.johnson", "bobPass123");
        if (loggedInBob != null) {
            System.out.println("Halo, " + loggedInBob.getUsername() + "!");
        }

        System.out.println("\n--- Manajemen Inventori & Transaksi ---");

        // 1. Inisialisasi Produk dan Stok
        Produk produkLaptop = new Produk("PRD001", "Laptop Gaming", 15000000.0, 12000000.0, "Laptop canggih untuk gaming.");
        Stok produkStokLaptop = new Stok("STK001", 10, "Gudang Utama");
        System.out.println(produkLaptop.getDetailProduk());
        System.out.println("Stok Laptop saat ini: " + produkStokLaptop.getJumlah());
        produkStokLaptop.tambahStok(5);

        Produk produkKeyboard = new Produk("PRD002", "Keyboard Mekanik", 1200000.0, 900000.0, "Keyboard gaming RGB.");
        Stok produkStokKeyboard = new Stok("STK002", 20, "Gudang Cabang A");
        System.out.println(produkKeyboard.getDetailProduk());
        System.out.println("Stok Keyboard saat ini: " + produkStokKeyboard.getJumlah());
        produkStokKeyboard.kurangStok(5);

        // 2. Inisialisasi Mitra (Supplier & Pelanggan)
        Mitra mitraSupplierA = new Mitra("MIT001", "PT. Teknologi Jaya", "Jl. Merdeka No. 10", "0812-3456-7890");
        Mitra mitraPelangganB = new Mitra("MIT002", "Budi Santoso", "Jl. Maju Mundur No. 5", "0876-5432-1098");
        System.out.println("\n" + mitraSupplierA.getDetailMitra());
        System.out.println(mitraPelangganB.getDetailMitra());

        // 3. Membuat Order Pembelian
        System.out.println("\n--- Proses Order Pembelian ---");
        OrderPembelian orderBeli1 = new OrderPembelian("OP001", new Date(), mitraSupplierA);
        orderBeli1.tambahProduk(produkLaptop, 3);
        orderBeli1.tambahProduk(produkKeyboard, 2);
        orderBeli1.cetakDokumen();
        System.out.println("Detail Order Pembelian: " + orderBeli1.getDetailTransaksi());

        // 4. Membuat Invoice Pembelian dari Order Pembelian
        System.out.println("\n--- Proses Invoice Pembelian ---");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date jatuhTempoInvBeli = cal.getTime();

        InvoicePembelian invBeli1 = new InvoicePembelian(
                "INVBP001",
                new Date(),
                orderBeli1.getTotalOrder(),
                orderBeli1,
                jatuhTempoInvBeli
        );
        invBeli1.generateInvoice();
        invBeli1.cetakDokumen();

        // 5. Memproses Pembayaran Invoice Pembelian
        System.out.println("\n--- Proses Pembayaran Invoice Pembelian ---");
        if (invBeli1.prosesPembayaran()) {
            KuitansiPembelian kuitansiBeli1 = (KuitansiPembelian) invBeli1.generateKuitansi();
            PaperNetwork pnKuitansiBeli = new PaperNetwork("PNKB001", "Kuitansi Pembelian", false);
            kuitansiBeli1.setPaperNetwork(pnKuitansiBeli);
            kuitansiBeli1.verifikasiPembayaran();
            kuitansiBeli1.cetakDokumen();
            System.out.println("Detail Kuitansi Pembelian: " + kuitansiBeli1.getDetailTransaksi());
        }

        // --- Order Penjualan ---
        System.out.println("\n--- Proses Order Penjualan ---");
        OrderPenjualan orderJual1 = new OrderPenjualan("OJ001", new Date(), mitraPelangganB);
        orderJual1.tambahProduk(produkLaptop, 1);
        orderJual1.tambahProduk(produkKeyboard, 2);
        orderJual1.cetakDokumen();
        System.out.println("Detail Order Penjualan: " + orderJual1.getDetailTransaksi());
        daftarOrderPenjualan.add(orderJual1);
        daftarDokumenTransaksi.add(orderJual1);

        // Update stok setelah order penjualan
        produkStokLaptop.kurangStok(1);
        produkStokKeyboard.kurangStok(2);

        // 6. Membuat Invoice Penjualan dari Order Penjualan
        System.out.println("\n--- Proses Invoice Penjualan ---");
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3);
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

        // =========================
        // === Versi Input Manual ===
        // =========================
        System.out.println("\n===== Input Manual (CLI) =====");
        Scanner scanner = new Scanner(System.in);

        // Data untuk menu
        ArrayList<Produk> produkMenu = new ArrayList<>();
        ArrayList<Stok> stokMenu = new ArrayList<>();
        ArrayList<Mitra> mitraMenu = new ArrayList<>();
        ArrayList<User> userMenu = new ArrayList<>();
        ArrayList<com.example.inventory.transaction.OrderPenjualan> orderPenjualanMenu = new ArrayList<>();
        ArrayList<com.example.inventory.transaction.InvoicePembelian> invoicePembelianMenu = new ArrayList<>();
        ArrayList<com.example.inventory.transaction.DokumenTransaksi> dokumenTransaksiMenu = new ArrayList<>();
        ReportGenerator reportGenMenu = new ReportGenerator("RPTCLI", "CLI");

        // Tambahkan deklarasi variabel userLoginMenu di sini
        User userLoginMenu = null;

        boolean running = true;
        while (running) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Registrasi Pengguna Baru");
            System.out.println("2. Login Pengguna");
            System.out.println("3. Input Produk & Stok Baru");
            System.out.println("4. Input Mitra Baru");
            System.out.println("5. Buat Order Pembelian");
            // Tambahkan menu order penjualan
            System.out.println("6. Buat Order Penjualan");
            System.out.println("7. Buat Invoice Pembelian & Proses Pembayaran");
            System.out.println("8. Lihat Laporan (Report Generator)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            String pilihan = scanner.nextLine();

            try {
                switch (pilihan) {
                    case "1":
                        // Registrasi Pengguna Baru
                        boolean regSuccess = false;
                        while (!regSuccess) {
                            System.out.println("\n--- Registrasi Pengguna Baru ---");
                            System.out.print("Masukkan username: ");
                            String username = scanner.nextLine();
                            System.out.print("Masukkan password: ");
                            String password = scanner.nextLine();
                            System.out.print("Masukkan email: ");
                            String email = scanner.nextLine();

                            if (!email.contains("@")) {
                                System.out.println("Registrasi gagal. Email harus mengandung '@'.");
                                continue;
                            }

                            User userBaru = User.register(username, password, email);
                            if (userBaru != null) {
                                System.out.println("Registrasi berhasil untuk " + userBaru.getUsername());
                                userMenu.add(userBaru);
                                regSuccess = true;
                            } else {
                                System.out.println("Registrasi gagal. Silakan coba lagi.");
                            }
                        }
                        break;
                    case "2":
                        // Login Pengguna
                        boolean loginSuccess = false;
                        while (!loginSuccess) {
                            System.out.println("\n--- Login Pengguna ---");
                            System.out.print("Masukkan username: ");
                            String loginUser = scanner.nextLine();
                            System.out.print("Masukkan password: ");
                            String loginPass = scanner.nextLine();

                            userLoginMenu = User.authenticate(loginUser, loginPass);
                            if (userLoginMenu != null) {
                                System.out.println("Login berhasil. Selamat datang, " + userLoginMenu.getUsername() + "!");
                                loginSuccess = true;
                            } else {
                                System.out.println("Login gagal. Username atau password salah. Coba lagi? (y/n): ");
                                String ulang = scanner.nextLine();
                                if (!ulang.equalsIgnoreCase("y")) {
                                    break;
                                }
                            }
                        }
                        break;
                    case "3":
                        // Input Produk & Stok Baru
                        System.out.println("\n--- Input Produk Baru ---");
                        System.out.print("Kode Produk: ");
                        String kodeProduk = scanner.nextLine();
                        System.out.print("Nama Produk: ");
                        String namaProduk = scanner.nextLine();
                        System.out.print("Harga Jual: ");
                        double hargaJual = Double.parseDouble(scanner.nextLine());
                        System.out.print("Harga Beli: ");
                        double hargaBeli = Double.parseDouble(scanner.nextLine());
                        System.out.print("Deskripsi: ");
                        String deskripsi = scanner.nextLine();

                        Produk produkBaru = new Produk(kodeProduk, namaProduk, hargaJual, hargaBeli, deskripsi);

                        System.out.print("Kode Stok: ");
                        String kodeStok = scanner.nextLine();
                        int jumlahStok = 0;
                        while (true) {
                            System.out.print("Jumlah Stok: ");
                            String jumlahStokStr = scanner.nextLine();
                            try {
                                jumlahStok = Integer.parseInt(jumlahStokStr);
                                break;
                            } catch (NumberFormatException nfe) {
                                System.out.println("Jumlah stok harus berupa angka.");
                            }
                        }
                        String lokasiStok = "";
                        System.out.print("Lokasi Stok: ");
                        lokasiStok = scanner.nextLine();

                        Stok stokBaru = new Stok(kodeStok, jumlahStok, lokasiStok);

                        produkMenu.add(produkBaru);
                        stokMenu.add(stokBaru);

                        System.out.println("Produk berhasil ditambahkan:");
                        System.out.println(produkBaru.getDetailProduk());
                        System.out.println("Stok saat ini: " + stokBaru.getJumlah());
                        break;
                    case "4":
                        // Input Mitra Baru
                        System.out.println("\n--- Input Mitra ---");
                        System.out.print("Kode Mitra: ");
                        String kodeMitra = scanner.nextLine();
                        System.out.print("Nama Mitra: ");
                        String namaMitra = scanner.nextLine();
                        System.out.print("Alamat: ");
                        String alamatMitra = scanner.nextLine();
                        String telpMitra = "";
                        boolean validTelp = false;
                        while (!validTelp) {
                            System.out.print("No. Telepon: ");
                            telpMitra = scanner.nextLine();
                            if (telpMitra.matches("\\d+")) {
                                validTelp = true;
                            } else {
                                System.out.println("Nomor telepon harus berupa angka saja.");
                            }
                        }

                        Mitra mitraBaru = new Mitra(kodeMitra, namaMitra, alamatMitra, telpMitra);
                        mitraMenu.add(mitraBaru);
                        System.out.println("Mitra berhasil ditambahkan:");
                        System.out.println(mitraBaru.getDetailMitra());
                        break;
                    case "5":
                        // Buat Order Pembelian
                        if (produkMenu.isEmpty() || mitraMenu.isEmpty()) {
                            System.out.println("Produk dan Mitra harus sudah diinput terlebih dahulu.");
                            break;
                        }
                        System.out.println("\n--- Buat Order Pembelian ---");
                        System.out.print("Kode Order Pembelian: ");
                        String kodeOrder = scanner.nextLine();

                        // Pilih mitra
                        System.out.println("Daftar Mitra:");
                        for (int i = 0; i < mitraMenu.size(); i++) {
                            Mitra m = mitraMenu.get(i);
                            System.out.println((i+1) + ". [ID: " + m.getIdMitra() + "] " + m.getNama());
                        }
                        Mitra mitraDipilih = null;
                        while (mitraDipilih == null) {
                            System.out.print("Pilih nomor mitra atau masukkan ID mitra: ");
                            String inputMitra = scanner.nextLine();
                            // Cek apakah input berupa angka (nomor urut)
                            try {
                                int idxMitra = Integer.parseInt(inputMitra) - 1;
                                if (idxMitra >= 0 && idxMitra < mitraMenu.size()) {
                                    mitraDipilih = mitraMenu.get(idxMitra);
                                    break;
                                }
                            } catch (NumberFormatException nfe) {
                                // Bukan angka, cek berdasarkan ID mitra
                                for (Mitra m : mitraMenu) {
                                    if (m.getIdMitra().equals(inputMitra)) {
                                        mitraDipilih = m;
                                        break;
                                    }
                                }
                                if (mitraDipilih != null) break;
                            }
                            System.out.println("Pilihan tidak tersedia.");
                        }

                        OrderPembelian orderPembelian = new OrderPembelian(kodeOrder, new Date(), mitraDipilih);

                        // Pilih produk
                        System.out.println("Daftar Produk:");
                        for (int i = 0; i < produkMenu.size(); i++) {
                            System.out.println((i+1) + ". " + produkMenu.get(i).getDetailProduk());
                        }
                        int idxProduk = -1;
                        while (true) {
                            System.out.print("Pilih nomor produk: ");
                            try {
                                idxProduk = Integer.parseInt(scanner.nextLine()) - 1;
                                if (idxProduk >= 0 && idxProduk < produkMenu.size()) break;
                                else System.out.println("Pilihan tidak tersedia.");
                            } catch (NumberFormatException nfe) {
                                System.out.println("Input angka tidak valid.");
                            }
                        }
                        Produk produkDipilih = produkMenu.get(idxProduk);

                        int jumlahBeli = 0;
                        while (true) {
                            System.out.print("Jumlah produk yang ingin dibeli: ");
                            try {
                                jumlahBeli = Integer.parseInt(scanner.nextLine());
                                if (jumlahBeli > 0) break;
                                else System.out.println("Jumlah harus lebih dari 0.");
                            } catch (NumberFormatException nfe) {
                                System.out.println("Input angka tidak valid.");
                            }
                        }
                        orderPembelian.tambahProduk(produkDipilih, jumlahBeli);

                        orderPembelian.cetakDokumen();
                        System.out.println("Total Order: " + orderPembelian.getTotalOrder());

                        // Tidak perlu assignment ke produkBaru/mitraBaru di sini
                        // Tidak perlu variabel lastOrderPembelian jika tidak digunakan di menu lain
                        break;
                    case "6":
                        // Buat Order Penjualan
                        if (produkMenu.isEmpty() || mitraMenu.isEmpty()) {
                            System.out.println("Produk dan Mitra harus sudah diinput terlebih dahulu.");
                            break;
                        }
                        System.out.println("\n--- Buat Order Penjualan ---");
                        System.out.print("Kode Order Penjualan: ");
                        String kodeOrderJual = scanner.nextLine();

                        // Pilih mitra (pelanggan)
                        System.out.println("Daftar Mitra (Pelanggan):");
                        for (int i = 0; i < mitraMenu.size(); i++) {
                            Mitra m = mitraMenu.get(i);
                            System.out.println((i+1) + ". [ID: " + m.getIdMitra() + "] " + m.getNama());
                        }
                        Mitra pelangganDipilih = null;
                        while (pelangganDipilih == null) {
                            System.out.print("Pilih nomor mitra atau masukkan ID mitra: ");
                            String inputMitra = scanner.nextLine();
                            try {
                                int idxMitra = Integer.parseInt(inputMitra) - 1;
                                if (idxMitra >= 0 && idxMitra < mitraMenu.size()) {
                                    pelangganDipilih = mitraMenu.get(idxMitra);
                                    break;
                                }
                            } catch (NumberFormatException nfe) {
                                for (Mitra m : mitraMenu) {
                                    if (m.getIdMitra().equals(inputMitra)) {
                                        pelangganDipilih = m;
                                        break;
                                    }
                                }
                                if (pelangganDipilih != null) break;
                            }
                            System.out.println("Pilihan tidak tersedia.");
                        }

                        OrderPenjualan orderPenjualan = new OrderPenjualan(kodeOrderJual, new Date(), pelangganDipilih);

                        // Pilih produk
                        System.out.println("Daftar Produk:");
                        for (int i = 0; i < produkMenu.size(); i++) {
                            System.out.println((i+1) + ". " + produkMenu.get(i).getDetailProduk());
                        }
                        int idxProdukJual = -1;
                        while (true) {
                            System.out.print("Pilih nomor produk: ");
                            try {
                                idxProdukJual = Integer.parseInt(scanner.nextLine()) - 1;
                                if (idxProdukJual >= 0 && idxProdukJual < produkMenu.size()) break;
                                else System.out.println("Pilihan tidak tersedia.");
                            } catch (NumberFormatException nfe) {
                                System.out.println("Input angka tidak valid.");
                            }
                        }
                        Produk produkJualDipilih = produkMenu.get(idxProdukJual);

                        int jumlahJual = 0;
                        while (true) {
                            System.out.print("Jumlah produk yang ingin dijual: ");
                            try {
                                jumlahJual = Integer.parseInt(scanner.nextLine());
                                if (jumlahJual > 0) break;
                                else System.out.println("Jumlah harus lebih dari 0.");
                            } catch (NumberFormatException nfe) {
                                System.out.println("Input angka tidak valid.");
                            }
                        }
                        orderPenjualan.tambahProduk(produkJualDipilih, jumlahJual);

                        orderPenjualan.cetakDokumen();
                        System.out.println("Total Order Penjualan: " + orderPenjualan.getTotalOrder());

                        // Simpan ke list order penjualan dan dokumen transaksi
                        orderPenjualanMenu.add(orderPenjualan);
                        dokumenTransaksiMenu.add(orderPenjualan);
                        break;
                    case "7":
                        // Buat Invoice Pembelian & Proses Pembayaran
                        // Cek apakah sudah ada order pembelian
                        // Untuk demo, ambil order terakhir dari menu 5
                        // Jika ingin lebih baik, simpan list order
                        System.out.println("\n--- Buat Invoice Pembelian ---");
                        // Cek apakah ada order pembelian terakhir
                        // Gunakan produkBaru, mitraBaru, lastOrderPembelian dari menu 5
                        // Jika tidak ada, tampilkan pesan
                        // Untuk demo, buat order baru jika belum ada
                        OrderPembelian orderPembelianInvoice;
                        if (produkMenu.isEmpty() || mitraMenu.isEmpty()) {
                            System.out.println("Produk dan Mitra harus sudah diinput terlebih dahulu.");
                            break;
                        }
                        // Pilih mitra
                        System.out.println("Daftar Mitra:");
                        for (int i = 0; i < mitraMenu.size(); i++) {
                            Mitra m = mitraMenu.get(i);
                            System.out.println((i+1) + ". [ID: " + m.getIdMitra() + "] " + m.getNama());
                        }
                        Mitra mitraInv = null;
                        while (mitraInv == null) {
                            System.out.print("Pilih nomor mitra atau masukkan ID mitra: ");
                            String inputMitra = scanner.nextLine();
                            try {
                                int idxMitraInv = Integer.parseInt(inputMitra) - 1;
                                if (idxMitraInv >= 0 && idxMitraInv < mitraMenu.size()) {
                                    mitraInv = mitraMenu.get(idxMitraInv);
                                    break;
                                }
                            } catch (NumberFormatException nfe) {
                                for (Mitra m : mitraMenu) {
                                    if (m.getIdMitra().equals(inputMitra)) {
                                        mitraInv = m;
                                        break;
                                    }
                                }
                                if (mitraInv != null) break;
                            }
                            System.out.println("Pilihan tidak tersedia.");
                        }

                        // Pilih produk
                        System.out.println("Daftar Produk:");
                        int idxProdukInv = -1;
                        for (int i = 0; i < produkMenu.size(); i++) {
                            System.out.println((i+1) + ". " + produkMenu.get(i).getDetailProduk());
                        }
                        while (true) {
                            System.out.print("Pilih nomor produk: ");
                            try {
                                idxProdukInv = Integer.parseInt(scanner.nextLine()) - 1;
                                if (idxProdukInv >= 0 && idxProdukInv < produkMenu.size()) break;
                                else System.out.println("Pilihan tidak tersedia.");
                            } catch (NumberFormatException nfe) {
                                System.out.println("Input angka tidak valid.");
                            }
                        }
                        Produk produkInv = produkMenu.get(idxProdukInv);

                        int jumlahBeliInv = 0;
                        while (true) {
                            System.out.print("Jumlah produk yang ingin dibeli: ");
                            try {
                                jumlahBeliInv = Integer.parseInt(scanner.nextLine());
                                if (jumlahBeliInv > 0) break;
                                else System.out.println("Jumlah harus lebih dari 0.");
                            } catch (NumberFormatException nfe) {
                                System.out.println("Input angka tidak valid.");
                            }
                        }

                        orderPembelianInvoice = new OrderPembelian("OPINVCLI", new Date(), mitraInv);
                        orderPembelianInvoice.tambahProduk(produkInv, jumlahBeliInv);

                        cal.add(Calendar.DAY_OF_MONTH, 7);
                        Date jatuhTempo = cal.getTime();

                        InvoicePembelian invoicePembelian = new InvoicePembelian(
                            "INVSCN001",
                            new Date(),
                            orderPembelianInvoice.getTotalOrder(),
                            orderPembelianInvoice,
                            jatuhTempo
                        );
                        invoicePembelian.generateInvoice();
                        invoicePembelian.cetakDokumen();

                        // --- Proses Pembayaran Invoice ---
                        System.out.println("\n--- Proses Pembayaran Invoice ---");
                        if (invoicePembelian.prosesPembayaran()) {
                            KuitansiPembelian kuitansiPembelian = (KuitansiPembelian) invoicePembelian.generateKuitansi();
                            PaperNetwork pnKuitansi = new PaperNetwork("PNKSCN001", "Kuitansi Pembelian CLI", false);
                            kuitansiPembelian.setPaperNetwork(pnKuitansi);
                            kuitansiPembelian.verifikasiPembayaran();
                            kuitansiPembelian.cetakDokumen();
                            System.out.println("Detail Kuitansi: " + kuitansiPembelian.getDetailTransaksi());
                        }
                        break;
                    case "8":
                        System.out.println("\n--- Laporan Sistem (CLI) ---");
                        System.out.println(reportGenMenu.generateInventorySummary(produkMenu, stokMenu));
                        Date nowMenu = new Date();
                        Calendar calStartMenu = Calendar.getInstance();
                        calStartMenu.setTime(nowMenu);
                        calStartMenu.add(Calendar.DAY_OF_MONTH, -30);
                        System.out.println(reportGenMenu.generateTransactionSummary(dokumenTransaksiMenu, calStartMenu.getTime(), nowMenu));
                        break;
                    case "0":
                        running = false;
                        System.out.println("Keluar dari program.");
                        break;
                    default:
                        System.out.println("Menu tidak valid.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Input angka tidak valid. Silakan coba lagi.");
            } catch (IndexOutOfBoundsException ioobe) {
                System.out.println("Pilihan tidak tersedia. Silakan coba lagi.");
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
        scanner.close();
        System.out.println("\n===== Proses Selesai =====");
    }
}