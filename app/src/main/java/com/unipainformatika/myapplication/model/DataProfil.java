package com.unipainformatika.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataProfil implements Parcelable {
    String namalengkap, nim, nohp, gender;

    public DataProfil(){

    }

    public DataProfil(String namalengkap, String nim, String nohp, String gender) {
        this.namalengkap = namalengkap;
        this.nim = nim;
        this.nohp = nohp;
        this.gender = gender;
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
        dest.writeString(this.nim);
        dest.writeString(this.namalengkap);
        dest.writeString(this.nohp);
        dest.writeString(this.gender);
    }

    protected DataProfil(Parcel in) {
        this.nim = in.readString();
        this.namalengkap = in.readString();
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
