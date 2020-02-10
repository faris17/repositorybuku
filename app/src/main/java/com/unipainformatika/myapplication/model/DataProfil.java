package com.unipainformatika.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataProfil implements Parcelable {
    String namalengkap, nim, nohp, gender, jurusan, level, status;

    public DataProfil(){

    }

    public DataProfil(String namalengkap, String nim, String nohp, String gender, String jurusan, String level, String status) {
        this.namalengkap = namalengkap;
        this.nim = nim;
        this.nohp = nohp;
        this.gender = gender;
        this.jurusan = jurusan;
        this.level = level;
        this.status = status;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamalengkap() {
        return namalengkap;
    }

    public void setNamalengkap(String namalengkap) {
        this.namalengkap = namalengkap;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namalengkap);
        dest.writeString(this.nim);
        dest.writeString(this.nohp);
        dest.writeString(this.gender);
        dest.writeString(this.jurusan);
        dest.writeString(this.level);
        dest.writeString(this.status);
    }

    protected DataProfil(Parcel in) {
        this.namalengkap = in.readString();
        this.nim = in.readString();
        this.nohp = in.readString();
        this.gender = in.readString();
        this.jurusan = in.readString();
        this.level = in.readString();
        this.status = in.readString();
    }

    public static final Creator<DataProfil> CREATOR = new Creator<DataProfil>() {
        @Override
        public DataProfil createFromParcel(Parcel source) {
            return new DataProfil(source);
        }

        @Override
        public DataProfil[] newArray(int size) {
            return new DataProfil[size];
        }
    };
}
