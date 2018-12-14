package com.dimana.bobo.bobodimanaapp.Model;

import com.google.gson.annotations.SerializedName;

public class Kost {
    @SerializedName("id_kost")
    private String id_kost;
    @SerializedName("judul")
    private String judul;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("fasilitas")
    private String fasilitas;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("harga")
    private String harga;
    @SerializedName("jumlah_kamar")
    private String jumlah_kamar;
    @SerializedName("tipe_kost")
    private String tipe_kost;

    public Kost(String id_kost, String judul, String gambar, String deskripsi, String fasilitas, String alamat, String latitude, String longitude, String harga, String jumlah_kamar, String tipe_kost) {
        this.id_kost = id_kost;
        this.judul = judul;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.fasilitas = fasilitas;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.harga = harga;
        this.jumlah_kamar = jumlah_kamar;
        this.tipe_kost = tipe_kost;
    }

    public String getId_kost() {
        return id_kost;
    }

    public void setId_kost(String id_kost) {
        this.id_kost = id_kost;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah_kamar() {
        return jumlah_kamar;
    }

    public void setJumlah_kamar(String jumlah_kamar) {
        this.jumlah_kamar = jumlah_kamar;
    }

    public String getTipe_kost() {
        return tipe_kost;
    }

    public void setTipe_kost(String tipe_kost) {
        this.tipe_kost = tipe_kost;
    }
}
