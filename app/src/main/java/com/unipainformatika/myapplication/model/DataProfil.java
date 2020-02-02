package com.unipainformatika.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataProfil implements Parcelable {
    String nama, nim, nohp, gender;

    public DataProfil(){

    }

    public DataProfil(String nama, String nim, String nohp, String gender) {
        this.nama = nama;
        this.nim = nim;
        this.nohp = nohp;
        this.gender = gender;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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
        dest.writeString(this.nim);
        dest.writeString(this.nama);
        dest.writeString(this.nohp);
        dest.writeString(this.gender);
    }

    protected DataProfil(Parcel in) {
        this.nim = in.readString();
        this.nama = in.readString();
        this.nohp = in.readString();
        this.gender = in.readString();
    }

    public static final Parcelable.Creator<DataProfil> CREATOR = new Parcelable.Creator<DataProfil>() {
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
