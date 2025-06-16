// User.java
package com.example.inventory.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID; // Untuk menghasilkan userId unik

public class User {
    private String userId;
    private String username;
    private String password; // Simpan password secara plain (hanya simulasi)
    private String email;
    private boolean isLoggedIn;

    // Simulasi database/penyimpanan untuk user
    // Dalam aplikasi nyata, ini adalah koneksi database atau repository
    private static final Map<String, User> usersDb = new HashMap<>(); // username -> Objek User

    public User(String userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isLoggedIn = false; // Status default saat objek dibuat
        usersDb.put(username, this); // Tambahkan ke "DB" simulasi
        System.out.println("[User] User '" + username + "' created (ID: " + userId + ").");
    }

    public static User register(String username, String plainPassword, String email) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("[User] Registrasi gagal: Username tidak boleh kosong.");
            return null;
        }
        if (usersDb.containsKey(username)) {
            System.out.println("[User] Registrasi gagal: Username '" + username + "' sudah ada.");
            return null;
        }
        if (plainPassword == null || plainPassword.isEmpty()) {
            System.out.println("[User] Registrasi gagal: Password tidak boleh kosong.");
            return null;
        }
        if (email == null || email.trim().isEmpty()) {
            System.out.println("[User] Registrasi gagal: Email tidak boleh kosong.");
            return null;
        }
        if (!email.contains("@")) {
            System.out.println("[User] Registrasi gagal: Email harus mengandung '@'.");
            return null;
        }
        String newUserId = UUID.randomUUID().toString();
        // Tidak hash password, simpan langsung
        System.out.println("[User] User '" + username + "' berhasil didaftarkan.");
        return new User(newUserId, username, plainPassword, email);
    }

    public static User authenticate(String username, String plainPassword) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("[User] Otentikasi gagal: Username tidak boleh kosong.");
            return null;
        }
        if (plainPassword == null || plainPassword.isEmpty()) {
            System.out.println("[User] Otentikasi gagal: Password tidak boleh kosong.");
            return null;
        }
        User user = usersDb.get(username);
        if (user == null) {
            System.out.println("[User] Otentikasi gagal: User '" + username + "' tidak ditemukan.");
            return null;
        }

        if (user.verifyPassword(plainPassword)) {
            user.setLoggedIn(true);
            System.out.println("[User] User '" + username + "' berhasil login.");
            return user;
        } else {
            System.out.println("[User] Otentikasi gagal: Password salah untuk user '" + username + "'.");
            return null;
        }
    }

    public boolean changePassword(String oldPlainPassword, String newPlainPassword) {
        if (!verifyPassword(oldPlainPassword)) {
            System.out.println("[User] Gagal mengubah password: Password lama salah.");
            return false;
        }
        if (newPlainPassword == null || newPlainPassword.isEmpty()) {
            System.out.println("[User] Gagal mengubah password: Password baru tidak boleh kosong.");
            return false;
        }
        this.password = newPlainPassword;
        System.out.println("[User] Password untuk user '" + this.username + "' berhasil diubah.");
        return true;
    }

    // Tidak perlu hashPassword lagi

    private boolean verifyPassword(String plainPassword) {
        return this.password.equals(plainPassword);
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void logout() {
        this.isLoggedIn = false;
        System.out.println("[User] User '" + this.username + "' berhasil logout.");
    }
}