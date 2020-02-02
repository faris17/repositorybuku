package com.unipainformatika.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataKP implements Parcelable {

    String nim, judul, studikasus, abstrak, pembimbingsatu, pembimbingdua, tanggal, namafile;

    public DataKP(String nim, String judul, String studikasus, String abstrak, String pembimbingsatu, String pembimbingdua, String tanggal, String namafile) {
        this.nim = nim;
        this.judul = judul;
        this.studikasus = studikasus;
        this.abstrak = abstrak;
        this.pembimbingsatu = pembimbingsatu;
        this.pembimbingdua = pembimbingdua;
        this.tanggal = tanggal;
        this.namafile = namafile;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getStudikasus() {
        return studikasus;
    }

    public void setStudikasus(String studikasus) {
        this.studikasus = studikasus;
    }

    public String getAbstrak() {
        return abstrak;
    }

    public void setAbstrak(String abstrak) {
        this.abstrak = abstrak;
    }

    public String getPembimbingsatu() {
        return pembimbingsatu;
    }

    public void setPembimbingsatu(String pembimbingsatu) {
        this.pembimbingsatu = pembimbingsatu;
    }

    public String getPembimbingdua() {
        return pembimbingdua;
    }

    public void setPembimbingdua(String pembimbingdua) {
        this.pembimbingdua = pembimbingdua;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNamafile() {
        return namafile;
    }

    public void setNamafile(String namafile) {
        this.namafile = namafile;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nim);
        dest.writeString(this.judul);
        dest.writeString(this.studikasus);
        dest.writeString(this.abstrak);
        dest.writeString(this.pembimbingsatu);
        dest.writeString(this.pembimbingdua);
        dest.writeString(this.tanggal);
        dest.writeString(this.namafile);
    }

    protected DataKP(Parcel in) {
        this.nim = in.readString();
        this.judul = in.readString();
        this.studikasus = in.readString();
        this.abstrak = in.readString();
        this.pembimbingsatu = in.readString();
        this.pembimbingdua = in.readString();
        this.tanggal = in.readString();
        this.namafile = in.readString();
    }

    public static final Parcelable.Creator<DataKP> CREATOR = new Parcelable.Creator<DataKP>() {
        @Override
        public DataKP createFromParcel(Parcel source) {
            return new DataKP(source);
        }

        @Override
        public DataKP[] newArray(int size) {
            return new DataKP[size];
        }
    };
}
