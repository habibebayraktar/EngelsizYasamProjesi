package com.nexis.proje;

public class Kullanici {

    public String kullaniciIsmi;
    public String kullaniciSoyadi;
    public String kullaniciEmail;
    public String kullaniciSifre;
    public String kullaniciId;
    public String kullaniciImg;

    public Kullanici( String id, String mail, String sifre, String isim, String soyad, String profil) {

        this.kullaniciIsmi = isim;
        this.kullaniciSoyadi = soyad;
        this.kullaniciEmail = mail;
        this.kullaniciSifre = sifre;
        this.kullaniciId = id;
        this.kullaniciImg = profil;
    }

    public Kullanici() {

    }


    public String getKullaniciIsmi() {
        return kullaniciIsmi;
    }

    public void setKullaniciIsmi(String kullaniciIsmi) {
        this.kullaniciIsmi = kullaniciIsmi;
    }

    public String getKullaniciEmail() {
        return kullaniciEmail;
    }

    public void setKullaniciEmail(String kullaniciEmail) {
        this.kullaniciEmail = kullaniciEmail;
    }

    public String getKullaniciId() {
        return kullaniciId;
    }

    public String getKullaniciSoyadi() {
        return kullaniciSoyadi;
    }

    public void setKullaniciSoyadi(String kullaniciSoyadi) {
        this.kullaniciSoyadi = kullaniciSoyadi;
    }

    public String getKullaniciSifre() {
        return kullaniciSifre;
    }

    public void setKullaniciSifre(String kullaniciSifre) {
        this.kullaniciSifre = kullaniciSifre;
    }

    public String getKullaniciImg() {
        return kullaniciImg;
    }

    public void setKullaniciImg(String kullaniciImg) {
        this.kullaniciImg = kullaniciImg;
    }

    public void setKullaniciId(String kullaniciId) {
        this.kullaniciId = kullaniciId;
    }
}
