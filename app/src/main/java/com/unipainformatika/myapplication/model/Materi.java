package com.unipainformatika.myapplication.model;

public class Materi {

    String judulbuku, kategori, namafile, id;

    public Materi(){}

    public Materi(String judulbuku, String kategori, String namafile, String id) {
        this.judulbuku = judulbuku;
        this.kategori = kategori;
        this.namafile = namafile;
        this.id = id;
    }

    public String getJudulbuku() {
        return judulbuku;
    }

    public void setJudulbuku(String judulbuku) {
        this.judulbuku = judulbuku;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNamafile() {
        return namafile;
    }

    public void setNamafile(String namafile) {
        this.namafile = namafile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
